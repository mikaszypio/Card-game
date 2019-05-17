package cardgame.game.model.cards;

import cardgame.game.Interactions;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.List;

public class Bang extends Card {

	public Bang(int id, String naz, int num, String col) {
		ID = id;
		nazwa = naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer = num;
		kolor = col;
	}

	public Bang(int id, String obraz, String opek, String naz, int num, String col) {
		ID = id;
		nazwa = naz;
		obrazek = obraz;
		opis = opek;
		numer = num;
		kolor = col;
	}

	@Override
	public boolean zagraj(Deck deck, List<Player> players,
		Player currentPlayer, Interactions interactions) {
		Player cel = interactions.selectTargetPlayer(currentPlayer, players);
		if (cel == null) {
			return false;
		}

		Player strzelec = currentPlayer;
		Hero p = strzelec.getHero();
		String name = p.dajNazwe();
		if (strzelec.hasShot() == true) {
			if (strzelec.isMultipleShooter() == false && !"Willy the Kid".equals(name)) {
				System.out.print("Nie mo�esz strzela� ponownie");
				return false;
			}
		}

		int dystans = policzDystans(strzelec, cel, players);
		int zasieg = strzelec.zasieg() - cel.modZasiegu();
		if (dystans > zasieg) {
			System.out.print("Nie dostrzelisz");
			return false;
		} else {
			if (name.equals("Slab Zabojca")) {
				cel.postrzelBardziej(deck, interactions);
			} else {
				cel.postrzel(deck, interactions);
			}
			strzelec.setShotStatus(true);
			return true;
		}
	}
}