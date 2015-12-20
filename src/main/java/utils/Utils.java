package utils;

import servlet.Customer;
import servlet.StatusRefresherScript;
import servlet.VehicleRegistrationStatus;
import sun.rmi.runtime.Log;

import javax.ws.rs.core.Response;
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
        if (status.getStatus() != customer.getStatus() && status.getStatus().equals(TSRTA_CONSTANTS.ACTIVE_REGISTRATION_STATUS)) {
            // inform customer
            informCustomer(customer, status);
        }
        customer.setStatus(status.getStatus());
        customer.setRegistrationNumber(status.getRegistrationNumber());
    }

    public static void informCustomer(Customer customer, VehicleRegistrationStatus status) {
        String smsToCustomer = String.format(TSRTA_CONSTANTS.INFORM_CUSTOMER_SMS, customer.getName(), status.getMakerClass(),
                customer.getTrNumber(), status.getRegistrationNumber(), customer.getAgentName(), "9966422211");
        String strUrl =  "http://api.mVaayoo.com/mvaayooapi/MessageCompose?user=reddy6sigma@gmail.com:iloveiit&senderID=TEST SMS&receipientno=9492215903&msgtxt=This is a test from mVaayoo API&state=4";
    }

//    public static String getAgentMo

    public static boolean isValidAuthToken(String authToken) throws SQLException {
        if (null == authToken) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "authToken is null.\n");
            return false;
        }
        Logger.getAnonymousLogger().log(Level.INFO, "authToken for validation : " + authToken);
        String getTokenDetailsQuery = String.format(TSRTA_CONSTANTS.GET_TOKEN_DETAILS_DB_QUERY, authToken);
        Logger.getAnonymousLogger().log(Level.INFO, "getTokenQuery : " + getTokenDetailsQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getTokenDetailsQuery);
        long tokenTimeStamp = 0;
        if (resultSet.next()) {
            tokenTimeStamp = resultSet.getDate(TSRTA_CONSTANTS.TOKEN_CREATED_ON_COLUMN_DB_AGENT_TOKEN_TABLE).getTime();
        }
        Logger.getAnonymousLogger().log(Level.INFO, "current millis " + System.currentTimeMillis() + " tokenTimeStamp "
                + tokenTimeStamp + " expiry time " + TSRTA_CONSTANTS.TOKEN_EXPIRY_TIME + " final result " + (System.currentTimeMillis() < tokenTimeStamp + TSRTA_CONSTANTS.TOKEN_EXPIRY_TIME));
        return System.currentTimeMillis() < tokenTimeStamp + TSRTA_CONSTANTS.TOKEN_EXPIRY_TIME;
    }

    public static String getAgentUserNameForToken(String authToken) throws SQLException {
        String getAgentNameForTokenQuery = String.format(TSRTA_CONSTANTS.GET_AGENT_NAME_FOR_TOKEN_QUERY, authToken);
        Logger.getAnonymousLogger().log(Level.INFO, "getAgentNameQuery : " + getAgentNameForTokenQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getAgentNameForTokenQuery);
        String agentUserName = null;
        if (resultSet.next()) {
            agentUserName = resultSet.getString(TSRTA_CONSTANTS.USER_NAME_COLUMN_DB_AGENT_TABLE);
        }
        return agentUserName;
    }

    public static String renewAuthTokenForUser(String uname) throws Exception {
        String deleteTokenQuery = String.format(TSRTA_CONSTANTS.DELETE_AUTH_TOKEN_DB_QUERY, uname);
        Statement statement = Resources.connection.createStatement();
        statement.execute(deleteTokenQuery);

        String token = UUID.randomUUID().toString();
        String writeTokenQuery = String.format(TSRTA_CONSTANTS.WRITE_AUTH_TOKEN_DB_QUERY, uname, token);
        Statement writeStatement = Resources.connection.createStatement();
        writeStatement.execute(writeTokenQuery);
        return token;
    }

    public static String getAuthToken(String cookie) {
        Logger.getAnonymousLogger().log(Level.INFO, " cookie : " + cookie);
        String[] nameValuePairs = cookie.split(TSRTA_CONSTANTS.COOKIE_SEPARATOR);
        for (String pair : nameValuePairs) {
            String[] nameValue = pair.split(TSRTA_CONSTANTS.NAME_VALUE_SEPARATOR, 2);
            if (TSRTA_CONSTANTS.AUTH_TOKEN_COOKIE_NAME.equals(nameValue[0])) {
                return nameValue[1];
            }
        }
        return "";
    }

    public static List<Customer> getAllCustomers() throws Exception {
        return readCustomersFromQuery(TSRTA_CONSTANTS.GET_ALL_CUSTOMERS_QUERY);
    }

    public static List<Customer> getCustomerList(String agentUserName) throws Exception {
        String getUsersForAgentQuery = String.format(TSRTA_CONSTANTS.GET_USERS_FOR_AGENT_QUERY, agentUserName);
        return readCustomersFromQuery(getUsersForAgentQuery);
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
            customer.setAgentName(resultSet.getString(TSRTA_CONSTANTS.CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setTrNumber(resultSet.getString(TSRTA_CONSTANTS.TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setMobileNumber(resultSet.getString(TSRTA_CONSTANTS.MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(TSRTA_CONSTANTS.VEHICLE_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(TSRTA_CONSTANTS.AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setStatus(getStatusForString(resultSet.getString(TSRTA_CONSTANTS.STATUS_COLUMN_DB_CUSTOMER_TABLE)));
            customer.setCreatedOn(resultSet.getDate(TSRTA_CONSTANTS.CREATED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
            customer.setModifiedOn(resultSet.getDate(TSRTA_CONSTANTS.MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
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

}
