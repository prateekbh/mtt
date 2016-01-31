package servlet.rest;

import servlet.ResourcesPath;
import utils.MTT_CONSTANTS;
import utils.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path(ResourcesPath.QUESTION_PAPER)
public class QuestionPaperResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getQuestionPaper(@PathParam(MTT_CONSTANTS.STUDENT_ID_REQUEST_PARAM) String studentId,
                               @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        System.out.println("studentId: " + studentId);
        int id = Integer.parseInt(studentId);
        int code = Utils.getPaperCodeForStudent(id);
        int[] order = Utils.numberToPermutation(code);
        QuestionPaperSet paperSet = new QuestionPaperSet(code, order);
        return Response.ok(paperSet).build();
    }
}
