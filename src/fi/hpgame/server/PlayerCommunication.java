package fi.hpgame.server;

import java.io.IOException;

public interface PlayerCommunication {
	
	public void write(String s);
	
	public String read() throws IOException;

}
