package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import fi.hpgame.gameLogic.Card;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.GameState;
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
			while (gameIsOn) {
				String userInput = input.readLine();
	
				if (userInput.equals("end")) {
					quitGame(player);
				
				} else if (userInput.equals("start")){
					startGame();
				} else {
					if (player == null) {
						player = joinGame(output, userInput);
					} else {
						game.putCard(new Card(userInput));
						output.println(("Thanks, added new card " + userInput));
						game.broadCastToPlayers(("Added new card " + userInput));
					
											
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

	private Player joinGame(PrintWriter output, String userInput) {
		Player player = new Player(userInput);
		game.addPlayer(player, output);
		output.println(("Added new player " + userInput));
		return player;
	}

}
