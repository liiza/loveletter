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

	public Player getPlayer(int i) throws GameException {
		if (i >= 0 && i < players.size()) {
			return players.get(i);
		}
		throw new GameException("Player list outof bounds exception");
	}
	
	public boolean playerIsInGame(Player player) {
		return players.contains(player);
	
	}

	public Player getPlayerInTurn() throws GameException {
		if (allPlayersReady()) {
			throw new GameException("All players have already played in this round");
		}
		return players.get(playerInTurn);
	}

	public boolean isPlayerInTurn(Player player) {
		System.out.println("Index of player " + player.getName() + " is " +  players.indexOf(player) + ". playerInTurn is " + playerInTurn);
		return players.indexOf(player) == playerInTurn;
	}
	
	public boolean allPlayersReady() {
		System.out.println(playerInTurn + " == " + players.size());
		return playerInTurn == players.size();
	}
	public void cardsDealed() throws GameException{
		playerInTurn = 0;
	}


	public void giveCard(Card card, Player player) {
		player.giveCard(card);
		
	}

	public void playCard(Card card, Player player1, Player player2, String additionalParameters) throws GameException {
		if (!isPlayerInTurn(player1)) {
			throw new GameException("Player "+ player1.getName()+" is not in turn, but is trying to play");
		}
		if (player1.hasProtection()) {
			player1.setProtection(false);
		}
		player1.playCard(card, player2, additionalParameters);
		playerInTurn++;
		
	}

	public boolean gameIsOver() {
		return this.players.size() == 1;
	}

	public String getWinner() throws GameException {
		if (players.isEmpty()) {
			throw new GameException("Trying to request winner, although there are no players in game anymore. \n"
					+ "The winner may have lost connection to server.");
		} else if(!gameIsOver()){
			throw new GameException("Trying to request winner although game is not over yet");
		}
		return players.get(0).getName();
	}

}
