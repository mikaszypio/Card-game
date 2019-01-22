package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.cards.karta;
import cardgame.game.model.gracz;

public class wiezienie extends karta{
	
	public wiezienie(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public wiezienie(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz g = kontakt.wybiezCel();
		g.doPaki();
		return true;
	}

}
