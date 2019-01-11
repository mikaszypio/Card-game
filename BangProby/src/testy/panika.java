package testy;

import java.util.List;

public class panika extends karta{
	
	public panika(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public panika(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz cel = kontakt.wybiezCel();
		gracz strzelec = gra.dajAktualnegoGracza();
		int dystans = gra.policzDystans(strzelec, cel);
		int zasieg = strzelec.zasiegCzysty() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie siêgniesz");
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
				karta odrzucona = kontakt.wybiezKarte(reka);
				reka.remove(odrzucona);
				strzelec.doReki(odrzucona);		
			}
			return true;
		}
	}
}