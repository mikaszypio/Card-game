package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class gatling extends Card{
	
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
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		List<Gracz> gracze = players;
		Gracz aktualny = currentPlayer;
		for(Gracz g : gracze) {
			if(g!=aktualny) {
				g.postrzel(deck);
			}
		}		
		return true;
	}
}
