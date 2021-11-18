package org.example.domains;

import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> cards;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getCards() {
        return cards;
    }

    // TODO: Change to Add Card
    public void addCards(List<Card> card) {
        this.cards = card;
    }

    public int getScore() {
       return cards.stream().mapToInt(Card::getCardValue).sum();
    }


    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                ", score=" + getScore() +
                '}';
    }
}
