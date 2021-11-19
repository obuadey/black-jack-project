package org.example.domains;

import java.util.Collections;
import java.util.List;

public class PharoahShuffle implements Shuffle{
    @Override
    public void shuffleCards(List<Card> deck) {
        Collections.shuffle(deck);
    }
}
