package testy;

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
		int ile = gra.dajIleGraczy();
		List<karta> karty = new ArrayList<karta>();
		List<gracz> gracze = gra.dajGraczy();
		int aktualny = gra.dajNumerAktualnegoGracza();
		
		for(int x = 0; x<ile; x++) {
			karta k = gra.dobiez();
			karty.add(k);
		}
		
		wypisz(karty);
		
		for(int x = 0; x<ile; x++) {
			//karta wybrana = kontakt.wybiezKarte(karty);
			//w tym momencie automatycznie wybiera siê, kto jak¹ kartê dostanie. Po ogarniêciu clasy kontakt bêdzie mo¿na korzystaæ z jej funkcji by gracze wybierali
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
