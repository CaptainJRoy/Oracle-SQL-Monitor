package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect() throws SQLException {
        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return null;
        }

        System.out.println("Oracle JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "system", "oracle");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

        if (connection != null) System.out.println("You made it, take control your database now!");
        else System.out.println("Failed to make connection!");
        return connection;
    }
}