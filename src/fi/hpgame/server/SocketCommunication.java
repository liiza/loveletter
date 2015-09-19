package fi.hpgame.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCommunication implements PlayerCommunication {

	private Socket socket;
	
	private PrintWriter output;
	
	private BufferedReader input;

	public SocketCommunication(Socket socket){
		this.socket = socket;
		try {
			this.output = new PrintWriter(this.socket.getOutputStream(),
					true);
			this.input = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void write(String s) {
		System.out.println("Writing message " + s + " to user.");
		this.output.println(s);
		
	}

	@Override
	public String read() throws IOException {

		return this.input.readLine();
	}

}
