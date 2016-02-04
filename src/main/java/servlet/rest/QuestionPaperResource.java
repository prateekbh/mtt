package servlet.rest;

import com.google.gson.Gson;
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
    public Response getQuestionPaper(@PathParam(MTT_CONSTANTS.QUESTION_PAPER_CODE_REQUEST_PARAM) String qpId,
                               @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        System.out.println("qpId: " + qpId);
        int id = Integer.parseInt(qpId);
        int code = id;
        if (-1 == code) {
            return Response.ok().status(MTT_CONSTANTS.HTTP_NOT_FOUND_CODE).build();
        }
        if (code > MTT_CONSTANTS.NUMBER_OF_SETS) {
            return Response.ok().status(MTT_CONSTANTS.HTTP_NOT_FOUND_CODE).build();
        }
        System.out.println("Valid question paper code");
        QuestionPaperSet paperSet = new QuestionPaperSet(code);
        System.out.println(" returning in json object : paperSet : " + paperSet);
        return Response.ok(new Gson().toJson(paperSet)).build();
    }
}
