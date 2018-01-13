/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Bruno'
 */
public class Memory implements Runnable {
    public class MemoryInfo{
        public float sga;
        public float pga;
           
        public MemoryInfo(float sga, float pga){
            this.sga = sga;
            this.pga = pga;
        }
        
        public String to_string(){
            StringBuilder sb = new StringBuilder();
            sb.append("SGA: ");
            sb.append(sga);
            sb.append("\nPGA:");
            sb.append(pga);
            return sb.toString();
        }
    }
       
    private Connection c;
    public MemoryInfo memoryInfo;
    
    public Memory(Connection c) {
        this.c = c;
        this.memoryInfo = new MemoryInfo(0, 0);
    }
           
    
    public void write(String dataStr) throws SQLException {
        String run =  "insert into memory values(TO_DATE('" +
                       dataStr + "', 'YYYY/MM/DD HH:MI:SS'), " +
                       this.memoryInfo.sga + ", " + this.memoryInfo.pga + ")";
        
        PreparedStatement ps = this.c.prepareStatement(run);
        ResultSet rs = ps.executeQuery();
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

            System.out.println("Memory: " + memoryInfo.toString());
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