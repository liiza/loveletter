package fi.hpgame.gameLogic;

public class Guard extends Card {

	private static String GUARD = "Guard";
	
	public Guard(GameController game) {
		super(GUARD, game);
	}

	@Override
	public void play(Player player1, Player player2) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}

}
