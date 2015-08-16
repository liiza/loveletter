package fi.hpgame.server;

import java.io.PrintWriter;
import java.net.Socket;

public class WriterThread implements Runnable{

	private PrintWriter output;
	
	private Player player;
	
	private Game game;
	
	public WriterThread(PrintWriter output, Player player, Game game) {
		this.output = output;
		this.player = player;
		this.game = game;
	}
	
	@Override
	public void run() {
	    while (game.playerIsInGame(player)) {
	    	output.println(game.getMessage());
	    	System.out.println("Writing message to user ");
	    }
		
	}

}
