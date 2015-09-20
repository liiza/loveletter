package fi.hpgame.AI.util;

import java.util.HashMap;
import java.util.Map;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.cards.*;


public final class AILogic {

	public static final Double cardsInDeck = 16.0;

	
	public static Map<Cards, Double> getProbabilitiesAtBeginning() throws GameException {
		HashMap<Cards, Double> probabilities = new HashMap<Cards, Double>();
		for (Cards cardType : Cards.values()) {
			switch (cardType) {
				case PRINCESS:
					probabilities.put(cardType, Princess.howMany/cardsInDeck);
					
				case KING:
					probabilities.put(cardType, King.howMany/cardsInDeck);
				
				case PRINCE:
					probabilities.put(cardType, Prince.howMany/cardsInDeck);
				
				case COUNTESSA:
					probabilities.put(cardType, Countess.howMany/cardsInDeck);
				
				case PRIEST:
					probabilities.put(cardType, Priest.howMany/cardsInDeck);
				
				case GUARD:
					probabilities.put(cardType, Guard.howMany/cardsInDeck);
				
				case MAID:
					probabilities.put(cardType, Maid.howMany/cardsInDeck);
				
				case BARON:
					probabilities.put(cardType, Baron.howMany/cardsInDeck);
					
				default:
					throw new GameException("Non existing card type");
					
			}
		
		}
		return probabilities;

	}
}
