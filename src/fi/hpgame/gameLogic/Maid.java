package fi.hpgame.gameLogic;

public class Maid extends Card {

	private static String MAID = "Maid";

	public Maid(GameController game) {
		super(MAID, game);
	}

	@Override
	public void play(Player player1, Player player2) {
		player1.setProtection(true);
	}

	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}

}
