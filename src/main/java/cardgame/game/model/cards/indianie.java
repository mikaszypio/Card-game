package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class indianie extends Card{
	
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
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		List<Gracz> gracze = players;
		for(Gracz g : gracze) {
			boolean czy = g.testKarty("bang", "Indianie", deck);
			if(czy==false) {
				g.zran(1, deck);
			}
		}	
		return true;
	}

}
