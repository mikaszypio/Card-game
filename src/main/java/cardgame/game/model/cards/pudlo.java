package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.kontakt;
import cardgame.game.model.gracz;

public class pudlo extends karta{

	public pudlo(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public pudlo(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		gracz strzelec = gra.dajAktualnegoGracza();
		postac p = strzelec.dajPostac();
		String name = p.dajNazwe();
		if(name=="Calamity Janet") {
			if(strzelec.czyStrzelal()==true)
				if(strzelec.wielostrzal()==false) {
				System.out.print("Nie mo�esz strzela� ponownie");
				return false;				
			}
			gracz cel = kontakt.wybiezCel(gra);
			int dystans = gra.policzDystans(strzelec, cel);
			int zasieg = strzelec.zasieg() - cel.modZasiegu();
			if(dystans>zasieg) {
				System.out.print("Nie dostrzelisz");
				return false;
			}else {
				cel.postrzel();
				return true;
			}
		}else {
			return false;
		}
	}
}
