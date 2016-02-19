package servlet.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import utils.MTT_CONSTANTS;
import utils.Resources;
import utils.Utils;

import javax.rmi.CORBA.Util;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
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

        String json = new Gson().toJson(new ArrayList<String>());
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
        int qpCode = Integer.parseInt(questionPaperCode);
        if (qpCode < 1 || qpCode > MTT_CONSTANTS.NUMBER_OF_SETS) {
            System.out.println("Invalid Question Paper code : " + qpCode);
            Response.serverError().status(MTT_CONSTANTS.HTTP_NOT_FOUND_CODE).build();
        }
//        String schoolName = jsonNode.get(MTT_CONSTANTS.SCHOOL_ID_REQUEST_PARAM).asText();
//        String studentPlace = jsonNode.get(MTT_CONSTANTS.STUDENT_PLACE_REQUEST_PARAM).asText();
//        String studentCenter = jsonNode.get(MTT_CONSTANTS.CENTER_ID_REQUEST_PARAM).asText();
//        String sex = jsonNode.get(MTT_CONSTANTS.STUDENT_SEX_REQUEST_PARAM).asText();
        // Custom to ZPHS Narsapur test. Remove later.
        System.out.println("Custom for Narsapur");
        String schoolName = "ZPHS Narsapur, Narsapur, Adilabad";
        String studentPlace = "Narsapur";
        String studentCenter = "Narsapur";
        String sex = "...";
        int id = jsonNode.get(MTT_CONSTANTS.STUDENT_ID_REQUEST_PARAM).asInt();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "\n\n #### insertStudent form json : " + jsonRequest);

//        int id = 1;
//        synchronized (this) {
//            id = Utils.getMaxOfTable(MTT_CONSTANTS.TABLE_NAME_STUDENT, MTT_CONSTANTS.STUDENT_TABLE_COLUMN_ID) + 1;
//            System.out.println("student id : " + id);
//            insertStudentIntoDB(id, questionPaperCode, studentName, schoolName, studentPlace, studentCenter, sex);
//        }

        synchronized (this) {
            insertStudentIntoDB(id, questionPaperCode, studentName, schoolName, studentPlace, studentCenter, sex);
        }

        System.out.println("\n\nInserted student successfully.\n\n");
        HashMap<String, String> resp = new HashMap<String, String>(2);
        resp.put(MTT_CONSTANTS.STUDENT_ID_REQUEST_PARAM, String.valueOf(id));
        resp.put(MTT_CONSTANTS.QUESTION_PAPER_CODE_REQUEST_PARAM, String.valueOf(questionPaperCode));
        return Response.ok(new Gson().toJson(resp)).build();
    }

    private void insertStudentIntoDB(int id, String questionPaperCode, String studentName, String schoolName,
                                     String place, String center, String sex) throws Exception {

        String insertStudentQuery = String.format(MTT_CONSTANTS.INSERT_STUDENT_QUERY, String.valueOf(id),
                questionPaperCode, studentName, schoolName, place, center, sex);
        System.out.println("insertStudentQuery : " + insertStudentQuery);
        Statement getStatement = Resources.connection.createStatement();
        getStatement.execute(insertStudentQuery);
    }
}
