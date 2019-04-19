package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class pudlo extends Card{

	public pudlo(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public pudlo(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		Gracz strzelec = currentPlayer;
		Postac p = strzelec.dajPostac();
		String name = p.dajNazwe();
		if(name=="Calamity Janet") {
			if(strzelec.czyStrzelal()==true)
				if(strzelec.wielostrzal()==false) {
				System.out.print("Nie mo�esz strzela� ponownie");
				return false;				
			}
			Gracz cel = kontakt.wybiezCel(players);
			int dystans = policzDystans(strzelec, cel, players);
			int zasieg = strzelec.zasieg() - cel.modZasiegu();
			if(dystans>zasieg) {
				System.out.print("Nie dostrzelisz");
				return false;
			}else {
				cel.postrzel(deck);
				return true;
			}
		}else {
			return false;
		}
	}
}
