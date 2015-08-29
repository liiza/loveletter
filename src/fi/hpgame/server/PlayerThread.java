package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import fi.hpgame.gameLogic.Card;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.GameState;
import fi.hpgame.gameLogic.King;
import fi.hpgame.gameLogic.Player;

public class PlayerThread implements Runnable {
	private Socket socket;
	private GameController game;
	private boolean gameIsOn = true;
	
	public PlayerThread(Socket clientSocket, GameController game){
		this.socket = clientSocket;
		this.game = game;
	}
	
	@Override
	public void run() {
		try {
			
		    PrintWriter output =
		            new PrintWriter(this.socket.getOutputStream(), true);
			BufferedReader input =
				        new BufferedReader(
				            new InputStreamReader(this.socket.getInputStream()));
	
			output.println("Server response : ok");
	
			Player player = null;
			Card card = null;
			while (gameIsOn) {
				String userInput = input.readLine();
	
				if (userInput.equals("end")) {
					quitGame(player);
				} else if (userInput.equals("start")){
					startGame();
				} else {
					if (player == null) {
						player = joinGame(output, userInput);
					} else if (card == null){
						
						int cardIndex; 
						List<Card> cards = player.getCards();
						try {
							cardIndex = Integer.parseInt(userInput);
							if (cardIndex < 0 || cardIndex >= cards.size()) {
								output.println("Give a card index that is in range of 0 to " + (cards.size() - 1));
								continue;
							}
						} catch (NumberFormatException e) {
							output.println("Give a valid integer.");
							continue;
						}
						card = cards.get(cardIndex);
						game.askPlayerToSelectPlayer(player);
						
											
					} else  {
						int playerIndex;
						try {
							playerIndex = Integer.parseInt(userInput);
							game.getPlayer(playerIndex);
						} catch (NumberFormatException e) {
							output.println("Give a valid integer.");
							continue;
						} catch(GameException e) {
							output.println("Give a number that is range.");
							continue;
						}
						Player player2 = game.getPlayer(playerIndex);
						// TODO don't play card against your self
						game.playCard(card, player, player2);
						output.println(("You played card " + card.getName() + " towards player " + player2.getName()));
						game.broadCastToPlayers((player.getName() + " played card " + userInput));
						output.println("You have following cards: " + player.getCards().toString());
						
						card = null;
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Start game with the players who have joined to the game
	private void startGame() {
		game.startGame();
		
	}

	private void quitGame(Player player) throws GameException {
	
		System.out.println("User quit.");
		if (player != null) {
			game.broadCastToPlayers("Player " + player.getName() + " has left the game.");
			game.removePlayer(player);
		}
		this.gameIsOn = false;

	}

	private synchronized Player joinGame(PrintWriter output, String userInput) {
		Player player = new Player(userInput);
		game.addPlayer(player, output);
		output.println(("Added new player " + userInput));
		return player;
	}

}
