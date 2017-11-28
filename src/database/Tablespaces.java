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
 * @author joaor
 */
public class Tablespaces implements Runnable {
    private Connection c;
    
    
    public Tablespaces(Connection c) {
        this.c = c;
    }
    
    @Override
    public void run() {
        try {
            String getTS =  "select\n" +
                            "   fs.tablespace_name                          \"Tablespace\",\n" +
                            "   (df.totalspace - fs.freespace)              \"Used MB\",\n" +
                            "   fs.freespace                                \"Free MB\",\n" +
                            "   df.totalspace                               \"Total MB\",\n" +
                            "   round(100 * (fs.freespace / df.totalspace)) \"Pct. Free\"\n" +
                            "from\n" +
                            "   (select\n" +
                            "      tablespace_name,\n" +
                            "      round(sum(bytes) / 1048576) TotalSpace\n" +
                            "   from\n" +
                            "      dba_data_files\n" +
                            "   group by\n" +
                            "      tablespace_name\n" +
                            "   ) df,\n" +
                            "   (select\n" +
                            "      tablespace_name,\n" +
                            "      round(sum(bytes) / 1048576) FreeSpace\n" +
                            "   from\n" +
                            "      dba_free_space\n" +
                            "   group by\n" +
                            "      tablespace_name\n" +
                            "   ) fs\n" +
                            "where\n" +
                            "   df.tablespace_name = fs.tablespace_name";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            
            while(rs.next()) {
                System.out.println("Tablespace: " + rs.getString(1) + "\t\t" +
                                   "Used MB: " + rs.getFloat(2) + "\t\t" +
                                   "Free MB: " + rs.getFloat(3) + "\t\t" +
                                   "Total MB: " + rs.getFloat(4) + "\t\t" +
                                   "Pct. Free: " + rs.getFloat(5));
            }
        }
        catch (Exception e) {
            System.out.println("Error getting Tablespaces!");
            e.printStackTrace();
        }
    }
}
