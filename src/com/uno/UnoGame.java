package com.uno;

import java.util.ArrayList;
import java.util.List;

public class UnoGame {

	public static void main(String[] args) {
	    List<Player> players = new ArrayList<>();
	    players.add(new Player("1", "Player 1"));
	    players.add(new Player("2", "Player 2"));
	    // Add more players if desired

	    Game game = new Game(players);
	    game.start();
	}

}
