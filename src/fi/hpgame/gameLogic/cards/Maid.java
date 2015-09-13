package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.Player;

public class Maid extends Card {

	private static final String MAID = "Maid";
	
	private static final int points = 4;
	
	public static final int howMany = 2;

	public Maid(GameController game) {
		super(MAID, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters) {
		player1.setProtection(true);
	}

	@Override
	public boolean requiresTargetPlayer() {
		return false;
	}

	@Override
	public Cards getType() {
		return Cards.MAID;
	}

	@Override
	public int getPoints() {
		return points ;
	}

	@Override
	public int howManyToAreInDeck() {
		return howMany;
	}

}
