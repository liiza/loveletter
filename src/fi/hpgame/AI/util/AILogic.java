package fi.hpgame.AI.util;

import java.util.HashMap;
import java.util.Map;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.cards.*;


public final class AILogic {

	public static final int initialCards = 16;
	
	public static Map<Cards, Double> getProbabilitiesForCardTypes(Map<Cards, Integer> cardsPlayed){
		int cardsLeft = initialCards -  cardsPlayed.size();
		HashMap<Cards, Double> probabilities = new HashMap<Cards, Double>();
		for (Cards cardType : Cards.values()) {
			
			switch (cardType) {
				case PRINCESS:
					addProbability(cardType, probabilities, Princess.howMany, cardsPlayed, cardsLeft);
					
				case KING:
					addProbability(cardType, probabilities, King.howMany, cardsPlayed, cardsLeft);

				case PRINCE:
					addProbability(cardType, probabilities, Prince.howMany, cardsPlayed, cardsLeft);

				case COUNTESSA:
					addProbability(cardType, probabilities, Countess.howMany, cardsPlayed, cardsLeft);
				
				case PRIEST:
					addProbability(cardType, probabilities, Priest.howMany, cardsPlayed, cardsLeft);
				
				case GUARD:
					addProbability(cardType, probabilities, Guard.howMany, cardsPlayed, cardsLeft);
				
				case MAID:
					addProbability(cardType, probabilities, Maid.howMany, cardsPlayed, cardsLeft);
				
				case BARON:
					addProbability(cardType, probabilities, Baron.howMany, cardsPlayed, cardsLeft);
					
				default:
					
					
			}
		
		}
		return probabilities;

	}

	private static void addProbability(Cards cardType, HashMap<Cards, Double> probabilities,
			int initialAmount, Map<Cards, Integer> cardsPlayed, int cardsInDeck) {
		int cardsPlayedOfType = cardsPlayed.get(cardType);
		probabilities.put(cardType, (initialAmount - cardsPlayedOfType)/ (double)cardsInDeck);
		
	}
}
