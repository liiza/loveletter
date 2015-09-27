package fi.hpgame.AI.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;
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

	public static Double advisibalityToPlayBaron(Card card, Map<Cards, Double> probabilities) {
		Double a = 0.0;
		switch(card.getType()) {
			case PRINCESS:
				a = 0.9;
			case COUNTESSA:
				a = 0.8;
				if (isNoLongerInGame(probabilities, Cards.PRINCESS)) {
					a = 0.9;
				}
			case KING:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA)) {
					a = 0.9;
				} else if (isNoLongerInGame(probabilities, Cards.PRINCESS) || isNoLongerInGame(probabilities, Cards.COUNTESSA)){
					a = 0.8;
				} else {
					a = 0.7;
				}
			case PRINCE:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA, Cards.KING)) {
					a = 0.9;
				} else if (isNoLongerInGame(probabilities, Cards.PRINCESS) || isNoLongerInGame(probabilities, Cards.COUNTESSA) || isNoLongerInGame(probabilities, Cards.KING) ){
					a = 0.6;
				} else {
					a = 0.5;
				}
			case BARON:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA, Cards.KING, Cards.PRINCE)) {
					a = 0.9;
				} else {
					a= 0.5;
				}
			case PRIEST:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA, Cards.KING, Cards.PRINCE, Cards.BARON)) {
					a = 0.9;
				} else {
					a= 0.1;
				}
			case MAID:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA, Cards.KING, Cards.PRINCE, Cards.BARON, Cards.PRIEST)) {
					a = 0.9;
				} else {
					a= 0.1;
				}
			case GUARD:
				if (isNoLongerInGame(probabilities, Cards.PRINCESS, Cards.COUNTESSA, Cards.KING, Cards.PRINCE, Cards.BARON, Cards.MAID)) {
					a = 0.9;
				} else {
					a= 0.1;
				}
			
		}
		return a;
	}

	private static boolean isNoLongerInGame(Map<Cards, Double> probabilities, Cards... types) {
		boolean isNoLongerInGame = true;
		for (Cards cardType : types) {
			isNoLongerInGame = isNoLongerInGame && (probabilities.get(cardType) == 0.0);
		}
		return isNoLongerInGame;
	}
}
