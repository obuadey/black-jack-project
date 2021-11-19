package org.example.domains;

import org.example.domains.enums.CardSuit;
import org.example.domains.enums.CardValue;
import org.example.domains.enums.Status;

import java.util.*;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private Stack<Card> deck = new Stack<>();
    private boolean hasWinner;
    private final String[] args;

    private List<Player> modifierPlayers = new ArrayList<>();
    private final PlayerStrategy playerStrategy = new PlayerStrategy();

    public Game(String[] args) {
        this.args = args;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Player getWinner() {
        int max = 0;
        Player win = new Player("Null");
        for (Player player: modifierPlayers){
            if (player.getScore() > max && player.getScore() <= 21) {
                max = player.getScore();
                win = player;
            }
        }
        return win;
    }


    public Stack<Card> getDeck() {
        return deck;
    }


    public int getHighestScore() {
       return players.stream().filter(player -> player.getScore() <= 21).mapToInt(Player::getScore).max().getAsInt();
    }


    private void createDeck(){
        for (CardSuit suit: CardSuit.values()){
            for (CardValue value: CardValue.values()){
                deck.add(new Card(value,suit));
            }
        }
    }


    private void createDefaultPlayers(int numberOfPlayers){
        for (int i =0; i < numberOfPlayers; i++){
            players.add(new Player("Player"+(i+1)));
            players.get(players.size()-1).setStrategy(Strategy.DEFAULT);
        }
    }


    private void processCommandArgs(){
        if (args.length == 0){
            createDefaultPlayers(3);
        }
        else if (args.length == 1){
            if (Integer.parseInt(args[0])  > 1 && Integer.parseInt(args[0])  <= 6){
                createDefaultPlayers(Integer.parseInt(args[0]));
            }
            else {
                System.out.println("Min Number of Players : 2 and Max Number of Players: 6");
            }
        }
        else {
            if (args.length % 2 == 0){
                for (int i=0; i < args.length; i++){
                    if (i % 2 == 0){
                        players.add(new Player(args[i].substring(2)));
                    }
                    else {
                        if(Objects.equals(args[i], "always-stick")){
                            players.get(players.size()-1).setStrategy(Strategy.ALWAYS_STICK);
                        }
                       else if(Objects.equals(args[i], "always-hit")){
                            players.get(players.size()-1).setStrategy(Strategy.ALWAYS_HIT);
                        }
                       else if (Objects.equals(args[i], "risk-calculator")){
                            players.get(players.size()-1).setStrategy(Strategy.RISK_CALCULATOR);
                        }
                       else {
                            players.get(players.size()-1).setStrategy(Strategy.DEFAULT);
                        }
                    }
                }
            }
            else {
                System.out.println("Player or Strategy not provided");
            }
        }
    }


    public void startGame(){
        processCommandArgs();
        createDeck();
        shuffleCards();
        for (int i = 0; i < 2; i++){
            for (Player player : players){
                Card popCard = deck.pop();
                player.addCard(popCard);
                System.out.println(player.getName() + " assigned " + popCard.getCardName()+ " of " + popCard.getCardSuit());
            }
        }

        players.forEach(player -> System.out.println(player.getName() + " " + player.getScore()));

        modifierPlayers = new ArrayList<>(players);
        hasWinner = false;

        while (!hasWinner){
            for(Player player : modifierPlayers){
                if (player.getStrategy() == Strategy.DEFAULT){
                    playerStrategy.defaultStrategy(player);
                }
                else if (player.getStrategy() == Strategy.ALWAYS_STICK){
                    playerStrategy.alwaysStickStrategy(player);
                }
                else if (player.getStrategy() == Strategy.ALWAYS_HIT){
                    playerStrategy.alwaysHitStrategy(player);
                }
                else if (player.getStrategy() == Strategy.RISK_CALCULATOR){
                    playerStrategy.defaultStrategy(player);
                }
            }

            playerStrategy.getBustedPlayers().forEach(player -> modifierPlayers.remove(player));

            for(Player player : modifierPlayers){
                if (player.getStatus() == Status.HIT){
                    Card popCard = deck.pop();
                    player.addCard(popCard);
                    System.out.println(player.getName() + " assigned " + popCard.getCardName() + " of " + popCard.getCardSuit());
                }
            }

            checkWinner();
        }

    }




    private void checkWinner(){
        if (modifierPlayers.size() == 0){
            hasWinner = true;
            System.out.println("No Winner in this Game");
            printScoreboard();
        }
        else if (modifierPlayers.size() == 1){
            hasWinner = true;
            System.out.println("Game was won by : " + getWinner().getName() + " with Score of " + getWinner().getScore());
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getScore() == 21).count() == modifierPlayers.size()){
            hasWinner = true;
            System.out.println("All players had exactly 21");
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getScore() == 21).count() == 1){
            hasWinner = true;
            System.out.println("Game was won by : " + getWinner().getName() + " with Score of " + getWinner().getScore());
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getStatus() == Status.STICK).count() == modifierPlayers.size()){
            hasWinner = true;
            System.out.println("Game was won by : " + getWinner().getName() + " with Score of " + getWinner().getScore());
            printScoreboard();
        }

    }

    private void shuffleCards(){
        PharoahShuffle pharoahShuffle = new PharoahShuffle();
        RiffleShuffle riffleShuffle = new RiffleShuffle();

        int random = new Random().nextInt();
        if (random %2 == 0){
            riffleShuffle.shuffleCards(deck);
        }
        else {
            pharoahShuffle.shuffleCards(deck);
        }
    }

    private void printScoreboard(){
        System.out.println();
        players.forEach(player -> System.out.println(player.getName() + " || Total Score :" + player.getScore()));
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", decks=" + deck +
                '}';
    }
}
