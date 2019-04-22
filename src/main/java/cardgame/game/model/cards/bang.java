package cardgame.game.model.cards;

import cardgame.game.Interactions;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.List;

public class bang extends Card {
	
	public bang(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
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
	public boolean zagraj(Deck deck, List<Player> players,
		Player currentPlayer, Interactions interactions) {	
		Player cel = interactions.selectTargetPlayer(currentPlayer, players);
		Player strzelec = currentPlayer;
		Hero p = strzelec.getHero();
		String name = p.dajNazwe();
		if(strzelec.hasShot()==true)
			if(strzelec.isMultipleShooter()==false && name!="Willy the Kid") {
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
				cel.postrzelBardziej(deck, interactions);
			}else {
				cel.postrzel(deck, interactions);
			}
			return true;
		}
	}
}
