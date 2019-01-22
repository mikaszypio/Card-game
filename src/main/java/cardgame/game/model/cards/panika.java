package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.kontakt;
import cardgame.game.model.gracz;
import java.util.List;
import java.util.Random;

public class panika extends karta{
	
	public panika(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public panika(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		gracz cel = kontakt.wybiezCel(gra);
		gracz strzelec = gra.dajAktualnegoGracza();
		int dystans = gra.policzDystans(strzelec, cel);
		int zasieg = strzelec.zasiegCzysty() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie si�gniesz");
			return false;
		}else {
			String co = kontakt.coChceszZniszczyc();
			if(co=="bron") { 
				eq bron = cel.dajBron();
				cel.ustawBron(null);
				strzelec.doReki(bron);
			}
			if(co=="dodatek") { 
				eq doda = cel.dajDodatek();
				cel.ustawDodatek(null);
				strzelec.doReki(doda);
			}
			if(co=="karta") {
				List<karta> reka = cel.dajReke();
				Random rand = new Random();
				karta wynik = reka.get(rand.nextInt(reka.size()));
				reka.remove(wynik);
				strzelec.doReki(wynik);		
			}
			return true;
		}
	}
}