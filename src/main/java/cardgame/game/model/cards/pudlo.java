package cardgame.game.model.cards;

import cardgame.game.kontakt;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
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
	public boolean zagraj(Deck deck, List<Player> players, Player currentPlayer) {
		Player strzelec = currentPlayer;
		Hero p = strzelec.getHero();
		String name = p.dajNazwe();
		if(name=="Calamity Janet") {
			if(strzelec.hasShot()==true)
				if(strzelec.isMultipleShooter()==false) {
				System.out.print("Nie mo�esz strzela� ponownie");
				return false;				
			}
			Player cel = kontakt.wybiezCel(players);
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
