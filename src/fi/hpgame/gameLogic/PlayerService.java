package fi.hpgame.gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerService {

	private List<Player> players = Collections.synchronizedList((new ArrayList<Player>()));
	
	private int playerInTurn;
	
	public void addPlayer(Player player) {
		players.add(player);
		
	}

	public void removePlayer(Player player) throws GameException {
		if (!this.players.remove(player)) {
			throw new GameException("Trying to remove player that is not in the game");
			
		}
		
	}
	
	private String printPlayers(){
		String s = "";
		for (Player player : players) {
			s += player.getName();
			s += " ";
		}
		return s;
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public boolean playerIsInGame(Player player) {
		return players.contains(player);
	
	}

	public Player getPlayerInTurn() throws GameException {
		if (players.size() <= playerInTurn) {
			throw new GameException("All players have already playid");
		}
		return players.get(playerInTurn++);
	}

	public boolean allPlayersReady() {
		return playerInTurn == players.size();
	}
	public void cardsDealed(){
		playerInTurn = 0;
	}

	public void giveCard(Card card, Player player) {
		player.giveCard(card);
		
	}
}
