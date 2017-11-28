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
            String getTS = "select b.tablespace_name, tbs_size SizeMb, a.free_space FreeMb\n" +
                            "from  (select tablespace_name, round(sum(bytes)/1024/1024 ,2) as free_space\n" +
                            "       from dba_free_space\n" +
                            "       group by tablespace_name) a,\n" +
                            "      (select tablespace_name, sum(bytes)/1024/1024 as tbs_size\n" +
                            "       from dba_data_files\n" +
                            "       group by tablespace_name) b\n" +
                            "where a.tablespace_name(+)=b.tablespace_name";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            
            while(rs.next()) {
                System.out.println(rs.getString(1) + "\t\t" + rs.getFloat(2));
            }
        }
        catch (Exception e) {
            System.out.println("Error getting Tablespaces!");
            e.printStackTrace();
        }
    }
}
