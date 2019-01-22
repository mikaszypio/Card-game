package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
import java.util.List;

public class salon extends karta{

	public salon(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public salon(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
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
		for(Gracz g : gracze) {
			g.lecz(1);
		}		
		return true;
	}
}
