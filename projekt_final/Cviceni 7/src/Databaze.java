import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Databaze {
	Databaze()
	{
		prvkyDatabaze=new HashMap<String,Kniha>();
	}
		
	public boolean setKniha(String nazev, String autor, int vydani, boolean dostupnost, boolean typ, String extra)
	{
		
		if (prvkyDatabaze.put(nazev,new Kniha(nazev, autor, vydani, dostupnost, typ, extra))==null)
			return true;
		else
			return false;
		
	} 
	
	public Kniha getKniha(String nazev) 
	{
		
		return prvkyDatabaze.get(nazev);
		
	}
	
	public boolean vymazKnihu(String nazev)
	{
		if (prvkyDatabaze.remove(nazev)!=null)
			return true;
		return false;
	}
	
	public void pujceniKnihy(String nazev, boolean pujceni) {
	    Kniha kniha = prvkyDatabaze.get(nazev);
	    if (kniha != null) {
	        if (pujceni) {
	            if (!kniha.isDostupnost()) {
	                System.out.println(kniha.getNazev() + " neni dostupna");
	            } else {
	                kniha.setDostupnost(false);
	                System.out.println(kniha.getNazev() + " je pujcena");
	            }
	        } else {
	            if (kniha.isDostupnost()) {
	                System.out.println(kniha.getNazev() + " je dostupna");
	            } else {
	                kniha.setDostupnost(true);
	                System.out.println(kniha.getNazev() + " je vracena");
	            }
	        }
	    } else {
	        System.out.println(nazev + " nebyla nalezena.");
	    }
	}
	
	public void vypisKnihyAutor(String autor) {
		List<Kniha> knihyAutora = new ArrayList<>();
		for (Kniha kniha : prvkyDatabaze.values()) {
			if (kniha.getAutor().equals(autor)) {
				knihyAutora.add(kniha);
			}
		}
		Collections.sort(knihyAutora, (k1, k2) -> Integer.compare(k1.getVydani(), k2.getVydani()));
		if (knihyAutora.isEmpty()) {
			System.out.println(autor + "nema knihy v databazi");
		} else {
			System.out.println("Knihy v poradi chronologicky od " + autor);
			for (Kniha kniha : knihyAutora) {
				System.out.println(kniha.getNazev() + ", " + kniha.getVydani());
			}
		}
	}
	
	public List<Kniha> vypisKnihyZanr(String zanr) {
		List<Kniha> knihy = new ArrayList<>();
		for (Kniha kniha : prvkyDatabaze.values()) {
			if (kniha.getExtra().equals(zanr)) {
				knihy.add(kniha);
			}
		}
		return knihy;
	}
	
	public List<Kniha> vypisKnihyPujcene() {
		List<Kniha> pujcene = new ArrayList<>();
		for (Kniha kniha : prvkyDatabaze.values()) {
			if (!kniha.isDostupnost()) {
				pujcene.add(kniha);
			}
		}
		return pujcene;
	}
	
	public boolean ulozKnihu(String jmenoSouboru)
	{
		try {
			FileWriter fw = new FileWriter(jmenoSouboru);
			BufferedWriter out = new BufferedWriter(fw);
			for (Map.Entry<String, Kniha> entry : prvkyDatabaze.entrySet()){
				Kniha kniha = entry.getValue();
				if (kniha != null) {
	                out.write(kniha.getNazev() + ", " + kniha.getAutor() + ", " + kniha.getVydani() + ", " + dostupnostPrepis(kniha.isDostupnost()) + ", " + typPrepis(kniha.isTyp()) + ", " + kniha.getExtra());
	                out.newLine();
	            } else {
	            	out.write("null");
					out.newLine();
	            }
	        }
			out.close();
			fw.close();
		}
		catch (IOException e) {
			System.out.println("Soubor nelze vytvorit");
			return false;
		}
		return true;
	}
	
	public boolean nactiKnihu(String jmenoSouboru) {
	    FileReader fr = null;
	    BufferedReader in = null;

	    try {
	        fr = new FileReader(jmenoSouboru);
	        in = new BufferedReader(fr);

	        String radek = in.readLine();
	        String[] castiTextu = radek.split(",");

	        for (int i = 0; i < castiTextu.length; i++) {
	            castiTextu[i] = castiTextu[i].trim();
	        }

	        if (castiTextu.length != 6) {
	            return false;
	        }

	        String nazev = castiTextu[0];
	        String autor = castiTextu[1];
	        int vydani = Integer.parseInt(castiTextu[2]);
	        boolean dostupnost = castiTextu[3].equals("Dostupne");
	        boolean typ = castiTextu[4].equals("Ucebnice");
	        String extra = castiTextu[5];

	        prvkyDatabaze.put(nazev, new Kniha(nazev, autor, vydani, dostupnost, typ, extra));
	    } catch (IOException e) {
	        System.out.println("Chyba pri cteni: " + e.getMessage());
	        return false;
	    } catch (NumberFormatException e) {
	        System.out.println("Chyba pri prevodu cisla: " + e.getMessage());
	        return false;
	    } finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	            if (fr != null) {
	                fr.close();
	            }
	        } catch (IOException e) {
	            System.out.println("Chyba pri zavirani: " + e.getMessage());
	            return false;
	        }
	    }

	    return true;
	}
	
	public static String typPrepis(boolean vstup) {
		if(vstup) {
			return "Ucebnice";
	}else {
		return "Roman";
	}
}
	public static String dostupnostPrepis(boolean vstup) {
		if(vstup) {
			return "Dostupne";
	}else {
		return "Nedostupne";
	}
}
	
	public void vypisDatabaze()
	{
		for(Kniha kniha : prvkyDatabaze.values())
			System.out.println(kniha.getNazev() + ", " + kniha.getAutor() + ", " + kniha.getVydani() + ", " + dostupnostPrepis(kniha.isDostupnost()) + ", " + typPrepis(kniha.isTyp()) + ", " + kniha.getExtra());
	}
	
	public void ulozeniDatabaze() {
		 SQLovani i = new SQLovani();
		for(Kniha kniha : prvkyDatabaze.values()) {
			//System.out.println(kniha.getNazev() + ", " + kniha.getAutor() + ", " + kniha.getVydani() + ", " + dostupnostPrepis(kniha.isDostupnost()) + ", " + typPrepis(kniha.isTyp()) + ", " + kniha.getExtra());

		  i.vkladani("INSERT INTO knihy " + "(nazev, autor, vydani, dotupnost, typ, extra)"
        + "VALUES('"+kniha.getNazev()+"', '"+kniha.getAutor()+"', "+kniha.getVydani()+", '"+dostupnostPrepis(kniha.isDostupnost())+"', '"+typPrepis(kniha.isTyp())+"', '"+kniha.getExtra()+"')");
		  }
	}
	public void nacteniDatabaze() {
		SQLovani i = new SQLovani();
		i.nacitani();
	}
	
	private Map<String,Kniha>  prvkyDatabaze;
	
	
}