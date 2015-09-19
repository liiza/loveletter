package fi.hpgame.server;

import java.io.PrintWriter;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class WriterThread implements Runnable{

	private PlayerCommunication playerCommunication;
	
	private Player player;
	
	private GameController game;
	
	public WriterThread(PlayerCommunication playerCommunication, Player player, GameController game) {
		this.playerCommunication = playerCommunication;
		this.player = player;
		this.game = game;
	}
	
	@Override
	public void run() {
	    while (game.playerIsInGame(player)) {
	    	try {
	    		playerCommunication.write(game.getMessage(player));
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	System.out.println("Writing message to user " + player.getName());
	    }
		System.out.println("User " + player.getName() + " exited.");
	}

}
