package cardgame.game.model;

import cardgame.game.model.cards.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
	private List<Card> deck;
	private Card topOfDeck;
	private List<Card> rejectedCards;
	
	public Deck() {
		deck = new ArrayList<>();
		topOfDeck = null;
		rejectedCards = new ArrayList<>();
		
		deck.addAll(addEquipment());
		deck.addAll(addBeer());
		deck.add(addDynamite());
		deck.addAll(addBangs());
		deck.add(addWellsfargo());
		deck.addAll(addStagecoaches());
		deck.addAll(addPrisons());
		deck.add(addSalon());
		deck.add(addGatling());
		deck.addAll(addMisses());
		deck.addAll(addCats());
		deck.addAll(addPanic());
		deck.addAll(addShops());
		deck.addAll(addDuels());
		deck.addAll(addIndians());
	}
	
	private List<Card> addEquipment() {
		List<Card> equipment = new ArrayList<>();
		equipment.add(new Equipment(1,  "volcanic",    10, "pik",   true, 1, 0, false, true));
		equipment.add(new Equipment(2,  "volcanic",    10, "trefl", true, 1, 0, false, true));
		equipment.add(new Equipment(3,  "schofield",   13, "pik",   true, 2, 0, false, false));
		equipment.add(new Equipment(4,  "schofield",   11, "trefl", true, 2, 0, false, false));
		equipment.add(new Equipment(5,  "schofield",   12, "trefl", true, 2, 0, false, false));
		equipment.add(new Equipment(6,  "remington",   13, "trefl", true, 3, 0, false, false));
		equipment.add(new Equipment(7,  "revcarabine", 14, "trefl", true, 4, 0, false, false));
		equipment.add(new Equipment(8,  "winchester",   8, "pik",   true, 5, 0, false, false));
		equipment.add(new Equipment(9,  "scope",       14, "pik",  false, 1, 0, false, false));
		equipment.add(new Equipment(10, "barrel",      14, "pik",  false, 0, 0, true,  false));
		equipment.add(new Equipment(11, "barrel",      12, "pik",  false, 0, 0, true,  false));
		equipment.add(new Equipment(12, "mustang",      8, "kier", false, 0, 1, false, false));
		equipment.add(new Equipment(13, "mustang",      9, "kier", false, 0, 1, false, false));
		return equipment;
	}
	
	private List<Card> addBeer() {
		List<Card> beer = new ArrayList<>();
		beer.add(new piwko(14, "beer", 6, "kier"));
		beer.add(new piwko(15, "beer", 7, "kier"));
		beer.add(new piwko(16, "beer", 8, "kier"));
		beer.add(new piwko(17, "beer", 9, "kier"));
		beer.add(new piwko(18, "beer", 10, "kier"));
		beer.add(new piwko(19, "beer", 11, "kier"));
		return beer;
	}
	
	private Card addDynamite() {
		return new dynamit(20, "dynamite", 2, "kier");
	}
	
	private List<Card> addBangs() {
		List<Card> bangs = new ArrayList<>();
		bangs.add(new Bang(21, "bang", 12, "kier"));
		bangs.add(new Bang(22, "bang", 13, "kier"));
		bangs.add(new Bang(23, "bang", 14, "kier"));
		bangs.add(new Bang(24, "bang", 2, "karo"));
		bangs.add(new Bang(25, "bang", 3, "karo"));
		bangs.add(new Bang(26, "bang", 4, "karo"));
		bangs.add(new Bang(27, "bang", 5, "karo"));
		bangs.add(new Bang(28, "bang", 6, "karo"));
		bangs.add(new Bang(29, "bang", 7, "karo"));
		bangs.add(new Bang(30, "bang", 8, "karo"));
		bangs.add(new Bang(31, "bang", 9, "karo"));
		bangs.add(new Bang(32, "bang", 10, "karo"));
		bangs.add(new Bang(33, "bang", 11, "karo"));
		bangs.add(new Bang(34, "bang", 12, "karo"));		
		bangs.add(new Bang(35, "bang", 13, "karo"));		
		bangs.add(new Bang(36, "bang", 14, "karo"));		
		bangs.add(new Bang(37, "bang", 14, "pik"));		
		bangs.add(new Bang(38, "bang", 2, "trefl"));		
		bangs.add(new Bang(39, "bang", 3, "trefl"));		
		bangs.add(new Bang(40, "bang", 4, "trefl"));		
		bangs.add(new Bang(41, "bang", 5, "trefl"));		
		bangs.add(new Bang(42, "bang", 6, "trefl"));		
		bangs.add(new Bang(43, "bang", 7, "trefl"));		
		bangs.add(new Bang(44, "bang", 8, "trefl"));		
		bangs.add(new Bang(45, "bang", 9, "trefl"));
		return bangs;
	}
	
	private Card addWellsfargo() {
		return new welsfargo(46, "wellsfargo", 3, "kier");
	}
	
	private List<Card> addStagecoaches() {
		List<Card> stagecoaches = new ArrayList<>();
		stagecoaches.add(new dylizans(47, "stagecoach", 9, "pik"));		
		stagecoaches.add(new dylizans(48, "stagecoach", 9, "pik"));	
		return stagecoaches;
	}
	
	private List<Card> addPrisons() {
		List<Card> prisons = new ArrayList<>();
		prisons.add(new wiezienie(49, "jail", 4, "kier"));		
		prisons.add(new wiezienie(50, "jail", 11, "pik"));		
		prisons.add(new wiezienie(51, "jail", 10, "pik"));	
		return prisons;
	}
	
	private Card addSalon() {
		return new salon(52, "saloon", 5, "kier");	
	}
	
	private Card addGatling() {
		return new gatling(53, "gatling", 10, "kier");
	}
	
	private List<Card> addMisses() {
		List<Card> misses = new ArrayList<>();
		misses.add(new pudlo(54, "missed", 2, "pik"));		
		misses.add(new pudlo(55, "missed", 3, "pik"));		
		misses.add(new pudlo(56, "missed", 4, "pik"));		
		misses.add(new pudlo(57, "missed", 5, "pik"));
		misses.add(new pudlo(58, "missed", 6, "pik"));
		misses.add(new pudlo(59, "missed", 7, "pik"));
		misses.add(new pudlo(60, "missed", 8, "pik"));
		misses.add(new pudlo(61, "missed", 10, "trefl"));
		misses.add(new pudlo(62, "missed", 11, "trefl"));
		misses.add(new pudlo(63, "missed", 12, "trefl"));
		misses.add(new pudlo(64, "missed", 13, "trefl"));
		misses.add(new pudlo(65, "missed", 14, "trefl"));
		return misses;
	}
	
	private List<Card> addCats() {
		List<Card> cats = new ArrayList<>();
		cats.add(new kasia(66, "catbalou", 13, "kier"));
		cats.add(new kasia(67, "catbalou", 9, "karo"));
		cats.add(new kasia(68, "catbalou", 10, "karo"));
		cats.add(new kasia(69, "catbalou", 11, "karo"));
		return cats;
	}
	
	private List<Card> addPanic() {
		List<Card> panic = new ArrayList<>();
		panic.add(new panika(70, "panic", 8, "karo"));
		panic.add(new panika(71, "panic", 11, "kier"));
		panic.add(new panika(72, "panic", 12, "kier"));
		panic.add(new panika(73, "panic", 14, "kier"));
		return panic;
	}
	
	private List<Card> addShops() {
		List<Card> shops = new ArrayList<>();
		shops.add(new Shop(74, "generalstore", 12, "pik"));			
		shops.add(new Shop(75, "generalstore", 9, "trefl"));
		return shops;
	}
	
	private List<Card> addDuels() {
		List<Card> duels = new ArrayList<>();
		duels.add(new pojedynek(76, "duel", 12, "karo"));	
		duels.add(new pojedynek(77, "duel", 11, "pik"));
		duels.add(new pojedynek(78, "duel", 8, "trefl"));
		return duels;
	}
	
	private List<Card> addIndians() {
		List<Card> indians = new ArrayList<>();
		indians.add(new indianie(79, "indians", 13, "karo"));
		indians.add(new indianie(80, "indians", 14, "karo"));
		return indians;
	}
	
	// Previously was named "poker"
	public boolean checkCard(String kolor) {
		boolean result = false;
		Card card = getCard();
		if(kolor.equals(card.dajKolor())) {
			result=true;
		}

		rejectCard(card);
		return result;
	}
	
	// Previously was named "poker"
	public boolean checkCard(String kolor, int min, int max) {
		boolean result = false;
		Card card = getCard();
		if(kolor.equals(card.dajKolor()) && min >= card.dajNumer()
			&& max <= card.dajNumer()) {
			result = true;
		}

		rejectCard(card);
		return result;
	}
	
	public Card getCard(){
		Card card;
		if(topOfDeck == null) {
			if(deck.isEmpty()) {
				deck = rejectedCards;
				rejectedCards = new ArrayList<>();
			}
			
			Random rand = new Random();
			card = deck.get(rand.nextInt(deck.size()));
			deck.remove(card);
		} else {
			card = topOfDeck;
			topOfDeck = null;
		}
		
		return card;
	}
	
	public Card getRejectedCard() {
		return getRejectedCard(true);
	}
	
	public Card getRejectedCard(boolean remove) {
		if(rejectedCards.isEmpty()) {
			return null;
		}
		
		int lastIndex = rejectedCards.size() - 1;
		Card card = rejectedCards.get(lastIndex);
		if(remove) {
			rejectedCards.remove(card);
		}

		return card;
	}
	
	public int getRejectedCardsSize() {
		return rejectedCards.size();
	}
	
	public void rejectCard(Card card) {
		rejectedCards.add(card);
	}
	
	public void toTop(Card card) {
		topOfDeck = card;
	}
	
	public boolean withoutRejectedCards() {
		return rejectedCards.isEmpty();
	}
}
