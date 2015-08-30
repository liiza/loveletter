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
		game.putCard(new Maid(game));

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
	
	public synchronized void askPlayerToPlayCard() throws GameException {
		String msg = "Select a card to play ";
		Player player = playerService.getPlayerInTurn();
		List<Card> cards = player.getCards();
		for (int i = 0; i < cards.size(); i++) {
			msg += " [" + i + "] " + cards.get(i).getName();
		}
		sendMessageToPlayer(msg, player);
	}
	
	public synchronized void askPlayerToSelectPlayer(Player player, Card card) {
		if (!playerService.isPlayerInTurn(player)) {
			try {
				throw new GameException("Player is not in turn");
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String msg = "Choose player to play card " + card.getName() +" against to ";
		List<Player> players = playerService.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).hasProtection()) {
				msg += " [" + i + "] " + players.get(i).getName(); 						
			}
			
		}
		sendMessageToPlayer(msg, player);
	}
	
	
	public synchronized void playCard(Card card, Player player1, Player player2) {
		try {
			playerService.playCard(card, player1,player2);
			broadCastToPlayers((player1.getName()
					+ " played card " + card.getName()
					+ " against " + player2.getName()));
		} catch (GameException e) {
			e.printStackTrace();
		}
		notify();
	}
	public synchronized boolean allPlayersReady() {
		return playerService.allPlayersReady();
	}
	private void putCard(Card card) {
		cardService.addCard(card);
	}
	
	public synchronized void addPlayer(Player player, PrintWriter output) {
		playerService.addPlayer(player);
		new Thread(new WriterThread(output, player, this)).start();
	}
	
	public synchronized void removePlayer(Player player) throws GameException {
		playerService.removePlayer(player);
	}
	public synchronized Player getPlayer(int i) throws GameException {
		return playerService.getPlayer(i);
	}
	
	public synchronized boolean playerIsInGame(Player player) {
		return playerService.playerIsInGame(player);

	}

	public synchronized boolean isPlayerInTurn(Player player) {
		return playerService.isPlayerInTurn(player);
	}

	
	public void sendMessageToPlayer(String msg, Player player) {
		msgService.addMessage("Private message to player " + player.getName() + " : " + msg, player);
	}
	
	public void broadCastToPlayers(String msg) {
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




}
