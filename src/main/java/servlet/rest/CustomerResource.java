package servlet.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import utils.Resources;
import utils.TSRTA_CONSTANTS;
import utils.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import servlet.*;
import servlet.Customer.Status;


@Path(ResourcesPath.CUSTOMER)
public class CustomerResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCustomer(@PathParam("trNumber") String trNumber,
                                @HeaderParam(TSRTA_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(TSRTA_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        Customer customer = readCustomerFromDB(trNumber);
        return Response.ok(new Gson().toJson(customer)).build();
    }

    private Customer readCustomerFromDB(String trNumber) throws Exception {
        String readCustomerQuery = String.format(TSRTA_CONSTANTS.READ_CUSTOMER_DB_QUERY, trNumber);
        Logger.getAnonymousLogger().log(Level.INFO, "\nRead Customer Query : " + readCustomerQuery);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(readCustomerQuery);
        Customer customer = new Customer();
        if (resultSet.next()) {
            customer.setName(resultSet.getString(TSRTA_CONSTANTS.CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setTrNumber(resultSet.getString(TSRTA_CONSTANTS.TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setMobileNumber(resultSet.getString(TSRTA_CONSTANTS.MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
            customer.setVehicleDetails(resultSet.getString(TSRTA_CONSTANTS.VEHICLE_COLUMN_DB_CUSTOMER_TABLE));
            customer.setAgentName(resultSet.getString(TSRTA_CONSTANTS.AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE));
            customer.setStatus(Utils.getStatusForString(resultSet.getString(TSRTA_CONSTANTS.STATUS_COLUMN_DB_CUSTOMER_TABLE)));
            customer.setCreatedOn(resultSet.getDate(TSRTA_CONSTANTS.CREATED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
            customer.setModifiedOn(resultSet.getDate(TSRTA_CONSTANTS.MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
        }
        return customer;
    }
}
