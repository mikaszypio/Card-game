package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;

public class welsfargo extends karta{
	
	public welsfargo(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public welsfargo(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		Gracz g = gra.dajAktualnegoGracza();
		g.dobiezKarte();
		g.dobiezKarte();
		g.dobiezKarte();
		return true;
	}
}
