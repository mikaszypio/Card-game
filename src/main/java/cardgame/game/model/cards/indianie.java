package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.Gracz;
import java.util.List;

public class indianie extends Card{
	
	public indianie(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public indianie(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
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
			boolean czy = g.testKarty("Bang", "Indianie");
			if(czy==false) {
				g.zran(1);
			}
		}	
		return true;
	}

}
