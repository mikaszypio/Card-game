package testy;

public class welsfargo extends karta{
	
	public welsfargo(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public welsfargo(int id, String obraz, String opek, String naz, int num, String col) {
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
		g.dobiezKarte();
		return true;
	}
}
