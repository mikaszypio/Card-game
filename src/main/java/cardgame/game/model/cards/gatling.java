package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
import java.util.List;

public class gatling extends Card{
	
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
		List<Gracz> gracze = gra.dajGraczy();
		Gracz aktualny = gra.dajAktualnegoGracza();
		for(Gracz g : gracze) {
			if(g!=aktualny) {g.postrzel();}
		}		
		return true;
	}
}
