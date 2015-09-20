package fi.hpgame.gameLogic;

import fi.hpgame.gameLogic.cards.Card;

public class GameEvent {
	
	private EventType type;
	
	private Player player;
	
	private Player targetPlayer;
	
	private Card card;
	
	private String extraParameters;

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getTargetPlayer() {
		return targetPlayer;
	}

	public void setTargetPlayer(Player targetPlayer) {
		this.targetPlayer = targetPlayer;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getExtraParameters() {
		return extraParameters;
	}

	public void setExtraParameters(String extraParameters) {
		this.extraParameters = extraParameters;
	}

	public static GameEvent buildPlayCard(Card card, Player player1, Player player2,
			String additionalParameters) throws GameException {
		GameEvent event = new GameEvent();
		if (card == null || player1 == null) {
			throw new GameException("The play card event must have at least card and one player.");
		} 
		if (card.requiresTargetPlayer() && player2 == null) {
			throw new GameException("The card requires targetPlayer.");	
		}
		if (card.requiresExtraParemeters() && additionalParameters == null ) {
			throw new GameException("The card requires extra parameters");
		}
		event.setType(EventType.PLAYCARD);
		event.setCard(card);
		event.setPlayer(player1);
		event.setTargetPlayer(player2);
		event.setExtraParameters(additionalParameters);
		return event;
	}

	public String getMessage() throws GameException {
		
		switch(this.type) {
			case PLAYCARD:
				String msg = player.getName()
				+ " played card " + card.getName();
				if (card.requiresTargetPlayer()) {
					msg += " against " + targetPlayer.getName();
				} 
				if (card.requiresExtraParemeters()) {
					msg += " with parameters " + extraParameters;
				}
				return msg;
				
			default:
				throw new GameException("Unknown game type");
			
			}
	}
}
