package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.kontakt;
import cardgame.game.model.Gracz;
import java.util.List;

public class kasia extends Card{
	
	public kasia(int id, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public kasia(int id, String obraz, String opek, String naz, int num, String col, Gra g) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
		gra=g;
	}
	
	public boolean zagraj() {
		Gracz cel = kontakt.wybiezCel(gra);
		String co = kontakt.coChceszZniszczyc();
		if(co=="bron") { 
			Equipment bron = cel.dajBron();
			cel.ustawBron(null);
			gra.odzuc(bron);
		}
		if(co=="dodatek") { 
			Equipment doda = cel.dajDodatek();
			cel.ustawDodatek(null);
			gra.odzuc(doda);
		}
		if(co=="karta") {
			List<Card> reka = cel.dajReke();
			Card odrzucona = kontakt.wybiezKarte(reka);
			reka.remove(odrzucona);
			gra.odzuc(odrzucona);		
		}
		return true;
	}

}