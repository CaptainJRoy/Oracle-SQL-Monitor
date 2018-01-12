/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author joaor
 */
public class CPU implements Runnable {

    private Connection c;
    private HashMap<String, Float> cpu = new HashMap<>();

    public CPU(Connection c) {
        this.c = c;
    }
    
    public void read_CPU_Stats() {
        try {
            String getTS =  "select stat_name, value, comments from v$osstat\n" +
                            "where stat_name in ('IDLE_TIME', 'NUM_CPUS', 'IDLE_TIME', 'BUSY_TIME', 'USER_TIME', 'IOWAIT_TIME', 'NICE_TIME', 'NUM_CPU_CORES')";
            
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();

            while(rs.next())
                if(rs.getString(3).contains("(centi-secs)")) cpu.put(rs.getString(1), rs.getFloat(2)/100);
                else cpu.put(rs.getString(1), rs.getFloat(2));
            
            System.out.println("CPU: " + cpu.toString());
        }
        catch (Exception e) {
            System.out.println("Error getting CPU Stats!");
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        this.read_CPU_Stats();
    }
}