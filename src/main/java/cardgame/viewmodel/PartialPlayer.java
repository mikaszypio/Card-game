package cardgame.viewmodel;

import cardgame.game.model.Gracz;
import cardgame.game.model.cards.Equipment;
import java.util.ArrayList;
import java.util.List;

public class PartialPlayer {
	
	//id: 23, name: "Player2", role:1, ch: 1, hp: 5, cards: 5, items: [dummyItems[0]]
	private long id;
	private String name;
	private int role;
	private int ch;
	private int hp;
	private int cards;
	
	private List<Equipment> items;
	
	public PartialPlayer(Gracz gracz) {
		id = gracz.dajId();
		name = gracz.dajNick();
		role = gracz.dajRole();
		ch = gracz.dajPostac().dajID();
		hp = gracz.dajHp();
		cards = gracz.dajReke().size();
		items = new ArrayList<>();
		items.add(gracz.dajBron());
		items.add(gracz.dajDodatek());
		
	}
	
	public long getId() { return id; }
	public String getName() { return name; }
	public int getRole() { return role; }
	public int getCh() { return ch; }
	public int getHp() { return hp; }
	public int getCards() { return cards; }
}