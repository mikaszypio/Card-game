package cardgame.game.model;

import cardgame.game.model.cards.*;
import java.util.ArrayList;
import java.util.List;

public class Deck {
	private List<Card> deck;
	private Card szczyt;
	private List<Card> cmentaz;
	
	public Deck() {
		deck = new ArrayList<>();
		szczyt = null;
		cmentaz = new ArrayList<>();
		addEquipment();
		addBeer();
		addDynamite();
		addBangs();
		addWellsfargo();
		addStagecoaches();
		addPrisons();
		addSalon();
		addGatling();
		addMisses();
		addCat();
		addPanic();
		addShops();
		addDuels();
		addIndians();
	}
	
	private void addEquipment() {
		deck.add(new Equipment(1,  "volcanic",    10, "pik",   true, 1, 0, false, true));
		deck.add(new Equipment(2,  "volcanic",    10, "trefl", true, 1, 0, false, true));
		deck.add(new Equipment(3,  "schofield",   13, "pik",   true, 2, 0, false, false));
		deck.add(new Equipment(4,  "schofield",   11, "trefl", true, 2, 0, false, false));
		deck.add(new Equipment(5,  "schofield",   12, "trefl", true, 2, 0, false, false));
		deck.add(new Equipment(6,  "remington",   13, "trefl", true, 3, 0, false, false));
		deck.add(new Equipment(7,  "revcarabine", 14, "trefl", true, 4, 0, false, false));
		deck.add(new Equipment(8,  "winchester",   8, "pik",   true, 5, 0, false, false));
		deck.add(new Equipment(9,  "scope",       14, "pik",  false, 1, 0, false, false));
		deck.add(new Equipment(10, "barrel",      14, "pik",  false, 0, 0, true,  false));
		deck.add(new Equipment(11, "barrel",      12, "pik",  false, 0, 0, true,  false));
		deck.add(new Equipment(12, "mustang",      8, "kier", false, 0, 1, false, false));
		deck.add(new Equipment(13, "mustang",      9, "kier", false, 0, 1, false, false));
	}
	
	private void addBeer() {
		deck.add(new piwko(14, "beer", 6, "kier"));
		deck.add(new piwko(15, "beer", 7, "kier"));
		deck.add(new piwko(16, "beer", 8, "kier"));
		deck.add(new piwko(17, "beer", 9, "kier"));
		deck.add(new piwko(18, "beer", 10, "kier"));
		deck.add(new piwko(19, "beer", 11, "kier"));
	}
	
	private void addDynamite() {
		deck.add(new dynamit(20, "dynamite", 2, "kier"));
	}
	
	private void addBangs() {
		deck.add(new bang(21, "bang", 12, "kier"));
		deck.add(new bang(22, "bang", 13, "kier"));
		deck.add(new bang(23, "bang", 14, "kier"));
		deck.add(new bang(24, "bang", 2, "karo"));
		deck.add(new bang(25, "bang", 3, "karo"));
		deck.add(new bang(26, "bang", 4, "karo"));
		deck.add(new bang(27, "bang", 5, "karo"));
		deck.add(new bang(28, "bang", 6, "karo"));
		deck.add(new bang(29, "bang", 7, "karo"));
		deck.add(new bang(30, "bang", 8, "karo"));
		deck.add(new bang(31, "bang", 9, "karo"));
		deck.add(new bang(32, "bang", 10, "karo"));
		deck.add(new bang(33, "bang", 11, "karo"));
		deck.add(new bang(34, "bang", 12, "karo"));		
		deck.add(new bang(35, "bang", 13, "karo"));		
		deck.add(new bang(36, "bang", 14, "karo"));		
		deck.add(new bang(37, "bang", 14, "pik"));		
		deck.add(new bang(38, "bang", 2, "trefl"));		
		deck.add(new bang(39, "bang", 3, "trefl"));		
		deck.add(new bang(40, "bang", 4, "trefl"));		
		deck.add(new bang(41, "bang", 5, "trefl"));		
		deck.add(new bang(42, "bang", 6, "trefl"));		
		deck.add(new bang(43, "bang", 7, "trefl"));		
		deck.add(new bang(44, "bang", 8, "trefl"));		
		deck.add(new bang(45, "bang", 9, "trefl"));
	}
	
	private void addWellsfargo() {
		deck.add(new welsfargo(46, "wellsfargo", 3, "kier"));
	}
	
	private void addStagecoaches() {
		deck.add(new dylizans( 47, "stagecoach", 9, "pik"));		
		deck.add(new dylizans( 48, "stagecoach", 9, "pik"));	
	}
	
	private void addPrisons() {
		deck.add(new wiezienie(49, "jail", 4, "kier"));		
		deck.add(new wiezienie(50, "jail", 11, "pik"));		
		deck.add(new wiezienie(51, "jail", 10, "pik"));	
	}
	
	private void addSalon() {
		deck.add(new salon(52, "saloon", 5, "kier"));	
	}
	
	private void addGatling() {
		deck.add(new gatling(53, "gatling", 10, "kier"));
	}
	
	private void addMisses() {
		deck.add(new pudlo(54, "missed", 2, "pik"));		
		deck.add(new pudlo(55, "missed", 3, "pik"));		
		deck.add(new pudlo(56, "missed", 4, "pik"));		
		deck.add(new pudlo(57, "missed", 5, "pik"));
		deck.add(new pudlo(58, "missed", 6, "pik"));
		deck.add(new pudlo(59, "missed", 7, "pik"));
		deck.add(new pudlo(60, "missed", 8, "pik"));
		deck.add(new pudlo(61, "missed", 10, "trefl"));
		deck.add(new pudlo(62, "missed", 11, "trefl"));
		deck.add(new pudlo(63, "missed", 12, "trefl"));
		deck.add(new pudlo(64, "missed", 13, "trefl"));
		deck.add(new pudlo(65, "missed", 14, "trefl"));
	}
	
	private void addCat() {
		deck.add(new kasia(66, "catbalou", 13, "kier"));
		deck.add(new kasia(67, "catbalou", 9, "karo"));
		deck.add(new kasia(68, "catbalou", 10, "karo"));
		deck.add(new kasia(69, "catbalou", 11, "karo"));
	}
	
	private void addPanic() {
		deck.add(new panika(70, "panic", 8, "karo"));
		deck.add(new panika(71, "panic", 11, "kier"));
		deck.add(new panika(72, "panic", 12, "kier"));
		deck.add(new panika(73, "panic", 14, "kier"));	
	}
	
	private void addShops() {
		deck.add(new sklep(74, "generalstore", 12, "pik"));			
		deck.add(new sklep(75, "generalstore", 9, "trefl"));
	}
	
	private void addDuels() {
		deck.add(new pojedynek(76, "duel", 12, "karo"));	
		deck.add(new pojedynek(77, "duel", 11, "pik"));
		deck.add(new pojedynek(78, "duel", 8, "trefl"));
	}
	
	private void addIndians() {
		deck.add(new indianie(79, "indians", 13, "karo"));
		deck.add(new indianie(80, "indians", 14, "karo"));
	}
}
