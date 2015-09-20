package fi.hpgame.gameLogic;

import fi.hpgame.gameLogic.cards.Baron;
import fi.hpgame.gameLogic.cards.Countess;
import fi.hpgame.gameLogic.cards.Guard;
import fi.hpgame.gameLogic.cards.King;
import fi.hpgame.gameLogic.cards.Maid;
import fi.hpgame.gameLogic.cards.Priest;
import fi.hpgame.gameLogic.cards.Prince;
import fi.hpgame.gameLogic.cards.Princess;

public enum Cards {
	COUNTESSA(Countess.COUNTESS), 
	KING(King.KING), 
	MAID(Maid.MAID), 
	PRIEST(Priest.PRIEST), 
	PRINCE(Prince.PRINCE), 
	PRINCESS(Princess.PRINCESS), 
	GUARD(Guard.GUARD), 
	BARON(Baron.BARON);
	
	public String name;
	
	Cards(String n) {
		this.name = n;
	}
	
}
