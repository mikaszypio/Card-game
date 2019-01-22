package cardgame.game.model.cards;

import cardgame.game.model.gracz;
import cardgame.game.kontakt;

public class dynamit extends karta{
	
	public dynamit(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public dynamit(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz g =kontakt.wybiezCel();
		g.dostanDynamit();
		return true;
	}
}
