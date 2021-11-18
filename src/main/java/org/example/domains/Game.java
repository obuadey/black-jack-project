package org.example.domains;

import org.example.domains.enums.CardSuit;
import org.example.domains.enums.CardValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<Card> deck = new ArrayList<>();

    public Game() {
        System.out.println("Welcome To the Black Jack Game\n");
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

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
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
        for (Player player : players){
            player.addCard(deck.get(0));
            deck.remove(0);
            player.addCard(deck.get(0));
            deck.remove(0);
        }
        System.out.println(deck.size());
        players.forEach(c -> System.out.println(c.getCards()));
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", decks=" + deck +
                '}';
    }
}
