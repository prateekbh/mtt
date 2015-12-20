package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBQueryExecutor {
    private static Connection connection;
    public static void load() throws SQLException{
        String url = Resources.DB_URL;
        String hostName = Resources.DB_HOST_NAME;
        String port = Resources.DB_PORT;
        String dbName = Resources.DB_NAME;
        String userName = Resources.DB_USER;
        String password = Resources.DB_PASSWORD;

        try {
            connection = DriverManager.getConnection
                    (url + hostName + ":" + port + "/" + dbName,
                            userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
