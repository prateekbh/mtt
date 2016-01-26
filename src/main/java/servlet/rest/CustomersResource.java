package servlet.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import utils.MTT_CONSTANTS;
import utils.Resources;
import utils.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlet.Customer;
import servlet.ResourcesPath;

@Path(ResourcesPath.CUSTOMERS)
public class CustomersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomersForAgent(@HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        String json = new Gson().toJson(Utils.getCustomerList(Utils.getAgentUserNameForToken(authToken)));
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertCustomer(String jsonRequest,
                                   @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        String customerName = jsonNode.get(MTT_CONSTANTS.NAME_PUT_CUSTOMER_PARAM).textValue();
        String trNumber = jsonNode.get(MTT_CONSTANTS.TR_NUMBER_PUT_CUSTOMER_PARAM).asText();
        String mobileNumber = jsonNode.get(MTT_CONSTANTS.MOBILE_NUMBER_PUT_CUSTOMER_PARAM).asText();
        String vehicleDetails = jsonNode.get(MTT_CONSTANTS.VEHICLE_PUT_CUSTOMER_PARAM).asText();
        String agentName = jsonNode.get(MTT_CONSTANTS.AGENT_NAME_PUT_CUSTOMER_PARAM).asText();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "\n\n #### insertCustomer form json : " + jsonRequest);

        insertCustomerIntoDB(customerName, trNumber, mobileNumber, vehicleDetails, agentName);
        return getCustomer(trNumber, cookie);
    }

    private void insertCustomerIntoDB(String customerName, String trNumber, String mobileNumber, String vehicleDetails,
                                      String agentName) throws Exception {

        String insertCustomerQuery = String.format(MTT_CONSTANTS.INSERT_CUSTOMER_QUERY, customerName, trNumber,
                mobileNumber, vehicleDetails, agentName, Customer.Status.NEW);
        Statement getStatement = Resources.connection.createStatement();
        getStatement.execute(insertCustomerQuery);
    }

    public Response getCustomer(String trNumber, String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        Customer customer = readCustomerFromDB(trNumber);
        return Response.ok(new Gson().toJson(customer)).build();
    }

    private Customer readCustomerFromDB(String trNumber) throws Exception {
        String readCustomerQuery = String.format(MTT_CONSTANTS.READ_CUSTOMER_DB_QUERY, trNumber);
        Logger.getAnonymousLogger().log(Level.INFO, "\nRead Customer Query : " + readCustomerQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(readCustomerQuery);
        Customer customer = new Customer();
        if (resultSet.next()) {
            customer.setName(resultSet.getString(MTT_CONSTANTS.CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setTrNumber(resultSet.getString(MTT_CONSTANTS.TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setMobileNumber(resultSet.getString(MTT_CONSTANTS.MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(MTT_CONSTANTS.VEHICLE_COLUMN_DB_CUSTOMER_TABLE));
            customer.setAgentName(resultSet.getString(MTT_CONSTANTS.AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setStatus(Utils.getStatusForString(resultSet.getString(MTT_CONSTANTS.STATUS_COLUMN_DB_CUSTOMER_TABLE)));
            customer.setCreatedOn(resultSet.getDate(MTT_CONSTANTS.CREATED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
            customer.setModifiedOn(resultSet.getDate(MTT_CONSTANTS.MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
        }
        return customer;
    }
}
