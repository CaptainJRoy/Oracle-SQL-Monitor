/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmonitor;

import database.Connect;
import insertionDB.CreateTables;
import java.sql.Connection;

/**
 *
 * @author joaor
 */
public class DBMonitor {

    
    public static void main(String[] args) {
        try {
            Connection connection = Connect.connect();
            CreateTables cT = new CreateTables(connection);
            Thread cTThread = new Thread(cT);
            cTThread.start();

            //10 Specifies the time interval in seconds to reprobe the DB
            Connection connection2 = Connect.connect();
            Monitor m = new Monitor(connection2, 10);
            Thread monitor = new Thread(m);
            monitor.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
