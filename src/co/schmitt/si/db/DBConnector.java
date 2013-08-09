package co.schmitt.si.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Yassir Klos
 * Klasse stellt eine Verbindung mit der internen HSQL Datenbank her
 * Die HSQL Datenbank-Files liegen relativ zum Laufzeitverzeichnis im Ordner res/database
 *
 */
public class DBConnector {

    private DBConnector() { }

    /**
     * @return Connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        }
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:res/database/", "SA", "");
        con.setAutoCommit(true);
        return con;
    }
}
