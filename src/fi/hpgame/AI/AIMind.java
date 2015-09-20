package fi.hpgame.AI;

import java.util.ArrayList;
import java.util.List;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameEvent;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class AIMind {

	GameController game;
	
	AIPlayer aiPlayer;
	
	List<GameEvent> events = new ArrayList<GameEvent>();
	
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

	public void processEvent(GameEvent event) {
		this.events.add(event);
		
	}

}
