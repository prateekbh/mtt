package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by gopi on 26/7/14.
 */

public class Resources {
    private static final String configFile = "/opt/mtt/config/main.properties";

    private static final String DB_URL_ = "url";
    private static final String DB_HOST_NAME_ = "hostName";
    private static final String DB_PORT_ = "port";
    private static final String DB_NAME_ = "dbname";
    private static final String DB_USER_ = "user";
    private static final String DB_PASSWORD_ = "password";
    private static final String PWD_HASHING_ALGO_ = "passwordHashingAlgo";

    public static String DB_URL;
    public static String DB_HOST_NAME;
    public static String DB_PORT;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;

    public static String PWD_HASHING_ALGO;

    public static Connection connection;
    public static Properties properties = new Properties();

    public static void loadResources() throws Exception {
        try {
            properties.load(new FileInputStream(utils.Resources.configFile));

            DB_URL = properties.getProperty(DB_URL_);
            DB_HOST_NAME = properties.getProperty(DB_HOST_NAME_);
            DB_PORT = properties.getProperty(DB_PORT_);
            DB_NAME = properties.getProperty(DB_NAME_);
            DB_USER = properties.getProperty(DB_USER_);
            DB_PASSWORD = properties.getProperty(DB_PASSWORD_);
            PWD_HASHING_ALGO = properties.getProperty(PWD_HASHING_ALGO_);

            System.out.println(" hostname : " + DB_HOST_NAME + " port : " + DB_PORT +
                    " dbname : " + DB_NAME + " userName : " + DB_USER + " password : " + DB_PASSWORD);
            Logger.getAnonymousLogger().log(Level.INFO, " hostname : " + DB_HOST_NAME + " port : " + DB_PORT +
                    " dbname : " + DB_NAME + " userName : " + DB_USER + " password : " + DB_PASSWORD +
            "\n: final url: " + DB_URL + DB_HOST_NAME + ":" + DB_PORT + "/" + DB_NAME);
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(DB_URL + DB_HOST_NAME + ":" + DB_PORT + "/" + DB_NAME,
                        DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cleanUp() throws Exception {
        connection.close();
    }

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
}
