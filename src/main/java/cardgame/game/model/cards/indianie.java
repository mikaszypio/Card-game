package cardgame.game.model.cards;

import cardgame.game.gra;
import cardgame.game.model.gracz;
import java.util.List;

public class indianie extends karta{
	
	public indianie(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public indianie(int id, String obraz, String opek, String naz, int num, String col) {
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
			boolean czy = g.testKarty("Bang", "Indianie");
			if(czy==false) {
				g.zran(1);
			}
		}	
		return true;
	}

}
