package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class wiezienie extends Card{
	
	public wiezienie(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public wiezienie(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		Gracz g = kontakt.wybiezCel(players);
		g.doPaki();
		return true;
	}

}
