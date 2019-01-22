package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.gracz;
import java.util.List;

public class gatling extends karta{
	
	public gatling(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public gatling(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		List<gracz> gracze = gra.dajGraczy();
		gracz aktualny = gra.dajAktualnegoGracza();
		for(gracz g : gracze) {
			if(g!=aktualny) {g.postrzel();}
		}		
		return true;
	}
}
