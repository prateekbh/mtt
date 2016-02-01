package utils;

import servlet.Student;

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
        int paperCode = 0;
        if (resultSet.next()) {
            paperCode = resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE);
        }
        System.out.println("studentId: " + studentId + " paperCode: " + paperCode);
        return paperCode;
    }

    public static String[] getAnswersForSet0() {
//        String getAnswersQuery = String.format(MTT_CONSTANTS.GET_STUDENT_RECORD_QUERY, studentId);
//        System.out.println("getStudentDetailsQuery : " + getAnswersQuery);
//        Statement statement = Resources.connection.createStatement();
//        ResultSet resultSet = statement.executeQuery(getAnswersQuery);
//        int paperCode = 0;
//        if (resultSet.next()) {
//            paperCode = resultSet.getInt(MTT_CONSTANTS.STUDENT_TABLE_COLUMN_QUESTION_PAPER_CODE);
//        }
//        System.out.println("studentId: " + studentId + " paperCode: " + paperCode);
//        return paperCode;
        return null;
    }

    public static List<Student> getStudentList() throws Exception {
        // TODO:
        return new ArrayList<Student>();
    }

    public static double effective_score(int correctly_answered, int wrongly_answered, int unanswered) {
        double result = 0.0;
        result = MTT_CONSTANTS.MIN_SCORE + MTT_CONSTANTS.WEIGHT_FACTOR *
                (1 - (double) correctly_answered / (double) (wrongly_answered + unanswered));
        return result;
    }

    private static List<Integer> order1 = new ArrayList<Integer>() {{
        add(1);add(2);add(3);add(4);add(5);add(6);add(7);add(8);add(9);add(10);add(11);add(12);
    }};

    private static List<Integer> order2 = new ArrayList<Integer>() {{
        add(10);add(12);add(9);add(3);add(7);add(5);add(8);add(11);add(2);add(1);add(6);add(4);
    }};

    private static List<Integer> order3 = new ArrayList<Integer>() {{
        add(3);add(5);add(7);add(9);add(11);add(2);add(4);add(6);add(8);add(10);add(12);add(1);
    }};

    private static List<Integer> order4 = new ArrayList<Integer>() {{
        add(2);add(6);add(4);add(10);add(8);add(2);add(4);add(6);add(8);add(10);add(12);add(1);
    }};

    private static HashMap<Integer, List<Integer>> codeToOrder = new HashMap<Integer, List<Integer>>();

    public static List<Integer> codeToOrderMapping(int code) {
        if (!codeToOrder.containsKey(code)) {
            System.out.println("Invalid code: " + code);
            return null;
        }
        return codeToOrder.get(code);
    }

    public static int[] numberToPermutation(int n) {
        int size = MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016;

        return new int[]{2, 3, 1, 5, 4};
        // TODO
//
//        int[] perms = new int[size];
//        for (int i = 0; i < perms.length; i++) {
//            perms[i] = i + 1;
//        }
//
//        for (int i = 0; i < size; i++) {
//            int f = factorial(size - i - 1);
//
//        }
//
//        return perms;
    }

    // no overflow checks
    public static int factorial(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) {
            f *= i;
        }
        return f;
    }

    public static boolean isPrime(int n) {
        if (n < 10 || n > 100) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
