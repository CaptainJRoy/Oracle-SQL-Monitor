package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    private static final String USERNAME = "sys";
    private static String PASSWORD = "oracle";
    private static final String URL = "127.0.0.1:5502";
    private static final String SCHEMA = "orcl";


    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:oracle:thin:127.0.0.1:5501:orcl12c", "sys", "oracle");
    }

}