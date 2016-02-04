package launch;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.*;
import servlet.rest.*;
import servlet.filters.*;
import utils.Resources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    //This is the place one needs to register the classes that need be instantiated.
    private static final String[] entryPoints = new String[] {
            Login.class.getCanonicalName(),
            Signup.class.getCanonicalName(),
            CorsResponseFilter.class.getCanonicalName(),
            StudentResource.class.getCanonicalName(),
            StudentsResource.class.getCanonicalName(),
            AnswerSheetResource.class.getCanonicalName(),
            AnswerSheetsResource.class.getCanonicalName(),
            QuestionPaperResource.class.getCanonicalName(),
            QuestionPapersResource.class.getCanonicalName(),
            Places.class.getCanonicalName(),
            SchoolNames.class.getCanonicalName(),
            AuthenticationFilter.class.getCanonicalName()
    };

    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
                org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        Resources.loadResources();
//        Tells the Jersey Servlet which REST service/class to load.
        String services = "";
        for (int i = 0; i < entryPoints.length; i++) {
            String entryPoint = entryPoints[i];
            services = services + (i == 0 ? "" : ", ") + entryPoint;
        }
        jerseyServlet.setInitParameter(
                "jersey.config.server.provider.classnames", services);
        Logger.getAnonymousLogger().log(Level.SEVERE, "\n\n HERE IAM .......... " + services + "\n\n");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
            Resources.cleanUp();
        }
    }
}
