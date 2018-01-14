/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertionDB;

import database.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author joaor
 */
public class DataBaseInfo implements Runnable {
    private Connection c;
    private HashMap<String, TableSpaceInfo> tablespace;
    private HashMap<String, UsersInfo> users;
    private HashMap<String, DataFileInfo> datafile;
    private HashMap<String, UsersGrantInfo> grants;
    private ArrayList<TablesInfo> tables;
    private HashMap<String, SessionsInfo> sessions;
    private HashMap<String, Float> cpu;
    private MemoryInfo memoryInfo;


    public DataBaseInfo(Connection c, HashMap<String, TableSpaceInfo> tbs, HashMap<String, UsersInfo> ui,
           HashMap<String, DataFileInfo> df, HashMap<String, UsersGrantInfo> grt,
           ArrayList<TablesInfo> tables, HashMap<String, SessionsInfo> sess,
           HashMap<String, Float> cpu, MemoryInfo mI) {

        this.c = c;
        this.tablespace = tbs;
        this.users = ui;
        this.datafile = df;
        this.grants = grt;
        this.tables = tables;
        this.sessions = sess;
        this.cpu = cpu;
        this.memoryInfo = mI;
    }
    
    
    private void save_info_memory() {
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            String run =  "insert into memory values(TO_DATE('" +
                        dataStr + "', 'YYYY/MM/DD HH:MI:SS'), " +
                        this.memoryInfo.to_string() + ")";
            System.out.println();
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados memória!");
        }
    }
    
    
    private void save_info_cpu() {
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            
            float idle_time = this.cpu.get("IDLE_TIME");
            float num_cpu = this.cpu.get("NUM_CPUS");
            float busy_time = this.cpu.get("BUSY_TIME");
            float user_time = this.cpu.get("USER_TIME");
            float iowait_time = this.cpu.get("IOWAIT_TIME");
            float nice_time = this.cpu.get("NICE_TIME");
            float num_cpu_cores = this.cpu.get("NUM_CPU_CORES");
            
            String run =  "insert into cpu values(TO_DATE('" +
                        dataStr + "', 'YYYY/MM/DD HH:MI:SS'), " +
                        num_cpu_cores + ", " + iowait_time +
                        ", " + nice_time + ", " + busy_time + ", " +
                        user_time + ", " + num_cpu + ", " + idle_time + ")";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados cpu!");
        }
    }
    
    
    private void save_info_sessions(){
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            
            for (SessionsInfo value : sessions.values()) {
            String run =  "insert into sessions values(" + value.sid + ", TO_DATE('" +
                          dataStr + "', 'YYYY/MM/DD HH:MI:SS'), '" + value.box + "', '" +
                          value.os_user + "', '" + value.program + "')";
            
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
        }
            }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados sessions!");
        
        }
    }
    
    
    @Override
    public void run() {
        save_info_memory();
        save_info_cpu();
        save_info_sessions();
    }
}
