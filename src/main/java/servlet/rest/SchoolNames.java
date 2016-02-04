package servlet.rest;

import com.google.gson.Gson;
import servlet.ResourcesPath;
import utils.MTT_CONSTANTS;
import utils.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path(ResourcesPath.SCHOOLS)
public class SchoolNames {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchoolNamesWithPrefix(@PathParam("q") String q,
                               @HeaderParam(MTT_CONSTANTS.HTTP_COOKIE_HEADER_NAME) String cookie) throws Exception {
//        String authToken = Utils.getAuthToken(cookie);
//        if (!Utils.isValidAuthToken(authToken)) {
//            Response.ResponseBuilder builder = Response.serverError();
//            builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
//            return builder.build();
//        }
        System.out.println("\n\nprefix: " + q);
        System.out.println("cookie: " + cookie);
        List<String> resp = Utils.getPlacesWithPrefix(q);
        return Response.ok(new Gson().toJson(resp)).build();
    }
}
