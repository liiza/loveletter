package fi.hpgame.gameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
}
