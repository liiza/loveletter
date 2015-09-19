package fi.hpgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.locks.Lock;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameState;

public class Server {
	// TODO read from configuration
	public static Integer portNumber = 4444;
	
	public static void main(String[] args) {
		
		System.out.println("Let the game begin.");
		
		GameController game = GameController.initGame();
		game.setState(GameState.PREPARATION);

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
							game.setState(GameState.ON);
							break;
						case ON:
							game.dealCards(1);
							game.setState(GameState.PLAYING);
							break;
						case PLAYING:
							if (game.gameIsOver()) {
								game.broadCastToPlayers("Game is over. The winner of this round is " + game.getWinner());
								game.setState(GameState.GAMEOVER);
								break;
							}
							if (game.deckIsEmpty()) {
								System.out.println("Final round");
								game.setState(GameState.FINALROUND);
								break;
							}
							
							if (game.allPlayersReady()) {
								game.newRound();
							}
							
							game.playerInTurnPlays();
							game.wait();
							break;
						
							
						case FINALROUND:
							game.broadCastToPlayers("Game is over. The winner of this round is " + game.getWinnerInLastRound());
							game.printAllPlayerHands();
							game.setState(GameState.GAMEOVER);
							
							break;
							
						case GAMEOVER:
							
							game.wait();
							break;
							
						default:
							System.out.println("The game is over");
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
