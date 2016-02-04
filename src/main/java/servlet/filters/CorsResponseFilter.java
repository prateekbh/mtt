package servlet.filters;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

public class CorsResponseFilter implements ContainerResponseFilter {
	@Context HttpServletRequest request;
	
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		String origin = requestContext.getHeaderString("Origin");

//		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

		responseContext.getHeaders().add("Access-Control-Allow-Headers",
				"Origin, X-Requested-With, Content-Type, Accept, "
						+ "Access-Control-Allow-Origin, Access-Control-Request-Method, " 
						+ "Access-Control-Allow-Methods, AuthToken, Cookies");
		responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
		responseContext.getHeaders().add("Access-Control-Allow-Methods",
				"GET, POST, DELETE, PUT, OPTIONS");
//		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
	}
}
