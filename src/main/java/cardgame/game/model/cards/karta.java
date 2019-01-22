package cardgame.game.model.cards;

public class karta {

	protected int ID;
	protected String nazwa;
	protected String obrazek;
	protected String opis;
	protected int numer;
	protected String kolor;
	
	public karta() {
		ID=-1;
		nazwa = "Brak nazwy";
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer = 1;
		kolor = "pik";
	}
	
	public karta(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public int dajID() {
		return ID;
	}
	
	public String dajNazwe() {
		return nazwa;
	}
	
	public String dajObrazek() {
		return obrazek;
	}
	
	public String dajOpis() {
		return opis;
	}
	
	public int dajNumer() {
		return numer;
	}
	
	public String dajKolor() {
		return kolor;
	}
	
	public boolean zagraj() {
		return false;
	}
	
	//testowe
	public void opisz() {	
		System.out.print(ID + "-" + nazwa + "\n");
	}
	
}
