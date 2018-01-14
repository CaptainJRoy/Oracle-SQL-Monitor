package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    public static Connection connect() throws SQLException {
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
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "hr", "oracle");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return null;
        }

        if (connection != null) System.out.println("Connection Estabilished!");
        else System.out.println("Failed to make connection!");
        return connection;
    }
    
    
    public static Connection connect_sys() throws SQLException {
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

        if (connection != null) System.out.println("Connection Estabilished!");
        else System.out.println("Failed to make connection!");
        return connection;
    }
}