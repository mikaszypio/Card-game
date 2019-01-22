package cardgame.game.model.cards;

import cardgame.game.gra;
import cardgame.game.model.gracz;
import java.util.List;

public class gatling extends karta{
	
	public gatling(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public gatling(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		List<gracz> gracze = gra.dajGraczy();
		for(gracz g : gracze) {
			g.postrzel();
		}		
		return true;
	}
}
