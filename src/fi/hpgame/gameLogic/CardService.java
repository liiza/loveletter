package fi.hpgame.gameLogic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fi.hpgame.gameLogic.cards.Baron;
import fi.hpgame.gameLogic.cards.Card;
import fi.hpgame.gameLogic.cards.Countess;
import fi.hpgame.gameLogic.cards.Guard;
import fi.hpgame.gameLogic.cards.King;
import fi.hpgame.gameLogic.cards.Maid;
import fi.hpgame.gameLogic.cards.Priest;
import fi.hpgame.gameLogic.cards.Prince;
import fi.hpgame.gameLogic.cards.Princess;

public class CardService {


	private List<Card> cards = Collections.synchronizedList(new ArrayList<Card>());

	public void addCard(Card card) {
		cards.add(card);
	}
	
	public synchronized Card getFirstCard() throws GameException {
		if (cards.isEmpty()) {
			throw new GameException("Trying to take card from empty stack."); 
		}
		Random rand = new Random();
		int randomNum = rand.nextInt(cards.size());
		return cards.remove(randomNum);
	}
	
	public boolean isEmpty(){
		return cards.isEmpty();
	}
	
	// For server logging purposes
	public void printAllCards(){
		for (Card card: cards) {
			System.out.println(card.getName());
		}
	}

	public void initDeck(GameController game) {
		cards.clear();
		
		for (Cards type : Cards.values()) {
			try {
				addCardsOfType(type, game);
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	private void addCardsOfType(Cards type, GameController game) throws GameException {

		switch(type) {
			case KING:
				addCards(King.class, King.howMany, game);
				break;
			case GUARD:
				addCards(Guard.class, Guard.howMany, game);
				break;
			case BARON:
				addCards(Baron.class, Baron.howMany, game);
				break;
			case PRINCESS:
				addCards(Princess.class, Princess.howMany, game);
				break;
			case MAID:
				addCards(Maid.class, Maid.howMany, game);
				break;
			case PRINCE:
				addCards(Prince.class, Prince.howMany, game);
				break;
			case PRIEST:
				addCards(Priest.class, Priest.howMany, game);
				break;
			case COUNTESSA:
				addCards(Countess.class, Countess.howMany, game);
				break;
			default:
				throw new GameException("Trying to initialize non existing card.");
			
		}
	
	}

	private void addCards(Class<?> clazz, int howMany, GameController game) {
		Constructor<?> constructor;
		try {
			constructor = clazz.getConstructor(String.class);
			for (int i = 0; i <howMany; i++) {
				cards.add((Card) constructor.newInstance(game));
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
