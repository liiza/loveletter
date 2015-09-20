package fi.hpgame.AI.util;

import fi.hpgame.gameLogic.Player;
import fi.hpgame.gameLogic.cards.Card;

public class Decision {

	private Card card;
	private Player targetPlayer;
	private String extraParameters;
	
	private Double advisableValue;

	public Decision(Card card, Player targetPlayer, String extraParameters, Double advisableValue) {
		this.card = card;
		this.targetPlayer = targetPlayer;
		this.extraParameters = extraParameters;
		this.advisableValue = advisableValue;
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

	public Double getAdvisableValue() {
		return advisableValue;
	}

	public void setAdvisableValue(Double advisableValue) {
		this.advisableValue = advisableValue;
	}

}
