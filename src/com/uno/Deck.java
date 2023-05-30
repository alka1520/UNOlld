package com.uno;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
        initializeDeck();
    }

    private void initializeDeck() {
        for (CardColor color : CardColor.values()) {
            if (color != CardColor.WILD) {
                for (CardValue value : CardValue.values()) {
                    if (value != CardValue.WILD && value != CardValue.WILD_DRAW_FOUR) {
                        cards.add(new Card(color, value));
                    }
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            replenishDeck();
        }
        return cards.remove(cards.size() - 1);
    }

    private void replenishDeck() {
        // Remove the current wild card if any
        cards.removeIf(card -> card.getColor() == CardColor.WILD || card.getValue() == CardValue.WILD_DRAW_FOUR);
        initializeDeck();
        shuffle();
    }
}
