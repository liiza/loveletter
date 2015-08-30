package fi.hpgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fi.hpgame.gameLogic.GameState;
import fi.hpgame.gameLogic.GameController;

public class ListenerThread implements Runnable {

	private ServerSocket serverSocket;
	private GameController game;
	
	public ListenerThread(ServerSocket serverSocket, GameController game) {
		this.serverSocket = serverSocket;
		this.game = game;
	}
	
	@Override
	public void run() {
		try {
			while (game.getState() == GameState.PREPARATION) {
	
				Socket clientSocket = serverSocket.accept();
			
				System.out.println("Client connected.");
				
				new Thread(new PlayerThread(clientSocket, game)).start();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
