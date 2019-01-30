package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasse um auf die SQLite DB zugreiffen zu können.
 */
public class Datenbank {

	private static String conString = "jdbc:sqlite:datenbank.db";
    private static Connection con = null;

    public Datenbank() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            Logger.getLogger(Datenbank.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    
    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(conString);
        } catch (SQLException ex) {
            Logger.getLogger(Datenbank.class.getName()).log(Level.SEVERE, null,
                    ex);
            con = null;
        }
        return con;
    }
    
    
    
    public static ResultSet executeQuery(String query) {
    	ResultSet r = null;	
    	try {
    		Statement st = getConnection().createStatement();	
			r = st.executeQuery(query);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return r;
        
    }
  

}