package testy;

import java.util.List;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		
		List<gracz> gracze = new ArrayList<gracz>();
		gracz tmp;
		tmp=new gracz("wardasz");
		gracze.add(tmp);
		tmp=new gracz("arame");
		gracze.add(tmp);
		tmp=new gracz("asirra");
		gracze.add(tmp);
		tmp=new gracz("lowca");
		gracze.add(tmp);
		tmp=new gracz("rin");
		gracze.add(tmp);
		
		gra Gra = new gra(gracze);
		//Gra.graj();
		Gra.zassaj();
		Gra.napiszGraczy();
		karta k = new sklep(53, "Indianie", 13, "karo");
		System.out.print("\nZagrywam sklep\n");
		k.zagraj();
		Gra.napiszGraczy();
		//Gra.napiszCmentaz();

	}
	
	
	
	
}
