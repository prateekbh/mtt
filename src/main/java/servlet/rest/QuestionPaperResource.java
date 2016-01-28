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
    public Response getQuestionPaper(@PathParam(MTT_CONSTANTS.QUESTION_PAPER_CODE_REQUEST_PARAM) String paperCode,
                               @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
        String authToken = Utils.getAuthToken(cookie);
        if (!Utils.isValidAuthToken(authToken)) {
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
            return builder.build();
        }

        int code = Integer.parseInt(paperCode);

        return Response.ok().build();
    }
}
