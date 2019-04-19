package cardgame.game.model.cards;

import cardgame.game.model.Deck;
import cardgame.game.model.Gracz;
import java.util.List;

public class Card {

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
	
	public boolean zagraj(Deck deck, List<Gracz> players, Gracz currentPlayer) {
		return false;
	}
	
	//liczy odleg�o�� mi�dzy graczami
	public int policzDystans(Gracz jeden, Gracz dwa, List<Gracz> gracze) {
		int pierwszy = gracze.indexOf(jeden);
		int drugi = gracze.indexOf(dwa);
		if(pierwszy ==-1 || drugi==-1) {
			System.out.print("liczenie dystansu-przysz�y z�e dane");
			return 0;
		} else {
			if(pierwszy>drugi) {
				int tmp = pierwszy;
				pierwszy=drugi;
				drugi=tmp;
			}
			int prosty = drugi-pierwszy;
			int skomplikowany = pierwszy + (gracze.size()-drugi);
			if(prosty>skomplikowany) {
				return skomplikowany; 
			} else {
				return prosty;
			}
		}		
	}
	
}
