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
    Stack<Card> deck = new Stack<>();


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
        for (Player player: players){
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

        players.forEach(player -> player.setStatus(Status.IN_GAME));

        for (Player player : players){
            player.addCard(deck.pop());
            player.addCard(deck.pop());
        }

        boolean hasWinner = false;

        List<Player> newPlayers = players;

        while(!hasWinner){
            //bust hit stick
            for(Player player : newPlayers){
                if(player.getScore() > 21){
                    player.setStatus(Status.BUSTED);
                    System.out.println("\n" +player.getName() + " busted "+ "with score " +player.getScore() + "\n");
                }
            }

            newPlayers = players.stream().filter(player -> !player.getStatus().equals(Status.BUSTED)).collect(Collectors.toList());
            newPlayers.forEach(player -> System.out.print(player.getName() + ": " + player.getScore() + " "));

            try {
                System.out.println("\nSatisfied with score? Enter [1]: To Stick OR Enter [2]: To Hit");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String line = reader.readLine();
                List<String> commandInput = List.of(line.split(" "));

                switch (commandInput.get(1)){
                    case "1":
                        System.out.println(line +"\n");
                        break;
                    case "2":
                        Optional<Player> player = players.stream().filter(c -> c.getName().equals(commandInput.get(0))).findFirst();
                        player.get().addCard(deck.pop());
                        break;
                    default:
                        System.out.println("Invalid Input Command \n");
                }
            }
            catch (IOException ioException){
                System.out.println(ioException.toString());
            }

        }
    }


    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", decks=" + deck +
                '}';
    }
}
