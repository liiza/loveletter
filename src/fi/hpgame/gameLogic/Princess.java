package fi.hpgame.gameLogic;

public class Princess extends Card {

	private static final String PRINCESS = "Princess";

	public Princess(GameController game) {
		super(PRINCESS, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {
		sendMessageToPlayer("You played card Princess and dropped out. ", player1);
		game.playerDropped(player1);

	}

}
