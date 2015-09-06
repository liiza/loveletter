package fi.hpgame.gameLogic;

public abstract class Card {
	protected GameController game;
	private String name;

	public Card(String s, GameController game) {
		this.name = s;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected void sendMessageToPlayer(String msg, Player player) {
		game.sendMessageToPlayer(msg, player);
	}
	
	public abstract void play(Player player1, Player player2, String additionalParameters) throws GameException;
	
	public boolean requiresTargetPlayer() {
		return false;
	}
	
	public boolean requiresExtraParemeters() {
		return false;
	}
}
