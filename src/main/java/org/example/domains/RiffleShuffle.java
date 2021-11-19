package org.example.domains;

import java.util.Collections;
import java.util.List;

public class RiffleShuffle implements Shuffle{
    @Override
    public void shuffleCards(List<Card> deck) {
        Collections.shuffle(deck);
    }
}
