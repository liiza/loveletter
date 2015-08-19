package fi.hpgame.gameLogic;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fi.hpgame.messages.MessageService;
import fi.hpgame.server.WriterThread;

public class GameController {
	
	
	private MessageService msgService = new MessageService();
	
	private CardService cardService = new CardService();
	
	private PlayerService playerService = new PlayerService();

	private GameState state;
	
	private GameController() {
		
	}
	
	public static synchronized GameController initGame(){
		GameController game = new GameController();
		game.putCard(new King("King"));
		game.putCard(new Priest("Priest"));
		game.putCard(new King("King"));
		game.putCard(new Priest("Priest"));

		game.setState(GameState.PREPARATION);
		return game;
	}
	
	public synchronized void startGame() {
		this.setState(GameState.ON);
		notify();
	}
	
	public synchronized void dealCards(int howMany) throws GameException {
		for (int i = 0;  i< howMany; i++) {
			for(Player player : playerService.getPlayers()) {
				if (!cardService.isEmpty()){
					Card card = cardService.getFirstCard();
					playerService.giveCard(card, player);
					sendMessageToPlayer("You were given a card " + card.getName(),  player);
				}
			}
		}
		playerService.cardsDealed();
	}
	public void askPlayerToPlayCard() throws GameException {
		sendMessageToPlayer("Play a card",  playerService.getPlayerInTurn());
	}
	
	public synchronized void playCard(Card card, Player player1, Player player2) {
		player1.playCard(card, player2);
		notify();
	}
	public boolean allPlayersReady() {
		return  playerService.allPlayersReady();
	}
	public void putCard(Card card) {
		cardService.addCard(card);
	}
	
	public void addPlayer(Player player, PrintWriter output) {
		playerService.addPlayer(player);
		new Thread(new WriterThread(output, player, this)).start();
		
	}
	
	public void removePlayer(Player player) throws GameException {
		playerService.removePlayer(player);
	}
	public Player getPlayer(int i) throws GameException {
		return playerService.getPlayer(i);
	}
	
	public boolean playerIsInGame(Player player) {
		return playerService.playerIsInGame(player);

	}
	public void sendMessageToPlayer(String msg, Player player) {
		msgService.addMessage("Private message to player " + player.getName() + " : " + msg, player);
	}
	
	public void broadCastToPlayers(String msg) {
		msgService.addMessage("BroadCasted message " + msg, playerService.getPlayers());
	}


	public char[] getMessage(Player player) throws GameException {

		return msgService.getMessage(player).toCharArray();
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}


}
