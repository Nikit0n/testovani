
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


public class Test {

	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("Zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	
	public static float pouzeCisla(Scanner sc) {
		float cislo = 0;
		try
		{
			cislo = sc.nextFloat();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cislo ");
			sc.nextLine();
			cislo = pouzeCisla(sc);
		}
		return cislo;
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

	public static void main(String[] args) {	
		Scanner sc=new Scanner(System.in);
		Databaze mojeDatabaze=new Databaze();
		int volba;
		String zanr;
		int rok;
		rok = 0;
		
		
		String nazev = null;
		String autor;
		int vydani;
		String dostupnostInput;
		boolean dostupnost;
		String typInput;
		boolean typ; 
		String extra = "nic";
		String nazevSouboru;

		mojeDatabaze.nacteniDatabaze();
		
	    Connection conn = DBConnection.getDBConnection();
	    
		boolean run=true;
		while(run)
		{
			System.out.println("Vyberte pozadovanou cinnost:");
			System.out.println("1 .. vlozeni nove knihy");
			System.out.println("2 .. uprava knihy");
			System.out.println("3 .. odstraneni knihy");
			System.out.println("4 .. vypujceni/vraceni knihy");
			System.out.println("5 .. vypis knih");
			System.out.println("6 .. vyhledani knihy");
			System.out.println("7 .. vypis knih podle autora, zanru nebo pujceni");
			System.out.println("8 .. ulozeni informace o knize do souboru");
			System.out.println("9 .. nacteni knih ze souboru");
			System.out.println("10 .. ukonceni aplikace");
			
			volba=pouzeCelaCisla(sc);
			switch(volba)
			{
			case 1:
			    System.out.println("Zadejte nazev knihy:");
			    nazev = sc.next();
			    
			    System.out.println("Zadejte nazev autora:");
			    autor = sc.next();
			    
			    System.out.println("Zadejte rok vydani:");
			    vydani = pouzeCelaCisla(sc);
			    
			    System.out.println("Zadejte stav dostupnosti (A, N):");
			    dostupnostInput = sc.next();
			    dostupnost = dostupnostInput.equalsIgnoreCase("A");
			    
			    System.out.println("Zadejte typ knihy (R, U):");
			    typInput = sc.next();
			    typ = typInput.equalsIgnoreCase("U");
			
			    if (typ) {
			        System.out.println("Zadejte rocnik:");
			        extra = sc.next();
			    } else {
			        System.out.println("Vyberte zanr 1-pisen 2-oda 3-hymnus 4-sonet 5-zalm:");
			        volba = pouzeCelaCisla(sc);
			        switch (volba) {
			            case 1:
			                extra = "pisen";
			                break;
			            case 2:
			                extra = "oda";
			                break;
			            case 3:
			                extra = "hymnus";
			                break;
			            case 4:
			                extra = "sonet";
			                break;
			            case 5:
			                extra = "zalm";
			                break;
			            default:
			                System.out.println("Neplatna volba zanru.");
			                break;
			        }
			    }

			    System.out.println(nazev + ", " + autor + ", " + vydani + ", " + dostupnostPrepis(dostupnost) + ", " + typPrepis(typ) + ", " + extra);

			    if (!mojeDatabaze.setKniha(nazev, autor, vydani, dostupnost, typ, extra)) {
			        System.out.println("Kniha v databazi jiz existuje");
			    }
			    break;
					
				case 2:
				    System.out.println("Zadejte nazev knihy k uprave");
				    nazev = sc.next();
				    Kniha knihaKUprave = mojeDatabaze.getKniha(nazev);
				    if (knihaKUprave != null) {
				        System.out.println("Vyberte parametr k uprave:");
				        System.out.println("1 - Autor");
				        System.out.println("2 - Rok vydani");
				        System.out.println("3 - Stav dostupnosti");

				        volba = pouzeCelaCisla(sc);
				        switch (volba) {
				            case 1:
				                System.out.println("Zadejte noveho autora:");
				                autor = sc.next();
				                knihaKUprave.setAutor(autor);
				                System.out.println("Autor knihy " + nazev + " byl upraven na: " + autor);
				                break;
				            case 2:
				                System.out.println("Zadejte novy rok vydani:");
				                rok = pouzeCelaCisla(sc);
				                knihaKUprave.setVydani(rok);
				                System.out.println("Rok vydani knihy " + nazev + " byl upraven na: " + rok);
				                break;
				            case 3:
				                System.out.println("Zadejte novy stav dostupnosti (A, N):");
				                dostupnostInput = sc.next();
				                if (dostupnostInput.equalsIgnoreCase("A")) {
				                    dostupnost = true;
				                } else {
				                    dostupnost = false;
				                }
				                knihaKUprave.setDostupnost(dostupnost);
				                System.out.println("Stav dostupnosti knihy " + nazev + " byl upraven na: " + dostupnostInput);
				                break;
				            default:
				                System.out.println("Neplatna volba.");
				                break;
				        }
				    } else {
				        System.out.println("Knihy s nazvem " + nazev + " nebyla nalezena.");
				    }
				    break;
				    
				case 3:
					System.out.println("Zadejte nazev knihy k odstraneni");
					nazev=sc.next();
					if (mojeDatabaze.vymazKnihu(nazev))
						System.out.println(nazev + " odstranen ");
					else
						System.out.println(nazev + " neni v databazi ");
					break;
					
				case 4:
					System.out.println("Zadejte nazev knihy:");
					nazev = sc.next();
					System.out.println("Vyberte: Pujceni - 1, Vraceni - 2");
					volba = pouzeCelaCisla(sc);
					switch (volba) {
					case 1:
						mojeDatabaze.pujceniKnihy(nazev, true);
						break;
					case 2:
						mojeDatabaze.pujceniKnihy(nazev, false);
						break;
					 default:
			                System.out.println("Neplatna volba.");
			                break;
					}
					break;
					
				case 5:
					mojeDatabaze.vypisDatabaze();
					break;
					
				case 6:
					System.out.println("Zadejte nazev knihy");
					nazev=sc.next();
					Kniha info = null;
					info=mojeDatabaze.getKniha(nazev);
					if (info!=null)
						System.out.println(info.getNazev() + ", " + info.getAutor() + ", " + info.getVydani() + ", " + dostupnostPrepis(info.isDostupnost()) + ", " + typPrepis(info.isTyp()) + ", " + info.getExtra());
					else
						System.out.println("Vybrana polozka neexistuje");
					break;
					
				case 7:
					System.out.println("Vypis knih podle: Autora - 1, Zanru - 2, Pujcene knihy - 3");
					volba = pouzeCelaCisla(sc);
					switch (volba) {
					case 1:
						System.out.println("Zadejte autora:");
						autor = sc.next();
						mojeDatabaze.vypisKnihyAutor(autor);
						break;
					case 2:
						System.out.println("Zadejte zanr (pisen, oda, hymnus, sonet, zalm):");
						zanr = sc.next();
						List<Kniha> knihyZanru = mojeDatabaze.vypisKnihyZanr(zanr);
						if(!knihyZanru.isEmpty()) {
							System.out.println("Seznam knih zanru: " + zanr);
							for (Kniha kniha :knihyZanru) {
								System.out.println(kniha.getNazev() + ", " + kniha.getAutor());
							}
						} else {
							System.out.println("Nebylo nalezeno");
						}
						break;
					case 3:
						List<Kniha> pujceneKnihy = mojeDatabaze.vypisKnihyPujcene();
					    if (!pujceneKnihy.isEmpty()) {
					        System.out.println("Seznam pujcenych knih:");
					        for (Kniha kniha : pujceneKnihy) {
					            System.out.println(kniha.getNazev() + ", " + kniha.getAutor() + ", " + (kniha.isTyp() ? "Ucebnice" : "Roman"));
					        }
					    } else {
					        System.out.println("Nebylo nalezeno");
					    }
					    break;
					}
					break;
				    
				case 8:
					System.out.println("Zadejte nazev souboru:");
				    nazevSouboru = sc.next();
				    System.out.println("Zadejte nazev knihy, kterou chcete ulozit:");
				    nazev = sc.next();
				    Kniha vybranaKniha = mojeDatabaze.getKniha(nazev);
				    if (vybranaKniha != null) {
				        boolean uspesneUlozeno = mojeDatabaze.ulozKnihu(nazevSouboru);
				        if (uspesneUlozeno) {
				            System.out.println("Kniha " + nazev + " byla úspěšně uložena do souboru " + nazevSouboru);
				        } else {
				            System.out.println("Chyba při ukládání knihy " + nazev + " do souboru " + nazevSouboru);
				        }
				    } else {
				        System.out.println("Kniha " + nazev + " nebyla nalezena v databázi.");
				    }
				    break;
				    
				case 9:
				    System.out.println("Zadejte nazev souboru:");
				    String soubor = sc.next();
				    if (mojeDatabaze.nactiKnihu(soubor)) {
				        System.out.println("Kniha byla nactena.");
				    } else {
				        System.out.println("Chyba pri nacitani knihy.");
				    }
				    break;
				    
				case 10:
					mojeDatabaze.ulozeniDatabaze();
					run=false;
					break;

			}
			
		}
	}

}
