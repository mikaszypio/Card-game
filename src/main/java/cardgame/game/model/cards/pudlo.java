package cardgame.game.model.cards;

import cardgame.game.Interactions;
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
	public boolean zagraj(Deck deck, List<Player> players,
		Player currentPlayer, Interactions interactions) {
		Player strzelec = currentPlayer;
		Hero p = strzelec.getHero();
		String name = p.dajNazwe();
		if("Calamity Janet".equals(name)) {
			if(strzelec.hasShot()==true)
				if(strzelec.isMultipleShooter()==false) {
				System.out.print("Nie mo�esz strzela� ponownie");
				return false;				
			}
			
			Player cel = interactions.selectTargetPlayer(currentPlayer, players);
			if(cel == null) {
				return false;
			}
			
			int dystans = policzDystans(strzelec, cel, players);
			int zasieg = strzelec.zasieg() - cel.modZasiegu();
			if(dystans>zasieg) {
				System.out.print("Nie dostrzelisz");
				return false;
			} else {
				cel.postrzel(deck, interactions);
				cel.setShotStatus(true);
				return true;
			}
		}
		
		return false;
	}
}
