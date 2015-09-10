package fi.hpgame.gameLogic;

import java.util.List;

public class King extends Card {

	private static String KING = "King";

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

}
