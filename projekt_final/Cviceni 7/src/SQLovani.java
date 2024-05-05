import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class SQLovani {
	public SQLovani() {}

	public static boolean typPrepisZpet(String vstup) {
		if(vstup.equals("Ucebnice")) {
			return true;
	}else {
		return false;
	}
}
	public static boolean dostupnostPrepisZpet(String vstup) {
		if(vstup.equals("Dostupne")) {
			return true;
	}else {
		return false;
	}
}
	
	
	  public void vkladani(String insertQuery) {
		    if (insertQuery == null) {
		      throw new NullPointerException("query must not be null!");
		    } else if (insertQuery.isEmpty()) {
		      throw new IllegalArgumentException("query must not be empty!");
		    }
		    Connection conn = DBConnection.getDBConnection();
		    try (PreparedStatement prStmt = conn.prepareStatement(insertQuery);) {
		      int rowsInserted = prStmt.executeUpdate();
		      System.out.println("Byla vlozena kniha");
		    } catch (SQLException e) {
		      System.out.println("Nastala Chyba typu: " + e);
		    }
		  }
	  
	  public void nacitani() {
			Databaze mojeDatabaze=new Databaze();
		    Connection conn = DBConnection.getDBConnection();
		    boolean dostupnost = false;
		    boolean typ = false;
		    String vyber = "SELECT nazev, autor, vydani, dotupnost, typ, extra FROM knihy"; //nazev, autor, vydani, dotupnost, typ, extra
		    
		    try (PreparedStatement prStmt = conn.prepareStatement(vyber);
		        ResultSet rs = prStmt.executeQuery()) {
		    	System.out.println("Nactene knihy:");
		      while (rs.next()) {
		        System.out.println(rs.getString("nazev") + ", " + rs.getString("autor") + ", " + rs.getString("vydani")+ ", "+rs.getString("dotupnost") + ", " + rs.getString("typ")+ ", "+rs.getString("extra"));
		        dostupnost = dostupnostPrepisZpet(rs.getString("dotupnost"));
		        typ = typPrepisZpet(rs.getString("typ"));
		        mojeDatabaze.setKniha(rs.getString("nazev"), rs.getString("autor"), rs.getInt("vydani"), dostupnost, typ, rs.getString("extra"));
		      }
		    } catch (SQLException e) {
		      e.printStackTrace();
		    }
		  }
}
