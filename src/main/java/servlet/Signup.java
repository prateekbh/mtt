package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.Resources;
import utils.TSRTA_CONSTANTS;

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

@Path("/signup")
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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        String uname = jsonNode.get(TSRTA_CONSTANTS.USER_NAME_REQUEST_PARAM).asText();
        String pwd = jsonNode.get(TSRTA_CONSTANTS.PASSWORD_REQUEST_PARAM).asText();
        String agentName = jsonNode.get(TSRTA_CONSTANTS.AGENT_NAME_REQUEST_PARAM).asText();
        //store the uname and pwd in DB. Also call login to get auth_token and return the same.
        Response.ResponseBuilder responseBuilder = Response.ok();
        if (userAlreadyExists(uname)) {
            System.out.println("User already exists ####");
            responseBuilder.status(TSRTA_CONSTANTS.HTTP_CONFLICT_CODE);
            return responseBuilder.build();
        }
        insertNewAgentIntoDB(uname, pwd, agentName);
        responseBuilder.status(TSRTA_CONSTANTS.HTTP_OK_CODE);
        responseBuilder.cookie(new Login().getUserCookie(uname));
        return responseBuilder.build();
    }

    private boolean userAlreadyExists(String userName) throws Exception {
        Statement query = Resources.connection.createStatement();
        String agentExistenceQuery = String.format(TSRTA_CONSTANTS.AGENT_EXISTENCE_ENQURY_DB_QUERY, userName);
        ResultSet resultSet = query.executeQuery(agentExistenceQuery);
        boolean userAlreadyExists = resultSet.next();
        query.close();
        return userAlreadyExists;
    }

    private void insertNewAgentIntoDB(String uname, String pwd, String agentName) throws Exception {
        String hashedPassword = getHashedPassword(pwd);
        String insertAgentQuery = String.format(TSRTA_CONSTANTS.INSERT_AGENT_DB_QUERY, uname, hashedPassword, agentName);
        Statement query = Resources.connection.createStatement();
        query.execute(insertAgentQuery);
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
