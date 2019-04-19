package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Player;
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
	public boolean zagraj(Deck deck, List<Player> players, Player currentPlayer) {
		List<Player> gracze = players;
		for(Player g : gracze) {
			boolean czy = g.testCard("bang", "Indianie", deck);
			if(czy==false) {
				g.hurt(1, deck);
			}
		}	
		return true;
	}

}
