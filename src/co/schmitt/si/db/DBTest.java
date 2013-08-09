package co.schmitt.si.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBTest {
	
	static Connection con;
	/*
	 * Beispiel zur Implementierung einer abfrage
	 */
	public static void main(String[] args) {
        try {
            con = DBConnector.getConnection();
            
            PreparedStatement psTest = con.prepareStatement("select * from kurs");
            ResultSet rsTest = psTest.executeQuery();
            
            while (rsTest.next()) {
            	System.out.println(rsTest.getString("Name") + "\t" + rsTest.getInt("Gebuehren") + "\t" + rsTest.getInt("min_teilnehmer") + "\t" + rsTest.getInt("max_teilnehmer")+ "\t" + rsTest.getInt("teilnehmeranzahl"));
            }
            
            con.prepareStatement("SHUTDOWN").execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
