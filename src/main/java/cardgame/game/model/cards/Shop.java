package cardgame.game.model.cards;

import cardgame.game.Interactions;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.ArrayList;
import java.util.List;

public class Shop extends Card {

	public Shop(int id, String naz, int num, String col) {
		ID = id;
		nazwa = naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer = num;
		kolor = col;
	}

	public Shop(int id, String obraz, String opek, String naz, int num, String col) {
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
		int ile = players.size();
		List<Card> karty = new ArrayList<>();
		int aktualny = players.indexOf(currentPlayer);

		for (int x = 0; x < ile; x++) {
			Card k = deck.getCard();
			karty.add(k);
		}

		for (int x = 0; x < ile; x++) {
			interactions.sendNewCardsNotification(karty, players.get(aktualny).getId());
			Card wybrana = interactions.selectCard(karty, players.get(aktualny).getId());
			karty.remove(wybrana);
			players.get(aktualny).addToHand(wybrana);
			if (aktualny < (ile - 1)) {
				aktualny++;
			} else {
				aktualny = 0;
			}
		}

		return true;
	}
}
