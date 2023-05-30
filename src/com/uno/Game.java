package com.uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private List<Player> players;
    private int currentPlayerIndex;
    private Card currentCard;
    private boolean reverseDirection;

    public Game(List<Player> players) {
        this.deck = new Deck();
        this.players = players;
        this.currentPlayerIndex = 0;
        this.reverseDirection = false;
    }

    public void start() {
        deck.shuffle();
        dealInitialCards();

        currentCard = deck.drawCard();
        while (!isGameEnded()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            displayCurrentCard();
            displayPlayerHand(currentPlayer);

            if (currentPlayer.hasValidCards(currentCard)) {
                Card selectedCard = getPlayerSelectedCard(currentPlayer);
                if (selectedCard != null) {
                    if (selectedCard.getColor() == CardColor.WILD || selectedCard.getColor() == CardColor.WILD_DRAW_FOUR) {
                        chooseWildCardColor(selectedCard);
                    }
                    playCard(selectedCard);
                }
            } else {
                System.out.println("You have no valid cards to play. Drawing a card...");
                currentPlayer.drawCard(deck);
            }

            updateCurrentPlayer();
        }

        declareWinner();
    }

    private void dealInitialCards() {
        for (Player player : players) {
            List<Card> initialCards = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                initialCards.add(deck.drawCard());
            }
            player.receiveInitialCards(initialCards);
        }
    }

    private void displayCurrentCard() {
        System.out.println("Current Card: " + currentCard.getColor() + " " + currentCard.getValue());
    }

    private void displayPlayerHand(Player player) {
        System.out.println("Player " + player.getName() + "'s Hand:");
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            System.out.println(i + ". " + card.getColor() + " " + card.getValue());
        }
    }

    private Card getPlayerSelectedCard(Player player) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a card to play (enter the index): ");
        int index = scanner.nextInt();
        if (index >= 0 && index < player.getHand().size()) {
            Card selectedCard = player.getHand().get(index);
            if (selectedCard.canPlayOn(currentCard)) {
                return selectedCard;
            } else {
                System.out.println("Invalid card selection. Try again.");
            }
        } else {
            System.out.println("Invalid card index. Try again.");
        }
        return null;
    }

    private void playCard(Card card) {
        currentCard = card;
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.playCard(card);

        if (currentPlayer.hasEmptyHand()) {
            System.out.println("Player " + currentPlayer.getName() + " has won!");
        } else {
            System.out.println("Player " + currentPlayer.getName() + " played: " +
                    card.getColor() + " " + card.getValue());
            if (card.getValue() == CardValue.SKIP) {
                skipNextPlayer();
            } else if (card.getValue() == CardValue.REVERSE) {
                reverseDirection();
            } else if (card.getValue() == CardValue.DRAW_TWO) {
                Player nextPlayer = getNextPlayer();
                nextPlayer.drawCard(deck);
                nextPlayer.drawCard(deck);
                skipNextPlayer();
            }
        }
    }

    private void skipNextPlayer() {
        Player nextPlayer = getNextPlayer();
        System.out.println("Skipping player: " + nextPlayer.getName());
        updateCurrentPlayer();
    }

    private void reverseDirection() {
        reverseDirection = !reverseDirection;
        System.out.println("Direction reversed.");
    }

    private Player getNextPlayer() {
        if (!reverseDirection) {
            if (currentPlayerIndex == players.size() - 1) {
                return players.get(0);
            } else {
                return players.get(currentPlayerIndex + 1);
            }
        } else {
            if (currentPlayerIndex == 0) {
                return players.get(players.size() - 1);
            } else {
                return players.get(currentPlayerIndex - 1);
            }
        }
    }

    private void updateCurrentPlayer() {
        if (!reverseDirection) {
            if (currentPlayerIndex == players.size() - 1) {
                currentPlayerIndex = 0;
            } else {
                currentPlayerIndex++;
            }
        } else {
            if (currentPlayerIndex == 0) {
                currentPlayerIndex = players.size() - 1;
            } else {
                currentPlayerIndex--;
            }
        }
    }

    private boolean isGameEnded() {
        for (Player player : players) {
            if (player.hasEmptyHand()) {
                return true;
            }
        }
        return false;
    }

    private void declareWinner() {
        Player winner = null;
        for (Player player : players) {
            if (player.hasEmptyHand()) {
                winner = player;
                break;
            }
        }
        if (winner != null) {
            System.out.println("Player " + winner.getName() + " has won the game!");
        } else {
            System.out.println("Game ended in a draw!");
        }
    }

    private void chooseWildCardColor(Card wildCard) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose a color for the Wild card (RED, BLUE, GREEN, YELLOW): ");
        String colorInput = scanner.nextLine().toUpperCase();

        CardColor chosenColor = CardColor.valueOf(colorInput);
        wildCard.setColor(chosenColor);
    }
}
