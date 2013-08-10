package co.schmitt.si.db;

import co.schmitt.si.model.Sport;
import co.schmitt.si.model.TrainingDate;

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
    private static final String SQL_DATES = "SELECT KURS.name, KURS.GEBUEHREN, KURS.MIN_TEILNEHMER, KURS.MAX_TEILNEHMER, KURS.TEILNEHMERANZAHL, TERMIN.wochentag, termin.uhrzeit_von, termin.uhrzeit_bis FROM kurs INNER JOIN kurs_termin ON kurs_termin.kurs_id = kurs.id INNER JOIN termin ON termin.id = kurs_termin.termin_id WHERE kurs.name=?";
    public static final String FIELD_WEEK_DAY = "WOCHENTAG";
    public static final String FIELD_START_TIME = "UHRZEIT_VON";
    public static final String FIELD_END_TIME = "UHRZEIT_BIS";

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
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
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
            if (mConnnection == null)
                mConnnection = getConnection();
            PreparedStatement ps = mConnnection.prepareStatement(SQL_DATES);
            ps.setString(1, sport.getName());
            ResultSet result = ps.executeQuery();
            // TODO Error handling
            int maxParticipants = -1, fees = -1, participants = -1, weekDay;
            Time startTime, endTime;
            List<TrainingDate> trainingDates = new ArrayList<>();

            while (result.next()) {
                // TODO No need to loop over participants and fees !
                maxParticipants = result.getInt(FIELD_MAX_PARTICIPANTS);
//                    minParticipants = result.getInt(FIELD_MIN_PARTICIPANTS);
                participants = result.getInt(FIELD_PARTICIPANTS);
                fees = result.getInt(FIELD_FEES);
                weekDay = result.getInt(FIELD_WEEK_DAY);
                startTime = result.getTime(FIELD_START_TIME);
                endTime = result.getTime(FIELD_END_TIME);
                trainingDates.add(new TrainingDate(weekDay, startTime, endTime));
                // TODO remove following line
                printAll(result);
            }
            sport.setParticipants(participants);
            sport.setMaxParticipants(maxParticipants);
            sport.setFees(fees);
            sport.setTrainingDates(trainingDates);
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
            System.out.println(resultSet.getString(FIELD_NAME) + "\t" + resultSet.getInt(FIELD_FEES) + "\t" + resultSet.getInt(FIELD_MIN_PARTICIPANTS) + "\t" + resultSet.getInt(FIELD_MAX_PARTICIPANTS) + "\t" + resultSet.getInt(FIELD_PARTICIPANTS) + "\t" + resultSet.getInt(FIELD_WEEK_DAY) + "\t" + resultSet.getString(FIELD_START_TIME) + "\t" + resultSet.getString(FIELD_END_TIME));
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
