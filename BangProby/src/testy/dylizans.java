package testy;

public class dylizans extends karta{
	
	public dylizans(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public dylizans(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	public boolean zagraj() {
		gracz g =gra.dajAktualnegoGracza();
		g.dobiezKarte();
		g.dobiezKarte();
		return true;
	}
}
