package testy;

public class pojedynek extends karta{
	
	public pojedynek(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public pojedynek(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz cel = kontakt.wybiezCel();
		gracz wyzywajacy = gra.dajAktualnegoGracza();
		boolean odbito = false;
		boolean toczySie = true;
		while(toczySie==true) {
			if(odbito==true) {
				if(wyzywajacy.testKarty("Bang", "Pojedynek")==true) {
					odbito=false;
				}else {
					wyzywajacy.zran(1);
					toczySie=false;
				}
			}else {
				if(cel.testKarty("Bang", "Pojedynek")==true) {
					odbito=true;
				}else {
					cel.zran(1);
					toczySie=false;
				}
			}
		}	
		return true;
	}
}
