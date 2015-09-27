package fi.hpgame.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

	private static final double MUST_PLAY = 1.0;

	private GameController game;
	
	private AIPlayer aiPlayer;
	
	private List<GameEvent> events = new ArrayList<GameEvent>();
	
	private Map<Cards, Integer> cardsPlayed = new HashMap<Cards, Integer>(); 
	
	public AIMind(GameController game, AIPlayer aiPlayer) {
		this.game = game;
		this.aiPlayer = aiPlayer;

		initCardsPlayed();
		
	}

	private void initCardsPlayed(){
		for (Cards cardType : Cards.values()) {
			cardsPlayed.put(cardType, 0);
		}
	}


	public Decision giveDecision() {
		List<Decision> decisions = new ArrayList<Decision>();
		
		boolean allOtherPlayersHaveProtection = game.getPlayersWithoutProtection().size() == 1 && game.getPlayersWithoutProtection().get(0) == aiPlayer;
		if (allOtherPlayersHaveProtection ) {
			for (Card card : aiPlayer.getCards()) {
				for (Cards cardType: Cards.values()) {
					decisions.add(getDecisionWithAdvisability(card, aiPlayer, cardType));
				}
			}
		} 
		else {
			for (Card card : aiPlayer.getCards()) {
				for (Player player: game.getPlayersWithoutProtection()){
					if (!player.hasProtection() && (card.getType() != Cards.MAID && player != aiPlayer)) {
						for (Cards cardType: Cards.values()) {
							decisions.add(getDecisionWithAdvisability(card, player, cardType));
						}
					}
				}
			}
			
		}
	
		
		return bestDecision(decisions);
	}

	public void processEvent(GameEvent event) {

		Cards cardType = event.getCard().getType();
		cardsPlayed.put(cardType, cardsPlayed.get(cardType) + 1);
	
		events.add(event);
		
	}

	private Decision getDecisionWithAdvisability(Card card, Player targetPlayer, Cards cardType) {
		Double advisibality = 0.5;
		
		switch (card.getType()) {
			// Playing princess is ultimately a bad idea
			case PRINCESS:
				advisibality = 0.0;
				return new Decision(card, null, null, advisibality);
			// Playing King is most likely a bad idea
			case KING:
				advisibality = 0.2;
				if (this.aiPlayer.hasCard(Cards.GUARD)) {
					advisibality = 0.0;
				}
				
				return new Decision(card, targetPlayer, null, advisibality);	
			case COUNTESSA:
				// Has to play Countessa if prince of king is in hand
				if (this.aiPlayer.hasCard(Cards.PRINCE) || this.aiPlayer.hasCard(Cards.KING)) {
					advisibality = MUST_PLAY;
				}
				return new Decision(card, null, null, advisibality);	
			case GUARD:
				advisibality = Math.min(getProbabilityForCardType(cardType) + 0.1, 0.9);
				return new Decision(card, targetPlayer, cardType.name, advisibality);
				
			case BARON:
				advisibality = AILogic.advisibalityToPlayBaron(getCardToUseInBaronAttack(aiPlayer), AILogic.getProbabilitiesForCardTypes(cardsPlayedOrInOwnHand())); 
				return new Decision(card, targetPlayer, cardType.name, advisibality);
			default:
				return new Decision(card, targetPlayer, cardType.name, advisibality);
		}
	
	}

	private Card getCardToUseInBaronAttack(AIPlayer player) {
		for (Card card : player.getCards()) {
			if (card.getType()!= Cards.BARON) {
				return card;
			}
		}
		return player.getCards().get(0);
	}

	private Double getProbabilityForCardType(Cards type) {
		Map<Cards, Double> probabilities = AILogic.getProbabilitiesForCardTypes(cardsPlayedOrInOwnHand());
		
		return probabilities.get(type);
	}

	private Map<Cards, Integer> cardsPlayedOrInOwnHand() {
		Map<Cards, Integer> cards = new HashMap<Cards, Integer>();
		cards.putAll(cardsPlayed);
		for (Card card : aiPlayer.getCards()) {
			cards.put(card.getType(), cards.get(card.getType()) + 1 );
		}
		return cards;
	}
	
	private Decision bestDecision(List<Decision> decisions) {

		for (Decision decision :decisions ) {
			System.out.println(decision.toString());
		}
		Collections.sort(decisions, new Comparator<Decision>() {
		    public int compare(Decision d1, Decision d2) {
		        return Double.compare(d1.getAdvisableValue(), d2.getAdvisableValue());
		    }	
		});

		return decisions.get(decisions.size() - 1);
	}
	

}
