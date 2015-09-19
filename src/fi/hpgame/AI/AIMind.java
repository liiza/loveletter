package fi.hpgame.AI;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class AIMind {

	GameController game;
	
	AIPlayer aiPlayer;
	
	public AIMind(GameController game, AIPlayer aiPlayer) {
		this.game = game;
		this.aiPlayer = aiPlayer;
	}

	public Decision giveDecision() {

		Card card = aiPlayer.getCards().get(0);
		
		Player targetPlayer = null;
		if (card.requiresTargetPlayer()) {
			try {
				targetPlayer =  game.getPlayer(0);
			} catch (GameException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String extraParameters = null;
		if (card.requiresExtraParemeters()){
			extraParameters =  "King";
		}

		
		return new Decision(card, targetPlayer, extraParameters);
	}

}
