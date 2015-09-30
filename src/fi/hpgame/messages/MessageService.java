package fi.hpgame.messages;


import java.util.ArrayList;
import java.util.List;

import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

/*
 * MessageService is synchronized class. The calls to getMessage block until there is a message in queue.
 */
public class MessageService {
	
	private static MessageService messageService;
	
	private MessageService(){
		
	}
	
	public static MessageService getInstance() {
		if (messageService == null) {
			messageService = new MessageService();
		}
		return messageService;
	}
	
	// List is not synchronized since we need to handle the synchronization manually
	private List<Message> messages = new ArrayList<Message>();
	
	public synchronized void addMessage(String msg, Player player) {
		 ArrayList<Player> players = new ArrayList<Player>();
		 players.add(player);
		 addMessage(msg, players);
	}
	
	public synchronized void addMessage(String msg, List<Player> players) {
		Message message = new Message(msg);
		message.setBroadCastTo(players.size());
		ArrayList<Player> copyPlayers = new ArrayList<Player>(players);
		message.setPlayers(copyPlayers);
		messages.add(message);
		notifyAll();
	}
	
	public synchronized String getMessage(Player player) throws GameException {
		Message message = pickTheFirstMessageVisibleToPlayer(player);
	
		while (message == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = pickTheFirstMessageVisibleToPlayer(player);
		}
		
		if (message.increaseSeen() == message.getBroadCastTo()) {
			if (!messages.remove(message)) {
				throw new GameException("Trying to remove message that is not in the list.");
			}
		}
		return message.getContent();
		
	}
	
	private Message pickTheFirstMessageVisibleToPlayer(Player player) {
		// FIFO
		for (Message message : messages) {
		
			if (message.shouldSeeTheMessage(player)) {
				message.removePlayer(player);
				return message;
			}	
	
		}
		return null;
		
	}



}
