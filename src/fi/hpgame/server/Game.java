package fi.hpgame.server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	
	private List<Card> cards = Collections.synchronizedList(new ArrayList<Card>());
	
	private List<Player> players = Collections.synchronizedList((new ArrayList<Player>()));
	
	private MessageService msgService = new MessageService();
	
	public static synchronized Game initGame(){
		Game game = new Game();
		game.putCard(new Card("King"));
		
		return game;
	}
	
	public void putCard(Card card) {
		cards.add(card);
	}
	
	public synchronized Card getFirstCard() {
		if (cards.isEmpty()) {
			return null; // TODO hmm
		}
		return cards.get(cards.size() - 1);
	}
	// For server logging purposes
	public void printAllCards(){
		for (Card card: cards) {
			System.out.println(card.getName());
		}
	}
	public void addPlayer(Player player, PrintWriter output) {
		players.add(player);
		new Thread(new WriterThread(output, player, this)).start();
		
	}
	
	public void removePlayer(Player player) throws GameException {
		if (!this.players.remove(player)) {
			throw new GameException("Trying to remove player that is not in the game");
			
		}
	}
	
	public void broadCastToPlayers(String msg) {
		msgService.addMessage("BroadCasted message " + msg, players);
	}
	private String printPlayers(){
		String s = "";
		for (Player player : players) {
			s += player.getName();
			s += " ";
		}
		return s;
	}

	
	public boolean playerIsInGame(Player player) {
		return players.contains(player);

	}

	public char[] getMessage(Player player) throws GameException {

		return msgService.getMessage(player).toCharArray();
	}
}
