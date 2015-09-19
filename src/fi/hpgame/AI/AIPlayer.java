package fi.hpgame.AI;

import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.Player;

public class AIPlayer extends Player {

	public AIMind aimind;
	private GameController game;
	
	public AIPlayer(String name, GameController game) {
		super(name);
		this.game = game;
		this.aimind = new AIMind(game, this);
	}
	public boolean isAI() {
		return true;
	}
	public void makeDecision() {
		Decision decision = aimind.giveDecision();
		game.playCard(decision.getCard(), this, decision.getTargetPlayer(), decision.getAdditionalParameters());
		
	}

	

}
