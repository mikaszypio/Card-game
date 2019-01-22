package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.gracz;
import java.util.ArrayList;
import java.util.List;

public class sklep extends karta{
	
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
	
	public boolean zagraj() {
		int ile = Gra.dajIleGraczy();
		List<karta> karty = new ArrayList<karta>();
		List<gracz> gracze = Gra.dajGraczy();
		int aktualny = Gra.dajNumerAktualnegoGracza();
		
		for(int x = 0; x<ile; x++) {
			karta k = Gra.dobiez();
			karty.add(k);
		}
		
		wypisz(karty);
		
		for(int x = 0; x<ile; x++) {
			//karta wybrana = kontakt.wybiezKarte(karty);
			//w tym momencie automatycznie wybiera si�, kto jak� kart� dostanie. Po ogarni�ciu clasy kontakt b�dzie mo�na korzysta� z jej funkcji by gracze wybierali
			karta wybrana = karty.get(0);
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
	
	public void wypisz(List<karta> karty) {
		for(karta k : karty) {
			System.out.print(k.dajNazwe()+ "\n");
		}
	}
}