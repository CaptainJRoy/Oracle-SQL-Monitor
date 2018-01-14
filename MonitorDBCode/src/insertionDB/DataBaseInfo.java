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
    
    private HashMap<String, String> tableset_datafile;
    private void save_info_datafiles(){
        try{
            for(DataFileInfo value : datafile.values()) {
                String run =  "insert into datafiles values('" + value.datafile + "', " +
                              value.total_mb + ", " + value.used_mb + ", " +
                              value.pct_used + ")";

                PreparedStatement ps = this.c.prepareStatement(run);
                ResultSet rs = ps.executeQuery();
            }
        }
        catch(Exception e) {
            System.out.println("Nenhum datafile novo!");
        }
        finally {
            tableset_datafile = new HashMap<>();
            for(DataFileInfo value : datafile.values()) tableset_datafile.put(value.tablespace, value.datafile);
        }
    }
    
    
    private void save_info_grants(){
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            
            for (UsersGrantInfo value : grants.values()) {
                for(int i = 0; i < value.privilege.size(); i++) {
                    String run =  "insert into grants values('" + value.grantee + "', '" + value.privilege.get(i) + "', " +
                              (value.admin_option.get(i)?1:0) + ", " + (value.common.get(i)?1:0) + ", " +
                              (value.inherited.get(i)?1:0) + ", TO_DATE('" + dataStr + "', 'YYYY/MM/DD HH:MI:SS'))";

                    PreparedStatement ps = this.c.prepareStatement(run);
                    ResultSet rs = ps.executeQuery();
                }
            }
        }
        catch(Exception e) {
            System.out.println("Nenhum grant novo!");
        }
    }
    
    
    private HashMap<String, ArrayList<String>> tableset_user;
    private void save_info_users(){
        try{
            tableset_user = new HashMap<>();
            for (UsersInfo value : users.values()) {
                if(tableset_user.containsKey(value.default_tablespace)) {
                    tableset_user.get(value.default_tablespace).add(value.userName);
                }
                else {
                    tableset_user.put(value.default_tablespace, new ArrayList<>());
                    tableset_user.get(value.default_tablespace).add(value.userName);
                }
                String run =  "insert into users values('" + value.userName + "', '" + value.default_tablespace + "', '" +
                              value.temporary_tablespace + "',";
                if(value.last_login != null) 
                    run +=  "TO_DATE('" + value.last_login + "', 'YYYY-MM-DD HH:MI:SS'), '";
                else run += "NULL, '";
                run += value.userName + "')";
                PreparedStatement ps = this.c.prepareStatement(run);
                ResultSet rs = ps.executeQuery();
            }
        }
        catch(Exception e) {
            System.out.println("Nenhum user novo!");
        }
    }
    
    
    private void save_info_tablespaces(){
        try{
            for (TableSpaceInfo value : tablespace.values()) {
                ArrayList<String> al = tableset_user.get(value.tablespace);
                String ts_df = tableset_datafile.get(value.tablespace);
                if(al != null)
                    for(String s : al) {
                        String run =  "insert into tablespaces values('" + value.tablespace + "', " + value.pct_used + ", " +
                                  value.total_mb + ", " + value.used_mb + ", " + value.free_mb +
                                  ", " + value.datafiles + ", '" + s + "', " +
                                  (ts_df!=null?("'"+ts_df+"'"):"NULL") + ")";
                        PreparedStatement ps = this.c.prepareStatement(run);
                        ResultSet rs = ps.executeQuery();
                    }
            }
        }
        catch(Exception e) {
            System.out.println("Nenhum tablespaces novo!");
        }
    }
    
    
    private void save_info_tables(){
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            
            for (TablesInfo value : tables) {
                String run =  "insert into tables values(TO_DATE('" + dataStr + "', 'YYYY/MM/DD HH:MI:SS'), '" + value.schema +
                              "', '" + value.object + "', " + value.object_size + ", '" + value.tablespace + "')";

                PreparedStatement ps = this.c.prepareStatement(run);
                ResultSet rs = ps.executeQuery();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados tables!");
        }
    }
    
    
    @Override
    public void run() {
        save_info_memory();
        save_info_cpu();
        save_info_sessions();
        save_info_datafiles();
        save_info_grants();
        save_info_users();
        save_info_tablespaces();
        save_info_tables();
    }
}
