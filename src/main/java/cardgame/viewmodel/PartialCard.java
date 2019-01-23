package cardgame.viewmodel;

import cardgame.game.model.cards.Card;

public class PartialCard {
	
	private int id;
	private String name;
	private int symbol;
	private String suit;
	
	public PartialCard(Card card) {
		
		id = card.dajID();
		name = card.dajNazwe();
		symbol = card.dajNumer();
		suit = card.dajKolor();
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public int getSymbol() { return symbol; }
	public String getSuit() { return suit; }
}
