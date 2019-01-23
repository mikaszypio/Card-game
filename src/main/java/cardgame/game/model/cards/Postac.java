package cardgame.game.model.cards;

import cardgame.game.Gra;

public class Postac {

	private Gra gra;
	private int maxHp;
	private String nazwa;
	private int ID;
	private String obrazek;
	private String opis;
	
	public Postac(int id, int hp, String name, Gra g) {
		ID=id;
		maxHp=hp;
		nazwa=name;
		gra=g;
	}
	
	public Postac(int id, int hp, String name, String pic, String opek, Gra g) {
		ID=id;
		maxHp=hp;
		nazwa=name;
		obrazek=pic;
		opis=opek;
		gra=g;
	}
	
	public int dajID() {
		return ID;
	}
	
	public int dajMaxHp() {
		return maxHp;
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
}
