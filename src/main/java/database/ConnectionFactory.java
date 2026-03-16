package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/bdados";
    private static final String USER = "root";
    private static final String PASSWORD = "password3368";

    // The returned connection will be treated and closed in the DAO
    // (Closed automatic in the try-with-resources).
    public static Connection createConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
