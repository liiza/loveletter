package fi.hpgame.gameLogic;

import java.util.ArrayList;
import java.util.List;

import fi.hpgame.gameLogic.cards.Card;



public class Player {
	
	private String name;
	
	private List<Card> cards =  new ArrayList<Card>();

	private boolean protection = false;

	private boolean stillPlaying = true;
	
	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void giveCard(Card card) {
		cards.add(card);
		
	}
	public List<Card> getCards() {
		return this.cards;
	}

	public String getHand(){
		String hand = "";
		for (Card card : this.cards) {
			hand += card.getName() + " ";
		}
		return hand;
	}
	
	public void playCard(Card card, Player player2, String additionalParameters) throws GameException {
		card.play(this, player2, additionalParameters);
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void setProtection(boolean protection) {
		this.protection = protection;
	}
	
	public boolean hasProtection() {
		return protection ;
	}

	public Card getCard(int cardIndex) throws GameException {
		if (cardIndex < 0 || cardIndex >= cards.size()) {
			throw new GameException("Give a card index that is in range of 0 to "
					+ (cards.size() - 1));
		
		} else {
			return cards.get(cardIndex);
		}
	}

	public void setStillPlaying(boolean b) {
		this.stillPlaying  = b;
	}
	
	public boolean isStillPlaying() {
		return stillPlaying;
	}

	public void discardCards() {
		this.cards.clear();
	}

	public boolean hasCard(Cards type) {
		for (Card card : cards) {
			if (card.getType() == type) {
				return true;
			}
		} return false;
	}
	
	
}
