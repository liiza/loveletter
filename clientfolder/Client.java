//package fi.hpgame.client;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Socket myClient;
	    int PortNumber = 4444;
	    String hostName = "Localhost";
	    PrintWriter output;
	    BufferedReader input;
	    
	    String userInput;
	    String serverInput;
	    
	    boolean gameIsOn = true;
		try {
			myClient = new Socket(hostName, PortNumber);
			
			output = new PrintWriter(myClient.getOutputStream(), true);
			input = new BufferedReader(
							new InputStreamReader(myClient.getInputStream()));
			
			ListenerThread thread = new ListenerThread(input);
			new Thread(thread).start();
		
			
			while (gameIsOn) {
				System.out.println("Enter text:");
				userInput = System.console().readLine();
				output.println(userInput);
				if (userInput != null && userInput.equals("end")) {
					System.out.println("Closing the game");
					gameIsOn = false;
					thread.closeGame();
				}
	
			}
			input.close();
			output.close();
			myClient.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static class ListenerThread implements Runnable {
		private BufferedReader input;
		private boolean gameIsOn = true;
		
		public ListenerThread(BufferedReader input) {
			this.input = input;
		}
		
		public void closeGame() {
			gameIsOn = false;
		}
		
		public @Override void run() {
			try {
				while (gameIsOn) {
					String serverInput = input.readLine();
					System.out.println(serverInput);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
