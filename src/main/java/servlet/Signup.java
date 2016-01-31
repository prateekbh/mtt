package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Resources;
import utils.MTT_CONSTANTS;

import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

@Path(ResourcesPath.SIGNUP)
public class Signup {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String badGet()
            throws ServletException, IOException {
        System.out.println("GET asked for signup :)");
        return "bad try :(";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleRequest(String jsonRequest) throws Exception{
        System.out.println("POST method of singup");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        String volunteerName = jsonNode.get(MTT_CONSTANTS.VOLUNTEER_NAME_REQUEST_PARAM).asText();
        String vuname = jsonNode.get(MTT_CONSTANTS.VOLUNTEER_USER_NAME_REQUEST_PARAM).asText();
        String pwd = jsonNode.get(MTT_CONSTANTS.VOLUNTEER_PASSWORD_REQUEST_PARAM).asText();
        String volunteerCenterId = jsonNode.get(MTT_CONSTANTS.CENTER_ID_REQUEST_PARAM).asText();
        //store the uname and pwd in DB. Also call login to get auth_token and return the same.
//        System.out.println("uname: " + vuname + " pwd " + pwd
//                + " volunteerName " + volunteerName + " center: " + volunteerCenterId);
        Response.ResponseBuilder responseBuilder =
                Response.ok("{\"message\" : \"Thank you for being a volunteer.\"}");
        if (userAlreadyExists(vuname)) {
            System.out.println("Volunteer already exists.");
            responseBuilder.status(MTT_CONSTANTS.HTTP_CONFLICT_CODE);
            return responseBuilder.build();
        }
        insertNewVolunteerIntoDB(vuname, pwd, volunteerName, volunteerCenterId);
        responseBuilder.status(MTT_CONSTANTS.HTTP_OK_CODE);
        responseBuilder.cookie(new Login().getUserCookie(vuname));
        return responseBuilder.build();
    }

    private boolean userAlreadyExists(String userName) throws Exception {
        if (null == Resources.connection) {
            System.out.println("connection is null \n\n");
            return true;
        }
        Statement query = Resources.connection.createStatement();
        System.out.println("check query: " + MTT_CONSTANTS.VOLUNTEER_EXISTENCE_ENQURY_DB_QUERY);
        String userExistenceQuery = String.format(MTT_CONSTANTS.VOLUNTEER_EXISTENCE_ENQURY_DB_QUERY, userName);
        ResultSet resultSet = query.executeQuery(userExistenceQuery);
        boolean userAlreadyExists = resultSet.next();
        query.close();
        return userAlreadyExists;
    }

    private void insertNewVolunteerIntoDB(String uname, String pwd, String volunteerName, String centerId)
            throws Exception {
        String hashedPassword = getHashedPassword(pwd);
        String insertVolunteerQuery =
                String.format(MTT_CONSTANTS.INSERT_VOLUNTEER_DB_QUERY, uname, hashedPassword, volunteerName, centerId);
        Statement query = Resources.connection.createStatement();
        query.execute(insertVolunteerQuery);
        query.close();
    }

    private String getHashedPassword(String pwd) throws Exception {
        // todo : Should hash the password.
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance(Resources.PWD_HASHING_ALGO);
//            messageDigest.update(pwd.getBytes(PASSWORD_ENCODING));
//            byte[] hash = messageDigest.digest();
//            hashString = new String(hash, PASSWORD_ENCODING);
//        } catch(Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
        return pwd;
    }
}
