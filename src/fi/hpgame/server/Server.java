package fi.hpgame.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		System.out.println("Let the game begin.");
		Game game  = new Game();
		Integer portNumber = 4444;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(portNumber);
			while (true) {
				Socket clientSocket = null;
				
				clientSocket = serverSocket.accept();
				System.out.println("Client connected.");
				
				new Thread(new WorkerThread(clientSocket, game)).start();
			
				//serverSocket.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
