/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmonitor;

import database.Connect;
import database.Monitor;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author joaor
 */
public class DBMonitor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = Connect.connect();
        Monitor m = new Monitor(connection, 5);
        Thread monitor = new Thread(m);
        
        monitor.start();
        
        //connection.close();
    }
    
}
