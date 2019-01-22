package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.gracz;

public class piwko extends karta {
	
	public piwko(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public piwko(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		gracz g = gra.dajAktualnegoGracza();
		g.lecz(1);
		return true;
	}
}
