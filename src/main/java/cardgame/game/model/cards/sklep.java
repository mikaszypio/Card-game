package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.ArrayList;
import java.util.List;

public class sklep extends Card{
	
	public sklep(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public sklep(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		int ile = players.size();
		List<Card> karty = new ArrayList<Card>();
		List<Gracz> gracze = players;
		//int aktualny = gra.dajNumerAktualnegoGracza();
		int aktualny = players.indexOf(currentPlayer);
		
		for(int x = 0; x<ile; x++) {
			Card k = deck.getCard();
			karty.add(k);
		}
		
		wypisz(karty);
		
		for(int x = 0; x<ile; x++) {
			//karta wybrana = kontakt.wybiezKarte(karty);
			//w tym momencie automatycznie wybiera si�, kto jak� kart� dostanie. Po ogarni�ciu clasy kontakt b�dzie mo�na korzysta� z jej funkcji by gracze wybierali
			Card wybrana = karty.get(0);
			karty.remove(wybrana);
			gracze.get(aktualny).doReki(wybrana);
			if(aktualny<(ile-1)) {
				aktualny++;
			}else {
				aktualny=0;
			}
		}
		
		return true;
	}
	
	public void wypisz(List<Card> karty) {
		for(Card k : karty) {
			System.out.print(k.dajNazwe()+ "\n");
		}
	}
}
