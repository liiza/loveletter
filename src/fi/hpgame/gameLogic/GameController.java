package fi.hpgame.gameLogic;

import java.util.List;

import fi.hpgame.AI.AIPlayer;
import fi.hpgame.AI.Decision;
import fi.hpgame.gameLogic.cards.Card;
import fi.hpgame.messages.MessageService;
import fi.hpgame.server.PlayerCommunication;
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
		return game;
	}
	
	public synchronized void startGame() {
		cardService.initDeck(this);
		playerService.preparePlayersForGame();
		notify();
	}
	
	public synchronized void newRound() throws GameException {
		playerService.cardsDealed();
	}
	public synchronized void dealCards(int howMany) throws GameException {
		for (int i = 0; i < howMany; i++) {
			for(Player player : playerService.getPlayers()) {
				playerTakeCardFromDeck(player);
			}
		}
		playerService.cardsDealed();
	}
	public synchronized void playerInTurnPlays() throws GameException {
		Player player = playerService.getPlayerInTurn();
		playerTakeCardFromDeck(player);
		
		if (player.isAI()) {
			Decision decision = ((AIPlayer)player).makeDecision();
			playCard(decision.getCard(), player, decision.getTargetPlayer(), decision.getAdditionalParameters());
		} else {
			askPlayerToPlayCard(player);
		}
	
		
	}
	
	public synchronized  void playerTakeCardFromDeck(Player player) throws GameException{
		if (!cardService.isEmpty()){
			Card card = cardService.getFirstCard();
			playerService.giveCard(card, player);
			sendMessageToPlayer("You were given a card " + card.getName(),  player);
		}

	}

	public synchronized void askPlayerToPlayCard(Player player) throws GameException {
		String msg;
		
		List<Card> cards = player.getCards();
		
		if (player.hasCard(Cards.COUNTESSA) && (player.hasCard(Cards.KING) && player.hasCard(Cards.PRINCE))) {
			msg = "You must play card Countessa. ";
			for (int i = 0; i < cards.size(); i++) {
				if ( cards.get(i).getType() == Cards.COUNTESSA) {
					msg += " [" + i + "] " + cards.get(i).getName();					
				}
			}
		} else {
			msg = "Select a card to play ";
			for (int i = 0; i < cards.size(); i++) {
				msg += " [" + i + "] " + cards.get(i).getName();
			}
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
		msg += playerService.getListOfPlayers();
		
		sendMessageToPlayer(msg, player);
	}

	
	public void askPlayerToGiveExtraParameter(Player player, Card card) {
		if (!playerService.isPlayerInTurn(player)) {
			try {
				throw new GameException("Player is not in turn");
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sendMessageToPlayer(card.getExtraParameterDescription(), player);
		
	}
	
	public synchronized void playCard(Card card, Player player1, Player player2, String additionalParameters) {
		try {
			playerService.playCard(card, player1, player2, additionalParameters);
			String msg = player1.getName()
					+ " played card " + card.getName();
			if (card.requiresTargetPlayer()) {
				msg += " against " + player2.getName();
			}
			
			broadCastToPlayers(msg);
		
		} catch (GameException e) {
			e.printStackTrace();
		}
		
		notify();
	}
	public synchronized boolean allPlayersReady() {
		return playerService.allPlayersReady();
	}
	
	public synchronized void addAIPlayer(String name) {
		playerService.addPlayer(new AIPlayer(name, this));
	}
	
	public synchronized void addPlayer(Player player, PlayerCommunication communication) {
		playerService.addPlayer(player);
		new Thread(new WriterThread(communication, player, this)).start();
	}
	
	public synchronized void removePlayer(Player player) throws GameException {
		playerService.removePlayer(player);
		broadCastToPlayers("Player player " + player.getName() + " quit."); 
	}
	public synchronized Player getPlayer(int i) throws GameException {
		return playerService.getPlayer(i);
	}
	
	public synchronized boolean playerIsInGame(Player player) {
		return playerService.playerIsInGame(player);

	}
	public void playerDropped(Player player) {
		playerService.playerDropped(player);
		broadCastToPlayers("Player player " + player.getName() + " dropped out."); 
		
	}

	public synchronized boolean isPlayerInTurn(Player player) {
		return playerService.isPlayerInTurn(player);
	}

	
	public void sendMessageToPlayer(String msg, Player player) {
		if (!player.isAI()) {
			msgService.addMessage("Private message to player " + player.getName() + " : " + msg, player);			
		}

	}
	
	public void broadCastToPlayers(String msg) {
		System.out.println ("Broadcasting to players");
		msgService.addMessage("BroadCasted message " + msg, playerService.getNonAIPlayers());
	}


	public String getMessage(Player player) throws GameException {

		return msgService.getMessage(player);
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	private void putCard(Card card) {
		cardService.addCard(card);
	}

	public boolean gameIsOver() {
		return playerService.gameIsOver();
	}
	
	public String getWinner() throws GameException{
		return playerService.getWinner();
	}

	public boolean deckIsEmpty() {
		return cardService.isEmpty();
	}

	public String getWinnerInLastRound() throws GameException {
		Player player =  playerService.getPlayerWithHighestHand();
		if (player == null) {
			throw new GameException("Winner could not be determined for some reason");
		}
		return player.getName();
	}

	public void printAllPlayerHands() {
		broadCastToPlayers(playerService.getAllPlayerHands());
	}




	




}
