package fi.hpgame.gameLogic;

public abstract class Card {
	private String name;

	public Card(String s) {
		this.name = s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void play(Player player1, Player player2);
	
}
