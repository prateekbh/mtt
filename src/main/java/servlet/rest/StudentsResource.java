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
        String sex = jsonNode.get(MTT_CONSTANTS.STUDENT_SEX_REQUEST_PARAM).asText();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "\n\n #### insertStudent form json : " + jsonRequest);

        int id = 1;
        synchronized (this) {
            id = Utils.getMaxOfTable(MTT_CONSTANTS.TABLE_NAME_STUDENT, MTT_CONSTANTS.STUDENT_TABLE_COLUMN_ID) + 1;
            System.out.println("student id : " + id);
            insertStudentIntoDB(id, questionPaperCode, studentName, schoolId, studentPlace, studentCenter, sex);
        }
        System.out.println("\n\nInserted student successfully.\n\n");
        return Response.ok(new Gson().toJson("Student ID: " + id)).build();
    }

    private void insertStudentIntoDB(int id, String questionPaperCode, String studentName, String schoolId,
                                     String place, String center, String sex) throws Exception {

        String insertStudentQuery = String.format(MTT_CONSTANTS.INSERT_STUDENT_QUERY, String.valueOf(id),
                questionPaperCode, studentName, schoolId, place, center, sex);
        System.out.println("insertStudentQuery : " + insertStudentQuery);
        Statement getStatement = Resources.connection.createStatement();
        getStatement.execute(insertStudentQuery);
    }
}
