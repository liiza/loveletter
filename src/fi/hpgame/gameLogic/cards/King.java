package fi.hpgame.gameLogic.cards;

import java.util.List;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.Player;

public class King extends Card {

	private static final String KING = "King";
	
	private static final int points = 6;

	public static final int howMany = 1;
	
	public King(GameController game) {
		super(KING, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters) {
		List<Card> temp = player1.getCards();
		player1.setCards(player2.getCards());
		player2.setCards(temp);
	}

	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}

	@Override
	public Cards getType() {
		return Cards.KING;
	}

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public int howManyToAreInDeck() {
		return howMany;
	}
	

}
