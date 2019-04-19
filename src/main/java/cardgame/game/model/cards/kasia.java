package cardgame.game.model.cards;

import cardgame.game.Game;
import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.List;

public class kasia extends Card{
	
	public kasia(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public kasia(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Player> players, Player currentPlayer) {
		Player cel = kontakt.wybiezCel(players);
		String co = kontakt.coChceszZniszczyc();
		if(co=="bron") { 
			Equipment bron = cel.getWeapon();
			cel.setWeapon(null);
			//gra.odzuc(bron);
			deck.rejectCard(bron);
		}
		if(co=="dodatek") { 
			Equipment doda = cel.getSupportItem();
			cel.setSupportItem(null);
			//gra.odzuc(doda);
			deck.rejectCard(doda);
		}
		if(co=="karta") {
			List<Card> reka = cel.dajReke();
			Card odrzucona = kontakt.wybiezKarte(reka);
			reka.remove(odrzucona);
			//gra.odzuc(odrzucona);
			deck.rejectCard(odrzucona);
		}
		return true;
	}

}