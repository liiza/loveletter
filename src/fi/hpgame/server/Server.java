package fi.hpgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.GameState;

public class Server {
	// TODO read from configuration
	public static Integer portNumber = 4444;
	
	public static void main(String[] args) {
		
		System.out.println("Let the game begin.");
		GameController game = GameController.initGame();

		try {
			new Thread(new ListenerThread(new ServerSocket(portNumber), game)).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// TODO End game somehow
		synchronized(game) {
			try {
				while (true) {
					switch (game.getState()) {
					case PREPARATION:
						game.wait();
						break;
					case ON:
						// Give two cards to each player at the beginning
						game.dealCards(2);
						game.setState(GameState.PLAYING);
						break;
					case PLAYING:
						if (game.allPlayersReady()) {
							game.setState(GameState.DEALNEWCARDS);
							break;
						}
						game.askPlayerToPlayCard();
						game.wait();
						break;
					case DEALNEWCARDS:
						game.dealCards(1);
						game.askPlayerToPlayCard();
						game.wait();
						break;
						
					default:
						game.wait();
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		
	

	}

}
