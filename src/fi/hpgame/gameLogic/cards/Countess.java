package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Countess extends Card {

	private static final String COUNTESS = "Countess";

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

}
