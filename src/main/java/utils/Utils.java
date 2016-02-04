package utils;

import servlet.Student;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static int getMaxOfTable(String tableName, String columnName) throws SQLException {
        String getMaxQuery = "select max(" + columnName + ") from " + tableName;
        System.out.println("getMaxQuery: " + getMaxQuery);
        Statement getMaxQueryStatement = Resources.connection.createStatement();
        ResultSet resultSet = getMaxQueryStatement.executeQuery(getMaxQuery);
        if (resultSet.next()) {
            System.out.println("Returning max: " + resultSet.getInt("max"));
            return resultSet.getInt("max");
        }
        System.out.println("Returning max -- default 1");
        return 1;   // for the first entry
    }

    public static boolean isValidAuthToken(String authToken) throws SQLException {
        if (null == authToken) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "authToken is null.\n");
            return false;
        }
        Logger.getAnonymousLogger().log(Level.INFO, "authToken for validation : " + authToken);
        String getTokenDetailsQuery = String.format(MTT_CONSTANTS.GET_TOKEN_DETAILS_DB_QUERY, authToken);
        Logger.getAnonymousLogger().log(Level.INFO, "getTokenQuery : " + getTokenDetailsQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getTokenDetailsQuery);
        long tokenTimeStamp = 0;
        if (resultSet.next()) {
            tokenTimeStamp = resultSet.getDate(MTT_CONSTANTS.VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_CREATED_ON).getTime();
        }
        Logger.getAnonymousLogger().log(Level.INFO, "current millis " + System.currentTimeMillis() + " tokenTimeStamp "
                + tokenTimeStamp + " expiry time " + MTT_CONSTANTS.TOKEN_EXPIRY_TIME + " final result " + (System.currentTimeMillis() < tokenTimeStamp + MTT_CONSTANTS.TOKEN_EXPIRY_TIME));
        return System.currentTimeMillis() < tokenTimeStamp + MTT_CONSTANTS.TOKEN_EXPIRY_TIME;
    }

    public static String renewAuthTokenForUser(String uname) throws Exception {
        String deleteTokenQuery = String.format(MTT_CONSTANTS.DELETE_AUTH_TOKEN_DB_QUERY, uname);
        Statement statement = Resources.connection.createStatement();
        statement.execute(deleteTokenQuery);

        String token = UUID.randomUUID().toString();
        String writeTokenQuery = String.format(MTT_CONSTANTS.WRITE_AUTH_TOKEN_DB_QUERY, uname, token);
        Statement writeStatement = Resources.connection.createStatement();
        writeStatement.execute(writeTokenQuery);
        return token;
    }

    public static String getAuthToken(String cookie) {
        Logger.getAnonymousLogger().log(Level.INFO, " cookie : " + cookie);
        String[] nameValuePairs = cookie.split(MTT_CONSTANTS.COOKIE_SEPARATOR);
        for (String pair : nameValuePairs) {
            String[] nameValue = pair.split(MTT_CONSTANTS.NAME_VALUE_SEPARATOR, 2);
            if (MTT_CONSTANTS.AUTH_TOKEN_COOKIE_NAME.equals(nameValue[0])) {
                return nameValue[1];
            }
        }
        return "";
    }

    public static int getPaperCodeForStudent(int studentId) throws Exception {
        String getStudentDetailsQuery = String.format(MTT_CONSTANTS.GET_STUDENT_RECORD_QUERY, studentId);
        System.out.println("getStudentDetailsQuery : " + getStudentDetailsQuery);
        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getStudentDetailsQuery);
        int paperCode = -1;
        if (resultSet.next()) {
            paperCode = resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE);
        }
        System.out.println("studentId: " + studentId + " paperCode: " + paperCode);
        return paperCode;
    }

    public static ArrayList<String> getPlacesWithPrefix(String prefix) throws SQLException {
        System.out.println("getPlaces: " + MTT_CONSTANTS.GET_PLACES_QUERY);
        String getPlaceQuery = String.format(MTT_CONSTANTS.GET_PLACES_QUERY, prefix);
        System.out.println("getPlacesQuery: " + getPlaceQuery);

        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getPlaceQuery);
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while (resultSet.next()) {
            String place = resultSet.getString("name");
            result.add(place);
        }
        System.out.println("place result: " + result);
        return result;
    }

    public static ArrayList<String> getSchoolNamesWithPrefix(String prefix) throws SQLException {
        System.out.println("getPlaces: " + MTT_CONSTANTS.GET_PLACES_QUERY);
        String getPlaceQuery = String.format(MTT_CONSTANTS.GET_PLACES_QUERY, prefix);
        System.out.println("getPlacesQuery: " + getPlaceQuery);

        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getPlaceQuery);
        ArrayList<String> result = new ArrayList<String>();
        int i = 0;
        while (resultSet.next()) {
            String place = resultSet.getString("name");
            result.add(place);
        }
        System.out.println("place result: " + result);
        return result;
    }

    public static String getSchoolIdForName(String name) {
        return "1";
    }

    public static String getCenterIdForName(String name) {
        return "1";
    }

    public static final String[] getQuestionsForSet0() {
        // TODO
        String[] set0 = new String[] {
                "Question 1 ?",
                "Question 2 ?",
                "Question 3 ?",
                "Question 4 ?",
                "Question 5 ?",
                "Question 6 ?",
                "Question 7 ?",
                "Question 8 ?",
                "Question 9 ?",
                "Question 10 ?",
                "Question 11 ?",
                "Question 12 ?"
        };
        return set0;
    }

    public static String[] getAnswersForSet0() throws Exception {
        String getAnswersQuery = String.format(MTT_CONSTANTS.GET_ANSWERS_QUERY);
        System.out.println("getStudentDetailsQuery : " + getAnswersQuery);
        Statement statement = Resources.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getAnswersQuery);
        String[] result = new String[MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016];
        while (resultSet.next()) {
            int serial_number = resultSet.getInt(MTT_CONSTANTS.ANSWERS_TABLE_COLUMN_SERIAL_NUMBER);
            String answer = resultSet.getString(MTT_CONSTANTS.ANSWERS_TABLE_COLUMN_ANSWER);
            System.out.println("num " + serial_number + " answer: " + answer);
            result[serial_number - 1] = answer;
        }
        return result;
    }

    public static List<Student> getStudentList() throws Exception {
        // TODO:
        return new ArrayList<Student>();
    }

    public static double effectiveScore(int correctly_answered, int wrongly_answered, int unanswered) {
        double result = 0.0;
        result = MTT_CONSTANTS.MIN_SCORE + MTT_CONSTANTS.WEIGHT_FACTOR *
                (1 - (double) correctly_answered / (double) (wrongly_answered + unanswered));
        return result;
    }
}
