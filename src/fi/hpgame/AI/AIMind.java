package fi.hpgame.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hpgame.AI.util.AILogic;
import fi.hpgame.AI.util.Decision;
import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.EventType;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameEvent;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class AIMind {

	private GameController game;
	
	private AIPlayer aiPlayer;
	
	private List<GameEvent> events = new ArrayList<GameEvent>();
	
	private int cardsLeft = 16;
	
	private Map<Player, Map<Cards, Double>> otherPlayers = new HashMap<Player, Map<Cards, Double>>();
	
	public AIMind(GameController game, AIPlayer aiPlayer) {
		this.game = game;
		this.aiPlayer = aiPlayer;
		initOtherPlayers();
	}

	private void initOtherPlayers() {
		for (Player player: game.getPlayers()) {
			try {
				otherPlayers.put(player, AILogic.getProbabilitiesAtBeginning());
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public Decision giveDecision() {
		List<Decision> decisions = new ArrayList<Decision>();
		
		for (Card card : aiPlayer.getCards()) {
			for (Player player: game.getPlayers()){
				if (!player.hasProtection()) {
					for (Cards cardType: Cards.values()) {
						decisions.add(getDecisionWithAdvisability(card, player, cardType));
					}
				}
				
			}
		}
		
		return bestDecision(decisions);
	}

	public void processEvent(GameEvent event) {
		if (event.getType().equals(EventType.PLAYCARD)) {
			this.cardsLeft--;
		}
		this.events.add(event);
		
	}

	private Decision getDecisionWithAdvisability(Card card, Player targetPlayer, Cards cardType) {
		Double advisibality = 0.5;
		switch (card.getType()) {
			// Playing princess is ultimately a bad idea
			case PRINCESS:
				return new Decision(card, null, null, 0.0);
			// Playing King is most likely a bad idea
			case KING:
				advisibality = 0.2;
				if (this.aiPlayer.hasCard(Cards.GUARD)) {
					advisibality = 0.0;
				}
				return new Decision(card, targetPlayer, null, advisibality);	
			case COUNTESSA:
				// Has to play this card
				if (this.aiPlayer.hasCard(Cards.PRINCE) || this.aiPlayer.hasCard(Cards.KING)) {
					advisibality = 1.0;
				}
				return new Decision(card, null, null, advisibality);	
			
			default:
				return new Decision(card, targetPlayer, cardType.name, advisibality);
		}
	
	}

	private Decision bestDecision(List<Decision> decisions) {
	
		Collections.sort(decisions, new Comparator<Decision>() {
		    public int compare(Decision d1, Decision d2) {
		        return Double.compare(d1.getAdvisableValue(), d2.getAdvisableValue());
		    }	
		});

		return decisions.get(0);
	}
	

}
