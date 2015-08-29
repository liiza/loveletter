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
		game.putCard(new King(game));
		game.putCard(new Priest(game));
		game.putCard(new King(game));
		game.putCard(new Priest(game));

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
		String msg = "Select a card to play ";
		Player player = playerService.getPlayerInTurn();
		List<Card> cards = player.getCards();
		for (int i = 0; i < cards.size(); i++) {
			msg += " [" + i + "] " + cards.get(i).getName();
		}
		sendMessageToPlayer(msg, player);
	}
	
	public void askPlayerToSelectPlayer(Player player) {
		String msg = "Choose player to play this card against to ";
		List<Player> players = playerService.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			msg += " [" + i + "] " + players.get(i).getName(); 					
		}
		sendMessageToPlayer(msg, player);
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
	public synchronized void sendMessageToPlayer(String msg, Player player) {
		msgService.addMessage("Private message to player " + player.getName() + " : " + msg, player);
	}
	
	public synchronized void broadCastToPlayers(String msg) {
		System.out.println ("Broadcasting to players");
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

	public String getGameSituation() {
		// TODO Auto-generated method stub
		return null;
	}


}
