/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmonitor;

import database.Connect;
import insertionDB.CreateTables;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author joaor
 */
public class DBMonitor {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        Connection connection = Connect.connect();
        
        CreateTables cT = new CreateTables(connection);
        Thread cTThread = new Thread(cT);
        cTThread.start();
        
        
        //10 Specifies the time interval in seconds to reprobe the DB
        Monitor m = new Monitor(connection, 4);
        Thread monitor = new Thread(m);
        monitor.start();
        
        //connection.close();
    }
    
}
