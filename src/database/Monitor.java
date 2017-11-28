/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import static java.lang.Thread.sleep;
import java.sql.Connection;

/**
 *
 * @author joaor
 */
public class Monitor implements Runnable {
    private Connection c;
    private final int REFRESH_TIME = 5000; //in milis
    
    public Monitor(Connection c) {
        this.c = c;
    }
    
    
    
    @Override
    public void run() {
        try {
            sleep(this.REFRESH_TIME);
            Tablespaces ts = new Tablespaces(this.c);
            Thread tablespaces = new Thread(ts);
            tablespaces.start();
            Users us = new Users(this.c);
            Thread usrs = new Thread(us);
            usrs.start();
        }
        catch (Exception e) {
            System.out.println("Monitor stopped working!");
            e.printStackTrace();
        }
        finally {
            run();
        }
    }
}
