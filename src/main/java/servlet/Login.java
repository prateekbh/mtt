package servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import utils.Resources;
import utils.TSRTA_CONSTANTS;
import utils.Utils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/login")
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
        String uname = jsonNode.get(TSRTA_CONSTANTS.USER_NAME_REQUEST_PARAM).textValue();
        String pwd = jsonNode.get(TSRTA_CONSTANTS.PASSWORD_REQUEST_PARAM).asText();

        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, "#### Login.handleRequest method uname " + uname + " pwd " + pwd);

        Response.ResponseBuilder responseBuilder = Response.ok();
        if (isValid(uname, pwd)) {
            responseBuilder.cookie(getUserCookie(uname));
        } else {
            responseBuilder.status(TSRTA_CONSTANTS.HTTP_UNAUTH_CODE);
        }
        return responseBuilder.build();
    }

    public NewCookie getUserCookie(String uname) throws Exception {
        String token = Utils.renewAuthTokenForUser(uname);
        return new NewCookie(TSRTA_CONSTANTS.AUTH_TOKEN_COOKIE_NAME, token);
    }

    private boolean isValid(String userName, String password) throws Exception {
        String getUnamePwdQuery = String.format(TSRTA_CONSTANTS.GET_UNAME_PWD_DB_QUERY, userName);
        Statement getStatement = Resources.connection.createStatement();
        ResultSet resultSet = getStatement.executeQuery(getUnamePwdQuery);
        String readPwd = null;
        while (resultSet.next()) {
            readPwd = resultSet.getString(TSRTA_CONSTANTS.PASSWORD_HASH_COLUMN_DB_AGENT_TABLE);
        }
        System.out.println("uname and pwd are VALID ####");
        return isValidPassword(readPwd, password);
    }

    private boolean isValidPassword(String dbPassword, String givenPassword) {
        // todo : now plain .. should be hashed later
        System.out.println(" db pwd : " + dbPassword + " given : " + givenPassword);
        return givenPassword.equals(dbPassword);
    }
}
