package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Countess extends Card {

	public static final String COUNTESS = "Countess";
	
	private static final int points = 7;

	public static final int howMany = 1;

	public Countess(GameController game) {
		super(COUNTESS, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {

	}

	@Override
	public Cards getType() {
		return Cards.COUNTESSA;
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
