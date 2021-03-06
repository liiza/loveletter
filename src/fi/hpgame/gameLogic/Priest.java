package fi.hpgame.gameLogic;

public class Priest extends Card{

	private static String PRIEST = "Priest";

	public Priest(GameController game) {
		super(PRIEST, game);
	}

	@Override
	public void play(Player player1, Player player2,  String additionalParameters) {
		sendMessageToPlayer(player2.getName() + " has following cards: " + player2.getHand(), player1);
		
	}

	@Override
	public boolean requiresTargetPlayer() {
		return true;
	}

	@Override
	public Cards getType() {
		return Cards.PRIEST;
	}
	

}
