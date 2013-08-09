package co.schmitt.si.db;

import co.schmitt.si.model.Sport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: pschmitt
 * Date: 8/9/13
 * Time: 10:33 PM
 */
public class DBProvider {
    private static final String HSQL_JDBC = "org.hsqldb.jdbcDriver";
    private static final String DB_FILE = "jdbc:hsqldb:file:res/database/";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";
    private static final String FIELD_NAME = "Name";
    private static final String FIELD_FEES = "Gebuehren";
    private static final String FIELD_MIN_PARTICIPANTS = "min_teilnehmer";
    private static final String FIELD_MAX_PARTICIPANTS = "max_teilnehmer";
    private static final String FIELD_PARTICIPANTS = "teilnehmeranzahl";
    private static final String SQL_HALT = "SHUTDOWN";

    private static Connection mConnnection;

    private DBProvider() {
    }

    /**
     * @return Connection
     * @throws java.sql.SQLException
     * @author Yassir Klos
     */
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName(HSQL_JDBC).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = DriverManager.getConnection(DB_FILE, DB_USER, DB_PASSWORD);
        con.setAutoCommit(true); // hm... how 'bout no ?
        return con;
    }

    /**
     * Retrieve extra information from the database
     *
     * @param sport The sport
     * @return Sport item, holding all extra data (fees, ...)
     */
    private static Sport grabDetails(Sport sport) {
        try {
            String name = sport.getName();
            if (mConnnection == null)
                mConnnection = getConnection();
            // TODO Get more info (kurs_[anfang|ende])
            PreparedStatement ps = mConnnection.prepareStatement("select * from kurs where name = ?");
            ps.setString(1, sport.getName());
            ResultSet result = ps.executeQuery();
            // TODO Error handling
            while (result.next()) {
                sport.setParticipants(result.getInt(FIELD_PARTICIPANTS));
                sport.setMaxParticipants(result.getInt(FIELD_MAX_PARTICIPANTS));
                sport.setFees(result.getInt(FIELD_FEES));
                // TODO remove following line
                printAll(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sport;
    }

    /**
     * Print all fields returned by query
     * TODO Delete me !
     *
     * @param resultSet The ResultSet the DB returned
     */
    private static void printAll(ResultSet resultSet) {
        try {
            System.out.println(resultSet.getString(FIELD_NAME) + "\t" + resultSet.getInt(FIELD_FEES) + "\t" + resultSet.getInt(FIELD_MIN_PARTICIPANTS) + "\t" + resultSet.getInt(FIELD_MAX_PARTICIPANTS) + "\t" + resultSet.getInt(FIELD_PARTICIPANTS));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close database
     */
    private static void close() {
        if (mConnnection == null)
            return;
        try {
            mConnnection = getConnection();
            mConnnection.prepareStatement(SQL_HALT).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all data that is to be displayed to the user
     *
     * @param sports A list containing all sports corresponding the users answers
     * @return A list containing all matching sports + the timetable data (fees, course start | end, participants...)
     */
    public static List<Sport> getTimetableData(List<Sport> sports) {
        List<Sport> enhancedSports = new ArrayList<>();
        for (Sport sport : sports) {
            enhancedSports.add(grabDetails(sport));
        }
        close();
        return enhancedSports;
    }
}
