package servlet.rest;

import com.google.gson.Gson;
import servlet.ResourcesPath;
import servlet.Student;
import utils.MTT_CONSTANTS;
import utils.Utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;


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

        Student student = Utils.getStudentById(Integer.parseInt(studentId));
        if (null == student) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Invalid student id requested. id: " + studentId);
            return Response.serverError().status(MTT_CONSTANTS.HTTP_NOT_FOUND_CODE).build();
        }
        Logger.getAnonymousLogger().log(Level.INFO, "getStudent by id. Student object: " + student);
        return Response.ok(new Gson().toJson(student)).build();
    }
}
