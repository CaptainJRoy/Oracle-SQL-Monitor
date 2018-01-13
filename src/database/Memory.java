/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Bruno'
 */
public class Memory implements Runnable {
    private Connection c;
    public MemoryInfo memoryInfo;
    
    public Memory(Connection c) {
        this.c = c;
        this.memoryInfo = new MemoryInfo(0, 0);
    }
    
    public void get_Memory_stats() {
        try {
            String getTS =  "select sum(value)/1024/1024 SGA from v$sga\n" +
                            "union select round((sum(pga_used_mem)/1024/1024),1) PGA from v$process";
            
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();

            boolean pga = true;
            while(rs.next())
                if(pga) {
                    this.memoryInfo.pga = rs.getFloat(1);
                    pga = false;
                }
                else this.memoryInfo.sga = rs.getFloat(1);

            //System.out.println("Memory: " + memoryInfo.toString());
        }
        catch (Exception e) {
            System.out.println("Error getting Memory Stats!");
            e.printStackTrace();
        }
        finally {
        }
    }
    
    @Override
    public void run() {
        this.get_Memory_stats();
    }
}