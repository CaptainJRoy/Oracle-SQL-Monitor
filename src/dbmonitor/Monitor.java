/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmonitor;

import database.*;
import insertionDB.*;
import static java.lang.Thread.sleep;
import java.sql.Connection;

/**
 *
 * @author joaor
 */
public class Monitor implements Runnable {
    private Connection c;
    private int REFRESH_TIME; //in milis
    public Tablespaces ts;
    public Users us;
    public Datafiles dfs;
    public Grants gran;
    public Table tab;
    public Sessions ses;
    public CPU cpu;
    public Memory mem;
    public DataBaseInfo dbinfo;
    
    public Monitor(Connection c, int time) {
        this.c = c;
        this.REFRESH_TIME = time * 1000;
        this.dbinfo = new DataBaseInfo(this.c, this);
    }

    
    @Override
    public void run() {
        try {
            ts = new Tablespaces(c);
            us = new Users(c);
            dfs = new Datafiles(c);
            gran = new Grants(c);
            tab = new Table(c);
            ses = new Sessions(c);
            cpu = new CPU(c);
            mem = new Memory(c);
            dbinfo = new DataBaseInfo(c, this);
        
            Thread tablespaces = new Thread(this.ts);
            tablespaces.start();
            
            Thread users = new Thread(this.us);
            users.start();
            
            Thread datafiles = new Thread(this.dfs);
            datafiles.start();
            
            Thread grants = new Thread(this.gran);
            grants.start();
            
            Thread tables = new Thread(this.tab);
            tables.start();
            
            Thread sessions = new Thread(this.ses);
            sessions.start();
            
            Thread cpu_info = new Thread(this.cpu);
            cpu_info.start();
            
            Thread memory = new Thread(this.mem);
            memory.start();
            
            Thread insertDB = new Thread(this.dbinfo);
            insertDB.start();
            
            sleep(this.REFRESH_TIME);
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
