package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Princess extends Card {

	private static final String PRINCESS = "Princess";
	
	private static final int points = 8;

	public static final int howMany = 1;

	public Princess(GameController game) {
		super(PRINCESS, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {
		sendMessageToPlayer("You played card Princess and dropped out. ", player1);
		game.playerDropped(player1);

	}

	@Override
	public Cards getType() {
		return Cards.PRINCESS;
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
