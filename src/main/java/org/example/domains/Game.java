package org.example.domains;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<Card> decks;

    public Game() {
        //  assignCards()
        //  startGame()
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

    public List<Card> getDecks() {
        return decks;
    }

    public void setDecks(List<Card> decks) {
        this.decks = decks;
    }

    public int getHighestScore() {
       return players.stream().filter(player -> player.getScore() <= 21).mapToInt(Player::getScore).max().getAsInt();
    }

    // TODO: Implement These methods
  //  assignCards()

  //  startGame()

  //  stopGame()

  //  assignPlayers()


    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", decks=" + decks +
                '}';
    }
}
