package cardgame.game.model.cards;

import cardgame.game.Game;
import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.List;
import java.util.Random;

public class panika extends Card{
	
	public panika(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public panika(int id, String obraz, String opek, String naz, int num, String col) {
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
		Player strzelec = currentPlayer;
		int dystans = policzDystans(strzelec, cel, players);
		int zasieg = strzelec.zasiegCzysty() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie si�gniesz");
			return false;
		}else {
			String co = kontakt.coChceszZniszczyc();
			if(co=="bron") { 
				Equipment bron = cel.getWeapon();
				cel.setWeapon(null);
				strzelec.addToHand(bron);
			}
			if(co=="dodatek") { 
				Equipment doda = cel.getSupportItem();
				cel.setSupportItem(null);
				strzelec.addToHand(doda);
			}
			if(co=="karta") {
				List<Card> reka = cel.dajReke();
				Random rand = new Random();
				Card wynik = reka.get(rand.nextInt(reka.size()));
				reka.remove(wynik);
				strzelec.addToHand(wynik);		
			}
			return true;
		}
	}
}