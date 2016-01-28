package utils;

import servlet.Customer;
import servlet.StatusRefresherScript;
import servlet.Student;
import servlet.VehicleRegistrationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static void updateCustomerStatus(Customer customer) {
        VehicleRegistrationStatus status = StatusRefresherScript.getVehicleStatus(customer.getTrNumber());
        if (null == status) {
            Logger.getAnonymousLogger().log(Level.INFO, "No change in status for TR Number " + customer.getTrNumber());
            return;
        }
        if (status.getStatus() != customer.getStatus() && status.getStatus().equals(MTT_CONSTANTS.ACTIVE_REGISTRATION_STATUS)) {
            // inform customer
            informCustomer(customer, status);
        }
        customer.setStatus(status.getStatus());
        customer.setRegistrationNumber(status.getRegistrationNumber());
    }

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

    public static void informCustomer(Customer customer, VehicleRegistrationStatus status) {
//        String smsToCustomer = String.format(MTT_CONSTANTS.INFORM_CUSTOMER_SMS, customer.getName(), status.getMakerClass(),
//                customer.getTrNumber(), status.getRegistrationNumber(), customer.getAgentName(), "9966422211");
//        String strUrl =  "http://api.mVaayoo.com/mvaayooapi/MessageCompose?user=reddy6sigma@gmail.com:iloveiit&senderID=TEST SMS&receipientno=9492215903&msgtxt=This is a test from mVaayoo API&state=4";
    }

//    public static String getAgentMo

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

    public static String getAgentUserNameForToken(String authToken) throws SQLException {
        String getAgentNameForTokenQuery = String.format(MTT_CONSTANTS.GET_VOLUNTEER_NAME_FOR_TOKEN_QUERY, authToken);
        Logger.getAnonymousLogger().log(Level.INFO, "getAgentNameQuery : " + getAgentNameForTokenQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getAgentNameForTokenQuery);
        String agentUserName = null;
        if (resultSet.next()) {
            agentUserName = resultSet.getString(MTT_CONSTANTS.VOLUNTEER_AUTH_TOKEN_TABLE_COLUMN_USER_NAME);
        }
        return agentUserName;
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

    public static List<Customer> getAllCustomers() throws Exception {
        return readCustomersFromQuery(MTT_CONSTANTS.GET_ALL_CUSTOMERS_QUERY);
    }

    public static List<Customer> getCustomerList(String agentUserName) throws Exception {
        String getUsersForAgentQuery = String.format(MTT_CONSTANTS.GET_USERS_FOR_AGENT_QUERY, agentUserName);
        return readCustomersFromQuery(getUsersForAgentQuery);
    }

    public static List<Student> getStudentList() throws Exception {
//        String getUsersForAgentQuery = String.format(MTT_CONSTANTS.GET_USERS_FOR_AGENT_QUERY, agentUserName);
//        return readCustomersFromQuery(getUsersForAgentQuery);
        // TODO:
        return new ArrayList<Student>();
    }

    public static List<Customer> readCustomersFromQuery(String query) throws Exception {
        Logger.getAnonymousLogger().log(Level.INFO, "readCustomersFromQuery method with query: " + query);
        String getUsersForAgentQuery = query;
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getUsersForAgentQuery);
        Logger.getAnonymousLogger().log(Level.INFO, "resultset size " + resultSet.getFetchSize());

        List<Customer> customerList = new ArrayList<Customer>();
        while(resultSet.next()) {
            Customer customer = new Customer();
            customer.setAgentName(resultSet.getString(MTT_CONSTANTS.CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setTrNumber(resultSet.getString(MTT_CONSTANTS.TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setMobileNumber(resultSet.getString(MTT_CONSTANTS.MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(MTT_CONSTANTS.VEHICLE_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(MTT_CONSTANTS.AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setStatus(getStatusForString(resultSet.getString(MTT_CONSTANTS.STATUS_COLUMN_DB_CUSTOMER_TABLE)));
            customer.setCreatedOn(resultSet.getDate(MTT_CONSTANTS.CREATED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
            customer.setModifiedOn(resultSet.getDate(MTT_CONSTANTS.MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
            customerList.add(customer);
        }
        return customerList;
    }

    public static Customer.Status getStatusForString(String status) {
        for(Customer.Status status1 : Customer.Status.values()) {
            if (status1.getStatusCode().equalsIgnoreCase(status)) return status1;
        }
        return null;
    }

    public static double effective_score(int correctly_answered, int wrongly_answered, int unanswered) {
        double result = 0.0;
        result = MTT_CONSTANTS.MIN_SCORE + MTT_CONSTANTS.WEIGHT_FACTOR *
                (1 - (double) correctly_answered / (double) (wrongly_answered + unanswered));
        return result;
    }

    public static int[] numberToPermutation(int n, int size) {
        int[] perms = new int[size];
        for (int i = 0; i < perms.length; i++) {
            perms[i] = i + 1;
        }

        for (int i = 0; i < size; i++) {
            int f = factorial(size - i - 1);

        }

        return perms;
    }

    // no overflow checks
    public static int factorial(int n) {
        int f = 1;
        for (int i = 2; i <= n; i++) {
            f *= i;
        }
        return f;
    }
}
