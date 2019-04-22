package cardgame.viewmodel;

import cardgame.game.model.cards.Card;

public class PartialCard {
	
	private final int id;
	private final String name;
	private final int symbol;
	private final String suit;
	
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
