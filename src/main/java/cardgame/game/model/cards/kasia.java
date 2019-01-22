package cardgame.game.model.cards;

import cardgame.game.gra;
import cardgame.game.kontakt;
import cardgame.game.model.gracz;
import java.util.List;

public class kasia extends karta{
	
	public kasia(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public kasia(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz cel = kontakt.wybiezCel();
		String co = kontakt.coChceszZniszczyc();
		if(co=="bron") { 
			eq bron = cel.dajBron();
			cel.ustawBron(null);
			gra.odzuc(bron);
		}
		if(co=="dodatek") { 
			eq doda = cel.dajDodatek();
			cel.ustawDodatek(null);
			gra.odzuc(doda);
		}
		if(co=="karta") {
			List<karta> reka = cel.dajReke();
			karta odrzucona = kontakt.wybiezKarte(reka);
			reka.remove(odrzucona);
			gra.odzuc(odrzucona);		
		}
		return true;
	}

}