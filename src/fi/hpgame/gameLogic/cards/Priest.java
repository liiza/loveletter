package fi.hpgame.gameLogic.cards;

import fi.hpgame.gameLogic.Cards;
import fi.hpgame.gameLogic.GameController;
import fi.hpgame.gameLogic.Player;

public class Priest extends Card{

	private static String PRIEST = "Priest";
	
	private static int points = 2;

	public static int howMany = 2;

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

	@Override
	public int getPoints() {
		return points;
	}

	@Override
	public int howManyToAreInDeck() {
		return howMany ;
	}
	

}
