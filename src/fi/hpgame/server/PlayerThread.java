package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class PlayerThread implements Runnable {
	private SocketCommunication playerSocket;
	private GameController game;
	private boolean gameIsOn = true;

	public PlayerThread(SocketCommunication socket, GameController game) {
		this.playerSocket = socket;
		this.game = game;
	}

	@Override
	public void run() {
		try {
			
			playerSocket.write("Server response : ok");

			Player player = null;
			Player targetPlayer = null;
			Card card = null;			
			
			while (gameIsOn) {

				String userInput = playerSocket.read();
				
				//TODO is this correct way to determine the client socket closing?
				if (userInput == null || userInput.equals("end")) {
					quitGame(player);
				} else if (userInput.equals("start")) {
					startGame();
				} else {
					if (player == null) {
						player = joinGame(userInput);
					} else if (game.isPlayerInTurn(player)) {
						try {
							if (card == null) {
								card = player.getCard(Integer.parseInt(userInput));
								if (card.requiresTargetPlayer()) {
									game.askPlayerToSelectPlayer(player, card);					
								} else if (card.requiresExtraParemeters()){
									game.askPlayerToGiveExtraParameter(player, card);
								} else {
									playCard(player, card, player);
									card = null;
									targetPlayer = null;
								}

							} else if (card.requiresTargetPlayer() && targetPlayer == null) {
								targetPlayer = game.getPlayer(Integer
										.parseInt(userInput));
								if (card.requiresExtraParemeters()) {
									game.askPlayerToGiveExtraParameter(player, card);
								} else {
									playCard( player, card, targetPlayer);
									card = null;
									targetPlayer = null;
								}
								
							} else {
								String additionalParameters = userInput;
								playCard(player, card, targetPlayer, additionalParameters);
								card = null;
								targetPlayer = null;
							}
						} catch (NumberFormatException e) {
							playerSocket.write("Give a valid integer.");
						} catch (GameException e) {
							playerSocket.write("Give a number that is range.");
						}
					} else {
						playerSocket.write("Not your turn!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void playCard(Player player, Card card,
			Player player2) {
		playCard(player, card,
				player2, null);
	}
	
	private void playCard(Player player, Card card,
			Player player2, String additionalParameters) {
		playerSocket.write(("You played card "
				+ card.getName() + " towards player " + player2
				.getName()));
		game.playCard(card, player, player2, additionalParameters);
		playerSocket.write("You have following cards: "
				+ player.getHand());	

	}

	// Start game with the players who have joined to the game
	private void startGame() {
		game.startGame();

	}

	private void quitGame(Player player) throws GameException {

		System.out.println("User quit.");
		if (player != null) {
			game.broadCastToPlayers("Player " + player.getName()
					+ " has left the game.");
			game.removePlayer(player);
		}
		this.gameIsOn = false;

	}

	private Player joinGame(String userInput) {
		Player player = new Player(userInput);
		game.addPlayer(player, playerSocket);
		game.broadCastToPlayers("Added new player " + player.getName());
		return player;
	}

}
