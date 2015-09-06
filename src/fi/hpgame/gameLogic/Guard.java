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

}
