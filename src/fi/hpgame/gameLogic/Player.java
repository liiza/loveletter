package fi.hpgame.gameLogic;

import java.util.ArrayList;
import java.util.List;



public class Player {
	
	private String name;
	
	private List<Card> cards =  new ArrayList<Card>();
	
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

	public void playCard(Card card, Player player2) {
		card.play(this, player2);
		
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
		
	}
	
}
