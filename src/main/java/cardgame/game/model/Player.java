package cardgame.game.model;

import cardgame.game.Interactions;
import cardgame.game.model.cards.Equipment;
import cardgame.game.model.cards.Card;
import cardgame.game.model.cards.Hero;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

	private Long id;
	private Hero hero;
	private final String nickname;
	private int role;		//1=szeryf, 2=pomocnik, 3=renegat, 4=bandyta
	private List<Card> reka;
	private int hitPoints;
	private Equipment weapon;
	private Equipment supportItem;
	private boolean shotStatus;
	private boolean gotDynamite;
	private boolean inJail;
	
	public Player(String a) {
		nickname = a;
		reka = new ArrayList<>(); 
		weapon = null;
		supportItem = null;
		gotDynamite = false;
		inJail = false;
		shotStatus = false;
		hitPoints = 5;
	}
	
	public Player(String a, Long id) {
		this.id = id;
		nickname = a;
		reka = new ArrayList<>(); 
		weapon = null;
		supportItem = null;
		gotDynamite = false;
		inJail = false;
		shotStatus = false;
		hitPoints = 5;
	}
	
	// get and set methods
	public Long getId() {
		return id;
	}
	
	public Hero getHero() {
		return hero;
	}
	
	public void setHero(Hero hero) {
		this.hero = hero;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public int getRole() {
		return role;
	}
	
	public void setRole(int roleIndex) {
		role = roleIndex;
	}
	
	public List<Card> getHand(){
		return reka;
	}
	
	public int getHitPoints() {
		return hitPoints;
	}
	
	public Equipment getWeapon() {
		return weapon;
	}
	
	// Methods for setting weapon, should be used only with null while stealing according to Wardasz
	public void setWeapon(Equipment equipment) {
		weapon = equipment;
	}
	
	public Equipment getSupportItem() {
		return supportItem;
	}
	
	// Methods for setting supportItem, should be used only with null while stealing according to Wardasz
	public void setSupportItem(Equipment equipment) {
		supportItem = equipment;
	}
	
	public boolean hasShot() {
		return shotStatus;
	}
	
	public void setShotStatus(boolean shotStatus) {
		this.shotStatus = shotStatus;
	}
	
	public boolean gotDynamite() {
		return gotDynamite;
	}
	
	public void setDynamite() {
		gotDynamite = true;
	}
	
	public boolean inPrison() {
		return inJail;
	}
	
	public void setInJail() {
		inJail = true;
	}
	
	// "Cards" methods
	public boolean checkDynamite(Deck deck, Interactions interactions) {
		gotDynamite=false;
		if(deck.checkCard("pik", 2, 9)) {
			hurt(3, deck, interactions);
			return true;
		}
		
		return false;
	}
	
	public boolean checkPrison(Deck deck) {
		if(deck.checkCard("kier")) {
			inJail = false;
		}
		
		return !inJail;
	}
	
	public void getExtraCard(Deck deck) {
		reka.add(deck.getCard());
	}
	
	public void addToHand(Card k) {
		reka.add(k);
	}
	
	public void removeFromHand(Card k) {
		reka.remove(k);
	}
	
	//bystra funkcja wywoywana gdy zagrywam bro/dodatek 
	public void equip(Equipment equipment, Deck deck){
		if(equipment.czyBron()){
			if(weapon != null) {
				//gra.odzuc(bron);
				deck.rejectCard(weapon);
			}
			
			weapon = equipment;
		} else {
			if(supportItem != null) {
				//gra.odzuc(dodatek);
				deck.rejectCard(supportItem);
			}
			
			supportItem = equipment;
		}
	}
	
	//wywoywane na pocztku tury
	public void getCards(Deck deck, List<Player> players, Interactions interactions) {	
		boolean czy;
		switch(hero.dajNazwe()) {
			case "Pedro Ramirez":
				czy = interactions.getCardsAlternativeWay(id, players);
				if(czy == true) {
					Card k = deck.getRejectedCard();
					if(k == null) {
						reka.add(deck.getCard());
						reka.add(deck.getCard());
					} else {
						addToHand(k);
						reka.add(deck.getCard());
					}
				} else {
					reka.add(deck.getCard());
					reka.add(deck.getCard());
				}
				
				break;
			case "Black Jack":
				reka.add(deck.getCard());
				Card k = deck.getCard();
				if(k.dajKolor().equals("kier") || k.dajKolor().equals("karo")) {
					reka.add(deck.getCard());
				}
				
				addToHand(k);
				break;
			case "Jesse Jones":
				czy = interactions.getCardsAlternativeWay(id, players);
				if(czy==true) {
					Player cel = interactions.selectTargetPlayer(this, players);
					List<Card> reka = cel.getHand();
					Random rand = new Random();
					Card wynik = reka.get(rand.nextInt(reka.size()));
					reka.remove(wynik);
					addToHand(wynik);
					reka.add(deck.getCard());
				}else {
					reka.add(deck.getCard());
					reka.add(deck.getCard());
				}				
				break;
			case "Kit Carlson":
				List<Card> wyciogniete = new ArrayList<>();
				wyciogniete.add(deck.getCard());
				wyciogniete.add(deck.getCard());
				wyciogniete.add(deck.getCard());
				System.out.println("Wycigne te trzy karty-wybierz ktrej z nich nie chcesz");
				Card smiec = interactions.selectCard(wyciogniete, id);
				wyciogniete.remove(smiec);
				//gra.naSzczyt(smiec);
				deck.toTop(smiec);
				for(Card ka : wyciogniete) {
					addToHand(ka);
				}
			default:
				reka.add(deck.getCard());
				reka.add(deck.getCard());
				break;
		}			
	}

	//wywoywane na koniec tury
	public void rejectCards(Deck deck, Interactions interactions) {
		int ile = reka.size();
		while(ile > hitPoints) {
			Card k = interactions.selectCard(reka, id);
			deck.rejectCard(k);
			ile--;
		}
	}
	
	//metoda sprawdza, czy masz dan kart na rce, jeli masz to odrzuca. W finalnej wersji powinna jeszcze pyta, czy chcesz odrzuci, ale... co jest nie tak w kontakcie
	public boolean testCard(String nazwa, String zrodlo, Deck deck, Interactions interactions) {
		System.out.print("Testuje gracz " + nickname + "\n");
		if(!reka.isEmpty()) {
			for(Card k : reka) {
				String cardName = k.dajNazwe();
				if(cardName.equals(nazwa)) {
					boolean useCard = interactions.useCounterCard(this, cardName, zrodlo);
					if(useCard) {
						reka.remove(k);
						deck.rejectCard(k);
						return true;
					} else {
						return false;
					}
				}		
			}
		}
		
		return false;
	}
	
	// "Character" methods
	
	//zwraca zasig gracza
	public int zasieg() {
		int wynik = 1;
		if(weapon != null) { wynik += weapon.dajZasiegMod(); }
		if(supportItem != null) { wynik += supportItem.dajZasiegMod(); }
		if(hero.dajNazwe().equals("Rose Doolan")) { wynik++; }
		return wynik;
	}
	
	//zwraca zasig bez uwzgldnienia broni (jakas karta potrzebuje takiego)
	public int zasiegCzysty() {
		int wynik = 1;
		if(supportItem != null) { wynik += supportItem.dajZasiegMod(); }
		if(hero.dajNazwe().equals("Rose Doolan")) { wynik++; }
		return wynik;
	}
	
	//zwraca negatywny modyfikator zasigu DO tego gracza (efekt mustanga)
	public int modZasiegu() {
		int wynik = 0;
		if(supportItem != null) { wynik += supportItem.dajObronaMod(); }
		if(hero.dajNazwe().equals("Paul Regret")) { wynik++; }
		return wynik;
	}
	
	public int getMaxHitPoints() {
		int max = hero.dajMaxHp();
		if(role == 1) { max++; }
		return max;
	}
	
	public void heal(int points) {
		hitPoints += points;
		int max = getMaxHitPoints();
		if(hitPoints > max) {
			hitPoints = max;
		}
	}
	
	public void hurt(int damage, Deck deck, Interactions interactions) {
		hitPoints -= damage;
		if(hero.dajNazwe().equals("Bart Cassady")) {
			while(damage > 0) {
				//dobiezKarte();
				reka.add(deck.getCard());
				damage--;
			}
		}
		
		while(hitPoints < 1) {
			if(testCard("beer", "Zgon", deck, interactions)) {
				hitPoints++;
			} else {
				return;
			}
		}
	}
	
	//mwi, czy bro gracza moe strzela wiele razy
	public boolean isMultipleShooter() {
		if(weapon != null) {
			return weapon.czyMultistrzal();
		} else {
			return false;
		}
	}
	
	//mwi, czy gracz ma baryk
	public boolean tryAvoid() {
		if(supportItem != null) {
			return supportItem.czyObrona();
		} else {
			return false;
		}
	}
	
	//funcja wywoywana dostaniem banga
	public void postrzel(Deck deck, Interactions interactions) {
		if(hero.dajNazwe().equals("Jourdonnais")) {
			//if(gra.poker("kier")==true) {
			if(deck.checkCard("kier")) {
				System.out.print("Epicko Jourdonnaisa ochronia\n");
				return;
			}
		}
		if(tryAvoid()==true) {
			//if(gra.poker("kier")==true) {
			if(deck.checkCard("kier")) {
				System.out.print("Baryka ochronia\n");
				return;
			}
		}		
		boolean czy = testCard("missed", "Bang", deck, interactions);
		if(czy==true){
			System.out.print("Spudowa\n");
		}else {
			hurt(1, deck, interactions);
		}
	}
	
	//funcja wywoywana dostaniem banga od postaci ktr trzeba podwjnie pudowa
	public void postrzelBardziej(Deck deck, Interactions interactions) {
		int barylki = 0;
		int pudla = 0;
		
		if(hero.dajNazwe().equals("Jourdonnais")) {
			//if(gra.poker("kier")==true) {
			if(deck.checkCard("kier")) {
				System.out.print("Epicko Jourdonnaisa pomaga uskoczy\n");
				barylki++;
			}
		}
		if(tryAvoid()==true) {
			//if(gra.poker("kier")==true) {
			if(deck.checkCard("kier")) {
				System.out.print("Baryka pomaga uskoczy\n");
				barylki++;
			}
		}	
		for(Card k : reka) {
			if(k.dajNazwe().equals("missed")) {
				pudla++;
			}
		}
		
		if(barylki==2) {
			System.out.print("Unikne precyzyjnego trafienia\n");
			return;
		}
		if(barylki==1) {
			boolean czy = testCard("missed", "Bang", deck, interactions);
			if(czy==true){
				System.out.print("Spudowa dziki pomocy baryki/wietnoci Jourdonnaisa\n");
			}
		}
		if(barylki == 0) {
			if(pudla > 1) {
				//pytanie czy chcesz pudowa
				int ile = 2;
				for(Card k : reka) {
					if(k.dajNazwe().equals("missed") && ile>0) {
						ile--;
						reka.remove(k);
						//gra.odzuc(k);
						deck.rejectCard(k);
					}
				}
				System.out.print("Jakim cudem spudowa \n");
				return;
			}
		}
		
		hurt(1, deck, interactions);
	}
}
