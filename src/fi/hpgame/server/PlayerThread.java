package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import fi.hpgame.gameLogic.Card;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class PlayerThread implements Runnable {
	private Socket socket;
	private GameController game;
	private boolean gameIsOn = true;

	public PlayerThread(Socket clientSocket, GameController game) {
		this.socket = clientSocket;
		this.game = game;
	}

	@Override
	public void run() {
		try {

			PrintWriter output = new PrintWriter(this.socket.getOutputStream(),
					true);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));

			output.println("Server response : ok");

			Player player = null;
			Player targetPlayer = null;
			Card card = null;			
			
			while (gameIsOn) {

				String userInput = input.readLine();
				
				//TODO is this correct way to determine the client socket closing?
				if (userInput == null || userInput.equals("end")) {
					quitGame(player);
				} else if (userInput.equals("start")) {
					startGame();
				} else {
					if (player == null) {
						player = joinGame(output, userInput);
					} else if (game.isPlayerInTurn(player)) {
						try {
							if (card == null) {
								card = player.getCard(Integer.parseInt(userInput));
								if (card.requiresTargetPlayer()) {
									game.askPlayerToSelectPlayer(player, card);					
								} else if (card.requiresExtraParemeters()){
									game.askPlayerToGiveExtraParameter(player, card);
								} else {
									playCard(output, player, card, player);
									card = null;
									targetPlayer = null;
								}

							} else if (card.requiresTargetPlayer() && targetPlayer == null) {
								targetPlayer = game.getPlayer(Integer
										.parseInt(userInput));
								if (card.requiresExtraParemeters()) {
									game.askPlayerToGiveExtraParameter(player, card);
								} else {
									playCard(output, player, card, targetPlayer);
									card = null;
									targetPlayer = null;
								}
								
							} else {
								String additionalParameters = userInput;
								playCard(output, player, card, targetPlayer, additionalParameters);
								card = null;
								targetPlayer = null;
							}
						} catch (NumberFormatException e) {
							output.println("Give a valid integer.");
						} catch (GameException e) {
							output.println("Give a number that is range.");
						}
					} else {
						output.println("Not your turn!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void playCard(PrintWriter output, Player player, Card card,
			Player player2) {
		playCard(output, player, card,
				player2, null);
	}
	
	private void playCard(PrintWriter output, Player player, Card card,
			Player player2, String additionalParameters) {
		output.println(("You played card "
				+ card.getName() + " towards player " + player2
				.getName()));
		game.playCard(card, player, player2, additionalParameters);
		output.println("You have following cards: "
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

	private Player joinGame(PrintWriter output, String userInput) {
		Player player = new Player(userInput);
		game.addPlayer(player, output);
		game.broadCastToPlayers("Added new player " + player.getName());
		return player;
	}

}
