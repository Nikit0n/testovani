
public class Kniha {
	public Kniha(String nazev, String autor, int vydani, boolean dostupnost, boolean typ, String extra)
	{
		this.nazev=nazev;
		this.autor=autor;
		this.vydani=vydani;
		this.dostupnost=dostupnost;
		this.typ=typ;
		this.extra=extra;
	}
	
	public String getNazev(){
		return nazev;
	}
	
	public void setNazev(String nazev) {
		this.nazev = nazev;
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getVydani() {
		return vydani;	
	}
	
	public void setVydani(int vydani) {
		this.vydani = vydani;
	}
	
	public boolean isDostupnost() {
		return dostupnost;
	}

	public void setDostupnost(boolean dostupnost) {
		this.dostupnost = dostupnost;
	}
	
	public boolean isTyp() {
		return typ;
	}
	
	public void setTyp(boolean typ) {
		this.typ = typ;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	private String nazev;
	private String autor;
	private int vydani;
	private boolean dostupnost;
	private boolean typ;
	private String extra;
}