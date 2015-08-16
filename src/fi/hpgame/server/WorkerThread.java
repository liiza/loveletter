package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class WorkerThread implements Runnable {
	private Socket socket;
	private Game game;
	
	public WorkerThread(Socket clientSocket, Game game){
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
	
			output.println("ok");
	
			boolean gameIsOn = true;
			Player player = null;
			while (gameIsOn) {
				String userInput = input.readLine();
		
				if (userInput == null) {
					System.out.println("User input is null.");
					continue;
				}
				if (userInput.equals("end")) {
					output.println(("end"));
					gameIsOn = false;
				} else {
					if (player == null) {
						player = new Player(userInput);
						game.addPlayer(player, output);
						output.println(("Added new player " + userInput));
					} else {
						game.putCard(new Card(userInput));
						output.println(("Thanks, added new card " + userInput));
						game.broadCastToPlayers(("Added new card " + userInput));
						game.printAllCards();
											
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error while reading socket.");
		}
	}

}
