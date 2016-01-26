package servlet.rest;

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
import servlet.*;


@Path(ResourcesPath.STUDENT)
public class StudentResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam(MTT_CONSTANTS.STUDENT_ID_REQUEST_PARAM) String studentId,
                                @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        Student student = readStudentFromDB(studentId);
        return Response.ok(new Gson().toJson(student)).build();
    }

    private Student readStudentFromDB(String trNumber) throws Exception {
//        String readCustomerQuery = String.format(MTT_CONSTANTS.READ_CUSTOMER_DB_QUERY, trNumber);
//        Logger.getAnonymousLogger().log(Level.INFO, "\nRead Customer Query : " + readCustomerQuery);
//        Statement getStatement = Resources.connection.createStatement();
//        ResultSet resultSet = getStatement.executeQuery(readCustomerQuery);
//        Customer customer = new Customer();
//        if (resultSet.next()) {
//            customer.setName(resultSet.getString(MTT_CONSTANTS.CUSTOMER_NAME_COLUMN_DB_CUSTOMER_TABLE));
//            customer.setTrNumber(resultSet.getString(MTT_CONSTANTS.TR_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
//            customer.setMobileNumber(resultSet.getString(MTT_CONSTANTS.MOBILE_NUMBER_COLUMN_DB_CUSTOMER_TABLE));
//            customer.setVehicleDetails(resultSet.getString(MTT_CONSTANTS.VEHICLE_COLUMN_DB_CUSTOMER_TABLE));
//            customer.setAgentName(resultSet.getString(MTT_CONSTANTS.AGENT_NAME_COLUMN_DB_CUSTOMER_TABLE));
//            customer.setStatus(Utils.getStatusForString(resultSet.getString(MTT_CONSTANTS.STATUS_COLUMN_DB_CUSTOMER_TABLE)));
//            customer.setCreatedOn(resultSet.getDate(MTT_CONSTANTS.CREATED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
//            customer.setModifiedOn(resultSet.getDate(MTT_CONSTANTS.MODIFIED_ON_COLUMN_DB_CUSTOMER_TABLE).getTime());
//        }
//        return customer;
        return new Student();
    }
}
