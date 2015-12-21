package servlet.filters;


import utils.MTT_CONSTANTS;
import utils.Utils;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String ignorePaths[] = new String[] {"/login", "/signup"};

    private static boolean isPathIgnored(String path) {
        for (String ignoredPath : ignorePaths) {
            if (ignoredPath.equalsIgnoreCase(path)) return true;
        }
        return false;
    }

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        if (ctx.getMethod() == HttpMethod.OPTIONS) {
            return;
        }

        if (isPathIgnored(ctx.getUriInfo().getPath())) {
            return;
        }

        String authToken = ctx.getCookies().containsKey(MTT_CONSTANTS.AUTH_TOKEN_COOKIE_NAME)
                ? ctx.getCookies().get(MTT_CONSTANTS.AUTH_TOKEN_COOKIE_NAME).getValue() : null;

        try {
            if (!Utils.isValidAuthToken(authToken)) {
                Response.ResponseBuilder builder = Response.serverError();
                builder.status(MTT_CONSTANTS.HTTP_UNAUTH_CODE);
                ctx.abortWith(builder.build());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Response.ResponseBuilder builder = Response.serverError();
            builder.status(MTT_CONSTANTS.HTTP_INTERNAL_SERVER_ERROR);
            ctx.abortWith(builder.build());
            return;
        }
    }
}
