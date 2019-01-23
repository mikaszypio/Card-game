package cardgame.game.model.cards;

import cardgame.game.Gra;

public class Card {

	protected Gra gra;
	protected int ID;
	protected String nazwa;
	protected String obrazek;
	protected String opis;
	protected int numer;
	protected String kolor;
	
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
	
}
