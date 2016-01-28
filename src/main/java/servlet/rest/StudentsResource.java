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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import servlet.ResourcesPath;

@Path(ResourcesPath.STUDENTS)
public class StudentsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents(@HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        String json = new Gson().toJson(Utils.getStudentList());
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(String jsonRequest,
                                   @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        String studentName = jsonNode.get(MTT_CONSTANTS.STUDENT_NAME_REQUEST_PARAM).textValue();
        String questionPaperCode = jsonNode.get(MTT_CONSTANTS.QUESTION_PAPER_CODE_REQUEST_PARAM).textValue();
        String schoolId = jsonNode.get(MTT_CONSTANTS.SCHOOL_ID_REQUEST_PARAM).asText();
        String studentPlace = jsonNode.get(MTT_CONSTANTS.STUDENT_PLACE_REQUEST_PARAM).asText();
        String studentCenter = jsonNode.get(MTT_CONSTANTS.CENTER_ID_REQUEST_PARAM).asText();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "\n\n #### insertStudent form json : " + jsonRequest);

        int id = 1;
        synchronized (this) {
            id = Utils.getMaxOfTable(MTT_CONSTANTS.TABLE_NAME_STUDENT, MTT_CONSTANTS.STUDENT_TABLE_COLUMN_ID) + 1;
            System.out.println("student id : " + id);
            insertStudentIntoDB(id, questionPaperCode, studentName, schoolId, studentPlace, studentCenter);
        }
        System.out.println("\n\nInserted student successfully.\n\n");
        return Response.ok(new Gson().toJson("Student ID: " + id)).build();
    }

    private void insertStudentIntoDB(int id, String questionPaperCode, String studentName, String schoolId,
                                     String place, String center) throws Exception {

        String insertStudentQuery = String.format(MTT_CONSTANTS.INSERT_STUDENT_QUERY, String.valueOf(id),
                questionPaperCode, studentName, schoolId, place, center);
        System.out.println("insertStudentQuery : " + insertStudentQuery);
        Statement getStatement = Resources.connection.createStatement();
        getStatement.execute(insertStudentQuery);
    }

//    public Response getStudent(String trNumber, String cookie) throws Exception {
//        String authToken = Utils.getAuthToken(cookie);
//        if (!Utils.isValidAuthToken(authToken)) {
//            Response.ResponseBuilder builder = Response.serverError();
//            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
//            return builder.build();
//        }
//
//        Customer customer = readCustomerFromDB(trNumber);
//        return Response.ok(new Gson().toJson(customer)).build();
//    }

//    private Customer readCustomerFromDB(String trNumber) throws Exception {
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
//    }
}
