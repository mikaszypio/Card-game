package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.kontakt;
import cardgame.game.model.gracz;

public class bang extends karta{
	
	public bang(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public bang(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {		
		gracz cel = kontakt.wybiezCel(gra);
		gracz strzelec = gra.dajAktualnegoGracza();
		postac p = strzelec.dajPostac();
		String name = p.dajNazwe();
		if(strzelec.czyStrzelal()==true)
			if(strzelec.wielostrzal()==false && name!="Willy the Kid") {
			System.out.print("Nie mo�esz strzela� ponownie");
			return false;				
		}
		int dystans = gra.policzDystans(strzelec, cel);
		int zasieg = strzelec.zasieg() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie dostrzelisz");
			return false;
		}else {
			if(name=="Slab Zabojca") {
				cel.postrzelBardziej();
			}else {
				cel.postrzel();
			}
			return true;
		}
	}
}
