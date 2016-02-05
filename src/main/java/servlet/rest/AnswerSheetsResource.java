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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import servlet.ResourcesPath;

@Path(ResourcesPath.ANSWER_SHEETS)
public class AnswerSheetsResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // todo
    public Response getAnswerSheets(@HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
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
    public Response processAnswers(String jsonRequest,
                               @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
//            Response.ResponseBuilder builder = Response.serverError();
//            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
//            return builder.build();
        }

        System.out.println("Received answers :) jsonRequest: " + jsonRequest);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        String studentId = jsonNode.get("student_id").textValue();
        String questionPaperCode = jsonNode.get("question_paper_code").textValue();
        JsonNode answers = jsonNode.get("answers");
        System.out.println("extracted studentId: " + studentId + " qp code " + questionPaperCode + " answers " + answers);
        // TODO: shit hacky way. Gotta cleanup.
        String marksCard = "";
        for (int i = 1; i <= MTT_CONSTANTS.NUMBER_OF_QUESTIONS_IN_2016; i++) {
            System.out.println("i: " + i + " resp " + answers.get(String.valueOf(i)));
            JsonNode answer = answers.get(String.valueOf(i));
            if (null == answer) {
                marksCard += 'U';   // unanswered
            } else {
                if (answer.asText().equalsIgnoreCase("Correct")) {
                    marksCard += 'C';
                } else if(answer.asText().equalsIgnoreCase("Wrong")) {
                    marksCard += 'W';
                } else {
                    System.out.println("************************ SOMETHING WRONG ************************");
                    throw new RuntimeException("Improper string as answer: " + answer);
                }
            }
        }

        String set0MarksCard = Utils.convertToSet0Answers(marksCard, Integer.parseInt(questionPaperCode));
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "\n\n #### inserting answers of student " + studentId + " answers: " + marksCard + " set0MarksCard " + set0MarksCard);

        insertAnswersIntoDB(studentId, questionPaperCode, marksCard, set0MarksCard);
        System.out.println("\n\nInserted answers successfully.\n\n");
        return Response.ok().build();
    }

    private void insertAnswersIntoDB(String studentId, String questionPaperCode, String answers, String set0Answers) throws Exception {

        String insertAnswersQuery = String.format(MTT_CONSTANTS.INSERT_ANSWERS_QUERY, studentId, questionPaperCode,
                answers, set0Answers);
        System.out.println("insertAnsQuery: " + insertAnswersQuery);
        Statement getStatement = Resources.connection.createStatement();
        getStatement.execute(insertAnswersQuery);
    }
}
