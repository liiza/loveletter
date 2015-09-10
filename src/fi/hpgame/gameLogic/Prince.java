package fi.hpgame.gameLogic;

public class Prince extends Card {

	private static final String PRINCE = "Prince";

	public Prince(GameController game) {
		super(PRINCE, game);
	}

	@Override
	public void play(Player player1, Player player2, String additionalParameters)
			throws GameException {
		player2.discardCards();
		game.playerTakeCardFromDeck(player2);

	}
	
	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}

}
