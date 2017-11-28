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
public class Datafiles implements Runnable{
    class DataFileInfo {
        /*public String tablespace;
        public float pct_used;
        public float total_mb;
        public float used_mb;
        public float free_mb;
        public float datafiles;
        
        public TableInfo(String t, float p, float tot, float u, float f, float d) {
            this.tablespace = t;
            this.pct_used = p;
            this.total_mb = tot;
            this.used_mb = u;
            this.free_mb = f;
            this.datafiles = d;
        }*/
    }
    
    
    private Connection c;
    private HashMap<String, DataFileInfo> datafiles;

    
    public Datafiles(Connection c) {
        this.c = c;
        this.datafiles = new HashMap<>();
    }
    
    
    private void read_Datafiles() {
        try {
            String getTS =  "SELECT  a.tablespace_name tablespace,\n" +
                            "    ROUND (((c.BYTES - NVL (b.BYTES, 0)) / c.BYTES) * 100,2) \"Pct. Used\",\n" +
                            "    c.BYTES / 1024 / 1024 \"Total MB\",\n" +
                            "    ROUND (c.BYTES / 1024 / 1024 - NVL (b.BYTES, 0) / 1024 / 1024,2) \"Used MB\",\n" +
                            "    ROUND (NVL (b.BYTES, 0) / 1024 / 1024, 2) \"Free MB\", \n" +
                            "    c.DATAFILES\n" +
                            "  FROM dba_tablespaces a,\n" +
                            "       (    SELECT   tablespace_name, \n" +
                            "                  SUM (BYTES) BYTES\n" +
                            "           FROM   dba_free_space\n" +
                            "       GROUP BY   tablespace_name\n" +
                            "       ) b,\n" +
                            "      (    SELECT   COUNT (1) DATAFILES, \n" +
                            "                  SUM (BYTES) BYTES, \n" +
                            "                  tablespace_name\n" +
                            "           FROM   dba_data_files\n" +
                            "       GROUP BY   tablespace_name\n" +
                            "    ) c\n" +
                            "  WHERE b.tablespace_name(+) = a.tablespace_name \n" +
                            "    AND c.tablespace_name(+) = a.tablespace_name\n" +
                            "ORDER BY NVL (((c.BYTES - NVL (b.BYTES, 0)) / c.BYTES), 0) DESC";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                if(datafiles.get(rs.getString(1)) != null)
                    datafiles.replace(rs.getString(1), new DataFileInfo());
                else datafiles.put(rs.getString(1), new DataFileInfo());
            }
        }
        catch (Exception e) {
            System.out.println("Error getting Datafiles!");
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        this.read_Datafiles();
    }
}
