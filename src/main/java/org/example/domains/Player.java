package org.example.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> cards = new ArrayList<>();

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
    public void addCards(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void addCard(Card card) {
        this.cards.add(card);
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
