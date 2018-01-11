/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmonitor;

import database.Datafiles;
import database.Tablespaces;
import database.Users;
import static java.lang.Thread.sleep;
import java.sql.Connection;

/**
 *
 * @author joaor
 */
public class Monitor implements Runnable {
    private Connection c;
    private int REFRESH_TIME; //in milis
    
    public Monitor(Connection c, int time) {
        this.c = c;
        this.REFRESH_TIME = time * 1000;
    }
    
    
    
    @Override
    public void run() {
        try {
            sleep(this.REFRESH_TIME);
            
            Tablespaces ts = new Tablespaces(this.c);
            Thread tablespaces = new Thread(ts);
            tablespaces.start();
            
            Users us = new Users(this.c);
            Thread users = new Thread(us);
            users.start();
            
            Datafiles dfs = new Datafiles(this.c);
            Thread datafiles = new Thread(dfs);
            datafiles.start();
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
