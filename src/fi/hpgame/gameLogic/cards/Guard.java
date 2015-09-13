package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.GameException;
import fi.hpgame.gameLogic.Player;

public class Guard extends Card {

	private static final String GUARD = "Guard";
	
	private static final Cards type = Cards.GUARD;

	private static int points = 1;
	
	public Guard(GameController game) {
		super(GUARD, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters) throws GameException {
		if (GUARD.equals(additionalParameters)) {
			sendMessageToPlayer("You can't guess for guard. Your turn dismissed.", player1);
		}
		else if (player2.getHand().contains(additionalParameters)) {
			sendMessageToPlayer("The player " + player1.getName() + " guessed correctly that you have card " + additionalParameters, player2);
			sendMessageToPlayer("Your guess was correct.", player1);
			game.playerDropped(player2);
		}
		else {
			sendMessageToPlayer("Your guess was incorrect. Player " + player2.getName() +
					" doesn't have card " + additionalParameters, player1);
		}

	}

	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}
	
	@Override 
	public boolean requiresExtraParemeters(){
		return true;
	}

	@Override 
	public String getExtraParameterDescription() {
		return "Give the card you except the player to have. \n"
				+ "The card must be one of the followings: King, Maid or Priest";
	}

	@Override
	public Cards getType() {
		return type;
	}

	@Override
	public int getPoints() {
		return points ;
	}
}
