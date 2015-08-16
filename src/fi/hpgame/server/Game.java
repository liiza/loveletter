package fi.hpgame.server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	
	private List<Card> cards = Collections.synchronizedList(new ArrayList<Card>());
	
	private List<Player> players = Collections.synchronizedList((new ArrayList<Player>()));
	
	private List<String> messages = Collections.synchronizedList((new ArrayList<String>()));
	
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
	
	public void removePlayer(String name) {
		
	}
	
	public synchronized void broadCastToPlayers(String msg) {
		messages.add(msg);
		notifyAll();
	}

	
	public boolean playerIsInGame(Player player) {
		return players.contains(player);

	}

	public synchronized char[] getMessage() {
		// do some shit here about threads sleeping and waiting for their turn
		while (messages.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("There are messages");
		char[] msg = messages.get(messages.size() - 1).toCharArray();
		System.out.println(msg);
		
		return msg;
	}
}
