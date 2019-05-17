package cardgame.game.model.cards;

import cardgame.game.Interactions;
import cardgame.game.model.Deck;
import cardgame.game.model.Player;
import java.util.List;
import java.util.Random;

public class panika extends Card{
	
	public panika(int id, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = "Brak obrazu";
		opis = "Brak opisu";
		numer=num;
		kolor=col;
	}
	
	public panika(int id, String obraz, String opek, String naz, int num, String col) {
		ID=id;
		nazwa=naz;
		obrazek = obraz;
		opis = opek;
		numer=num;
		kolor=col;
	}
	
	@Override
	public boolean zagraj(Deck deck, List<Player> players,
		Player currentPlayer, Interactions interactions) {
		Player cel = interactions.selectTargetPlayer(currentPlayer, players);
		if(cel == null) {
			return false;
		}
		
		Player strzelec = currentPlayer;
		int dystans = policzDystans(strzelec, cel, players);
		int zasieg = strzelec.zasiegCzysty() - cel.modZasiegu();
		if(dystans>zasieg) {
			System.out.print("Nie si�gniesz");
			return false;
		} else {
			String co = interactions.selectTargetCard(currentPlayer.getId());
			if("bron".equals(co)) { 
				Equipment bron = cel.getWeapon();
				cel.setWeapon(null);
				strzelec.addToHand(bron);
			} else if("dodatek".equals(co)) { 
				Equipment doda = cel.getSupportItem();
				cel.setSupportItem(null);
				strzelec.addToHand(doda);
			} else if("karta".equals(co)) {
				List<Card> reka = cel.getHand();
				Random rand = new Random();
				Card wynik = reka.get(rand.nextInt(reka.size()));
				reka.remove(wynik);
				strzelec.addToHand(wynik);		
			} else {
				return false;
			}
			
			
			return true;
		}
	}
}