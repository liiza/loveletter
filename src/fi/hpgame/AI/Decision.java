package fi.hpgame.AI;

import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class Decision {

	private Card card;
	private Player targetPlayer;
	private String extraParameters;

	public Decision(Card card, Player targetPlayer, String extraParameters) {
		this.card = card;
		this.targetPlayer = targetPlayer;
		this.extraParameters = extraParameters;
	}

	public Card getCard() {
		return card;
	}

	public Player getTargetPlayer() {
		return targetPlayer;
	}

	public String getAdditionalParameters() {
		return extraParameters;
	}

}
