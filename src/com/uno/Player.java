package com.uno;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String identifier;
    private String name;
    private List<Card> hand;

    public Player(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void receiveInitialCards(List<Card> cards) {
        hand.addAll(cards);
    }

    public void drawCard(Deck deck) {
        Card card = deck.drawCard();
        hand.add(card);
    }

    public boolean playCard(Card card) {
        if (hand.contains(card)) {
            hand.remove(card);
            return true;
        }
        return false;
    }

    public boolean hasValidCards(Card currentCard) {
        for (Card card : hand) {
            if (card.canPlayOn(currentCard)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEmptyHand() {
        return hand.isEmpty();
    }
}
