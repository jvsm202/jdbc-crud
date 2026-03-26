package database;

import java.sql.*;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/bdados";
    private static final String USER = "root";
    private static final String PASSWORD = "password3368";

    public static Connection openConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
