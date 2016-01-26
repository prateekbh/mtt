package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import utils.MTT_CONSTANTS;
import utils.Resources;
import utils.Utils;

import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path(ResourcesPath.LOGIN)
public class Login {

    public Login() {

    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String badGet()
            throws ServletException, IOException {
        return "bad try :(";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response handleRequest(String jsonRequest) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonRequest);
        Iterator<String> iterator = jsonNode.fieldNames();
        while (iterator.hasNext()) {
            System.out.println("fieldName:  " + iterator.next());
        }
        String uname = jsonNode.get(MTT_CONSTANTS.VOLUNTEER_USER_NAME_REQUEST_PARAM).textValue();
        String pwd = jsonNode.get(MTT_CONSTANTS.VOLUNTEER_PASSWORD_REQUEST_PARAM).asText();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.INFO, "#### Login.handleRequest method uname " + uname + " pwd " + pwd);

        Response.ResponseBuilder responseBuilder = Response.ok();
        if (isValid(uname, pwd)) {
            responseBuilder.cookie(getUserCookie(uname));
            
        } else {
            responseBuilder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
        }
        return responseBuilder.build();
    }

    public NewCookie getUserCookie(String uname) throws Exception {
        String token = Utils.renewAuthTokenForUser(uname);
        return new NewCookie(MTT_CONSTANTS.AUTH_TOKEN_COOKIE_NAME, token);
    }

    private boolean isValid(String userName, String password) throws Exception {
        String getUnamePwdQuery = String.format(MTT_CONSTANTS.GET_UNAME_PWD_DB_QUERY, userName);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getUnamePwdQuery);
        String readPwd = null;
        while (resultSet.next()) {
            readPwd = resultSet.getString(MTT_CONSTANTS.VOLUNTEER_TABLE_COLUMN_PASSWORD);
        }
        System.out.println("uname and pwd are VALID ####");
        return isValidPassword(readPwd, password);
    }

    private boolean isValidPassword(String dbPassword, String givenPassword) {
        // todo : now plain .. should be hashed later
        System.out.println(" db pwd : " + dbPassword + " given : " + givenPassword);
        return givenPassword.equalsIgnoreCase(dbPassword);
    }
}
