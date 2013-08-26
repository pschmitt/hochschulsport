package co.schmitt.si.db;

import co.schmitt.si.model.Sport;
import co.schmitt.si.model.TrainingDate;
import co.schmitt.si.ontology.OntologyString;
import co.schmitt.si.tools.ListDuplicateRemover;

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
    private static final String DB_FILE_LEGACY = "jdbc:hsqldb:file:res/database/";
    private static final String DB_FILE = "jdbc:hsqldb:res:database/hochschulsport";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";
    public static boolean LEGACY = false;
    private static Connection mConnnection;

    private DBProvider() {}

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
        Connection con = DriverManager.getConnection(LEGACY ? DB_FILE_LEGACY : DB_FILE, DB_USER, DB_PASSWORD);
        con.setAutoCommit(true); // hm... how 'bout no ?
        con.setReadOnly(true);
        return con;
    }

    /**
     * Retrieve extra information from the database
     *
     * @param sport The sport
     * @return Sport item, holding all extra data (fees, ...)
     * @deprecated Use gradDetails(sport) instead
     */
    @Deprecated
    private static Sport grabDetailsLegacy(Sport sport) {
        try {
            if (mConnnection == null) {
                mConnnection = getConnection();
            }
            PreparedStatement ps = mConnnection.prepareStatement(DBQueries.SQL_DETAIL_LEGACY);
            ps.setString(1, OntologyString.convert(sport.getName()));
            ResultSet result = ps.executeQuery();
            // TODO Error handling
            int maxParticipants = -1, fees = -1, participants = -1, weekDay;
            Time startTime, endTime;
            TrainingDate trainingDate;
            List<TrainingDate> trainingDates = new ArrayList<>();

            while (result.next()) {
                // TODO No need to loop over participants and fees !
                maxParticipants = result.getInt(DBFields.FIELD_MAX_PARTICIPANTS_LEGACY);
                //                    minParticipants = result.getInt(FIELD_MIN_PARTICIPANTS_LEGACY);
                participants = result.getInt(DBFields.FIELD_PARTICIPANTS_LEGACY);
                fees = result.getInt(DBFields.FIELD_FEES_LEGACY);
                weekDay = result.getInt(DBFields.FIELD_WEEK_DAY_LEGACY);
                startTime = result.getTime(DBFields.FIELD_START_TIME_LEGACY);
                endTime = result.getTime(DBFields.FIELD_END_TIME_LEGACY);
                // Build TrainingDate object and add it to the list if not already present
                trainingDate = new TrainingDate(weekDay, startTime, endTime);
                if (!trainingDates.contains(trainingDate)) {
                    trainingDates.add(trainingDate);
                }
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
     * Retrieve extra information from the database
     *
     * @param sport The sport
     * @return Sport item, holding all extra data (fees, ...)
     */
    private static Sport grabDetails(Sport sport) {
        try {
            if (mConnnection == null) {
                mConnnection = getConnection();
            }
            PreparedStatement ps = mConnnection.prepareStatement(DBQueries.SQL_DETAILS);
            ps.setString(1, OntologyString.convert(sport.getName()));
            ResultSet result = ps.executeQuery();
            // TODO Error handling
            int maxParticipants = -1, minParticipants = -1, fees = -1, participants = -1, weekDay;
            String maxParticipantsString, minParticipantsString;
            Time startTime, endTime;
            TrainingDate trainingDate;
            List<TrainingDate> trainingDates = new ArrayList<>();

            while (result.next()) {
                // TODO No need to loop over participants and fees !
                maxParticipantsString = result.getString(DBFields.FIELD_MAX_PARTICIPANTS);
                minParticipantsString = result.getString(DBFields.FIELD_MIN_PARTICIPANTS);
                if (maxParticipantsString.equals(DBFields.FIELD_VALUE_UNLIMITED)) {
                    maxParticipants = -1;
                } else {
                    maxParticipants = Integer.parseInt(maxParticipantsString);
                }
                if (minParticipantsString.equals(DBFields.FIELD_VALUE_UNLIMITED)) {
                    minParticipants = -1;
                } else {
                    minParticipants = Integer.parseInt(minParticipantsString);
                }
                //                participants = result.getInt(FIELD_PARTICIPANTS);
                fees = result.getInt(DBFields.FIELD_FEES);
                weekDay = result.getInt(DBFields.FIELD_WEEK_DAY);
                if (result.getTime(DBFields.FIELD_START_TIME) != null) {
                    startTime = result.getTime(DBFields.FIELD_START_TIME);
                } else {
                    startTime = null;
                }
                if (result.getTime(DBFields.FIELD_END_TIME) != null) {
                    endTime = result.getTime(DBFields.FIELD_END_TIME);
                } else {
                    endTime = null;
                }
                // Build TrainingDate object and add it to the list if not already present
                trainingDate = new TrainingDate(weekDay, startTime, endTime);
                if (!trainingDates.contains(trainingDate)) {
                    trainingDates.add(trainingDate);
                }
            }
            sport.setMaxParticipants(maxParticipants);
            sport.setParticipants(participants);
            sport.setMaxParticipants(maxParticipants);
            sport.setMinParticipants(minParticipants);
            sport.setFees(fees);
            trainingDates = ListDuplicateRemover.removeDuplicates(trainingDates);
            sport.setTrainingDates(trainingDates);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sport;
    }

    /**
     * Close database
     */
    private static void close() {
        if (mConnnection == null) {
            return;
        }
        try {
            mConnnection.prepareStatement(DBQueries.SQL_HALT).execute();
            mConnnection.close(); // Is this necessary ?
            mConnnection = null;
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
            enhancedSports.add(LEGACY ? grabDetailsLegacy(sport) : grabDetails(sport));
        }
        close();
        return enhancedSports;
    }
}
