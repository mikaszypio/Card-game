package cardgame.game.model.cards;

import cardgame.game.Gra;
import cardgame.game.model.gracz;

public class eq extends karta {

	private boolean bron;
	
	private int zasiegMod;
	private int obronaMod;
	private boolean obrona;
	private boolean miltistrzal;
	
	public eq(int id, String naz, int num, String col, boolean czyBron, int zasM, int obrM, boolean czyBroni, boolean czyWielo, Gra g){
		ID=id;
		nazwa=naz;
		numer=num;
		kolor=col;
		bron=czyBron;
		zasiegMod=zasM;
		obronaMod=obrM;
		obrona=czyBroni;
		miltistrzal=czyWielo;
		gra=g;
	}
	
	public boolean czyBron() {
		return bron;
	}
	
	public int dajZasiegMod() {
		return zasiegMod;
	}
	
	public int dajObronaMod() {
		return obronaMod;
	}
	
	public boolean czyObrona() {
		return obrona;
	}
	
	public boolean czyMultistrzal() {
		return miltistrzal;
	}
	
	public boolean zagraj() {
		gracz g = gra.dajAktualnegoGracza();
		g.wyposaz(this);
		return true;
	}
}
