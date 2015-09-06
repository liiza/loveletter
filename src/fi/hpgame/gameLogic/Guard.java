package fi.hpgame.gameLogic;

public class Guard extends Card {

	private static String GUARD = "Guard";
	
	public Guard(GameController game) {
		super(GUARD, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters) throws GameException {
		if (player2.getHand().contains(additionalParameters)) {
			game.removePlayer(player2);
			sendMessageToPlayer("The player " + player1.getName() + " guessed correctly that you have card " + additionalParameters, player2);
			sendMessageToPlayer("Your guess was correct.", player1);
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
				+ "The card must be one of the followings: Guard, King, Maid or Priest";
	}
}
