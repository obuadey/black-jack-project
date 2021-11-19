package org.example.domains;

import org.example.domains.enums.CardSuit;
import org.example.domains.enums.CardValue;
import org.example.domains.enums.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players = new ArrayList<>();
    private Stack<Card> deck = new Stack<>();
    private boolean hasWinner;

    private List<Player> modifierPlayers = new ArrayList<>();
    private List<Player> bustedPlayers = new ArrayList<>();


    public Game() {
        System.out.println("Welcome To the Black Jack Game");
        createDeck();
        promptUser();
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


    public void setDeck(Stack<Card> deck) {
        this.deck = deck;
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


    private void promptUser(){
        System.out.println("\nEnter [1]: Start Game | [2]: Assign Player | [3] Quit Game");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            switch (line){
                case "1":
                    startGame();
                    break;
                case "2":
                    assignPlayers();
                    break;
                case "3":
                    System.out.println("Thank You!!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Input Command \n");
                    promptUser();
            }
        }
        catch (IOException ioException){
            System.out.println(ioException.toString());
        }
    }


    private void startGame(){
        if (players.size() <= 1){
            System.out.println("To play Default Mode, Enter [1]: To Continue OR Enter [2]: Assign Player");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                switch (line){
                    case "1":
                        createDefaultPlayers();
                        playGame();
                        break;
                    case "2":
                        assignPlayers();
                        break;
                    default:
                        System.out.println("Invalid Input Command \n");
                        startGame();
                }
            }
            catch (IOException ioException){
                System.out.println(ioException.toString());
            }
        }
        else {
            playGame();
        }
    }


    private void assignPlayers(){
        System.out.println("\nEnter Player name: OR Enter [0] to exit");
        System.out.print("Name or Exit Code : ");
        while (players.size() <= 6) {
            if (players.size() < 6) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String line = reader.readLine();
                    if ("0".equals(line)) {
                        promptUser();
                        System.exit(1);
                    } else {
                        players.add(new Player(line));;
                        System.out.println("Total Number of players : " + players.size() +"\n");
                        System.out.print("Name or Exit Code : ");
                    }
                } catch (IOException ioException) {
                    System.out.println(ioException.toString());
                }
            }
            else {
                System.out.println("\n Maximum Player Limit Reached");
                promptUser();
            }
        }
    }


    private void createDefaultPlayers(){
        players.clear();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
    }


    private void playGame(){
        Collections.shuffle(deck);
        for (Player player : players){
            player.addCard(deck.pop());
            player.addCard(deck.pop());
        }

        modifierPlayers = new ArrayList<>(players);

        hasWinner = false;
        while (!hasWinner){
            for(Player player : modifierPlayers){
                if (player.getScore() < 17){
                    player.setStatus(Status.HIT);
                }
                else if (player.getScore() >=17 && player.getScore() <= 21){
                    player.setStatus(Status.STICK);
                }
                else {
                    player.setStatus(Status.BUSTED);
                    bustedPlayers.add(player);
                }
            }

            bustedPlayers.forEach(player -> modifierPlayers.remove(player));

            for(Player player : modifierPlayers){
                if (player.getStatus() == Status.HIT){
                    player.addCard(deck.pop());
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
            System.out.println("Game was won by : " + getWinner().toString());
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getScore() == 21).count() == modifierPlayers.size()){
            hasWinner = true;
            System.out.println("All players had exactly 21");
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getScore() == 21).count() == 1){
            hasWinner = true;
            System.out.println("Game was won by : " + getWinner().toString());
            printScoreboard();
        }
        else if (modifierPlayers.stream().filter(player -> player.getStatus() == Status.STICK).count() == modifierPlayers.size()){
            hasWinner = true;
            System.out.println("Game was won by : " + getWinner().toString());
            printScoreboard();
        }

    }

    private void printScoreboard(){
        players.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", decks=" + deck +
                '}';
    }
}
