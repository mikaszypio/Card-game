package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class bang extends Card {
	
	public bang(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		//gra=g;
	}
	
	public bang(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {		
		Gracz cel = kontakt.wybiezCel(players);
		Gracz strzelec = currentPlayer;
		Postac p = strzelec.dajPostac();
		String name = p.dajNazwe();
		if(strzelec.czyStrzelal()==true)
			if(strzelec.wielostrzal()==false && name!="Willy the Kid") {
			System.out.print("Nie mo�esz strzela� ponownie");
			return false;				
		}
		
		int dystans = policzDystans(strzelec, cel, players);
		int zasieg = strzelec.zasieg() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie dostrzelisz");
			return false;
		} else {
			if(name=="Slab Zabojca") {
				cel.postrzelBardziej(deck);
			}else {
				cel.postrzel(deck);
			}
			return true;
		}
	}
}
