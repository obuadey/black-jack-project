package org.example.domains;

import org.example.domains.enums.CardSuit;
import org.example.domains.enums.CardValue;

public class Card {
    private CardValue cardValue;
    private CardSuit cardSuit;

    public Card(CardValue cardValue, CardSuit cardSuit) {
        this.cardValue = cardValue;
        this.cardSuit = cardSuit;
    }

    public int getCardValue() {
        return cardValue.getValue();
    }

    public CardValue getCardName() {
        return cardValue;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardValue=" + cardValue +
                ", cardSuit=" + cardSuit +
                '}';
    }
}
