package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Baron extends Card {
	private static String BARON = "Baron";
	
	public Baron(GameController game) {
		super(BARON, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {
		for (Card card : player1.getCards()) {
			if (card.getType() != Cards.BARON) {
				if (card.getPoints() > player2.getCard(0).getPoints()) {
					game.playerDropped(player2);
				} else if (card.getPoints() < player2.getCard(0).getPoints()){
					game.playerDropped(player1);
				}
			}
		}
	}

	@Override 
	public boolean requiresTargetPlayer() {
		return true;
	}
	
	
	@Override
	public Cards getType() {
		return Cards.BARON;
	}

}
