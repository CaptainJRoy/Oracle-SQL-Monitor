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
public class Tablespaces implements Runnable {
    class TableInfo {
        public String tablespace;
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
        }
    }
    
    
    private Connection c;
    public HashMap<String, TableInfo> tablespaces;

    
    public Tablespaces(Connection c) {
        this.c = c;
        this.tablespaces = new HashMap<>();
    }
    
    
    private void read_Tablespaces() {
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
                if(tablespaces.get(rs.getString(1)) != null)
                    tablespaces.replace(rs.getString(1),
                                new TableInfo(rs.getString(1), rs.getFloat(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5), rs.getFloat(6)));
                else tablespaces.put(rs.getString(1),
                                     new TableInfo(rs.getString(1), rs.getFloat(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5), rs.getFloat(6)));
            }
            
            System.out.println("Tablespaces: " + tablespaces.toString());
        }
        catch (Exception e) {
            System.out.println("Error getting Tablespaces!");
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        this.read_Tablespaces();
    }
}
