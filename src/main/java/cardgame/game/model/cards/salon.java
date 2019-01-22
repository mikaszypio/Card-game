package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.gracz;
import java.util.List;

public class salon extends karta{

	public salon(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public salon(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		List<gracz> gracze = Gra.dajGraczy();
		for(gracz g : gracze) {
			g.lecz(1);
		}		
		return true;
	}
}
