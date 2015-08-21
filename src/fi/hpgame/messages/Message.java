package fi.hpgame.messages;

import java.util.ArrayList;
import java.util.List;

import fi.hpgame.gameLogic.Player;

public class Message {
	
	private String content;
	
	private int seen;
	
	private int broadCastTo;
	
	private List<Player> players = new ArrayList<Player>();
	
	public Message(String s) {
		this.content = s;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getBroadCastTo() {
		return broadCastTo;
	}

	public void setBroadCastTo(int broadCastTo) {
		this.broadCastTo = broadCastTo;
	}
	public int increaseSeen() {
		return ++this.seen;
	}
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	public boolean shouldSeeTheMessage(Player player) {
		return this.players.contains(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
		
	}
	
	public String printPlayers() {
		String s = "";
		for (Player player : players) {
			s +=player.getName();
		}
		return s;
	}
}
