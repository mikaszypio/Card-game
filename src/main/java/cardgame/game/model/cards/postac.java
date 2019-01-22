package cardgame.game.model.cards;

import java.util.ArrayList;
import java.util.List;

public class postac {

	private int maxHp;
	private String nazwa;
	private int ID;
	private String obrazek;
	private String opis;
	
	public postac(int id, int hp, String name) {
		ID=id;
		maxHp=hp;
		nazwa=name;
	}
	
	public postac(int id, int hp, String name, String pic, String opek) {
		ID=id;
		maxHp=hp;
		nazwa=name;
		obrazek=pic;
		opis=opek;
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
