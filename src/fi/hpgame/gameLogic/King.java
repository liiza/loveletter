package fi.hpgame.gameLogic;

import java.util.List;

public class King extends Card {

	public King(String s) {
		super(s);
	}

	@Override
	public void play(Player player1, Player player2) {
		List<Card> temp = player1.getCards();
		player1.setCards(player2.getCards());
		player2.setCards(temp);
	}

}
