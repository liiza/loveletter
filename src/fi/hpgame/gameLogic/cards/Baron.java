package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Baron extends Card {
	
	public static final String BARON = "Baron";
	
	private static final int points = 3;
	
	public static final int howMany = 2;	
	
	public Baron(GameController game) {
		super(BARON, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {
		Card card = player1.getCard(0);
			
		Card opponent = player2.getCard(0);
		String msgAboutBaronAction = player1.getName() + " played card " + card.getName() + " : " + card.getPoints() +
				" against card which is " + opponent.getName() + " : " + opponent.getPoints();
		
		sendMessageToPlayer(msgAboutBaronAction, player2);

		if (card.getPoints() > opponent.getPoints()) {
			game.playerDropped(player2);
		} else if (card.getPoints() < opponent.getPoints()){
			sendMessageToPlayer(msgAboutBaronAction, player1);
			game.playerDropped(player1);
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

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public int howManyToAreInDeck() {
		return howMany;
	}

}
