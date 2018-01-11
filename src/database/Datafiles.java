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
        public String tablespace;
        public String datafile;
        public float total_mb;
        public float used_mb;
        public float pct_used;
        
        public DataFileInfo(String t, String d, float tot, float u, float p) {
            this.tablespace = t;
            this.datafile = d;
            this.total_mb = tot;
            this.used_mb = u;
            this.pct_used = p;
        }
    }
    
    
    private Connection c;
    private HashMap<String, DataFileInfo> datafiles;

    
    public Datafiles(Connection c) {
        this.c = c;
        this.datafiles = new HashMap<>();
    }
    
    
    private void read_Datafiles() {
        try {
            String getTS =  "(SELECT  Substr(df.tablespace_name,1,20) \"Tablespace Name\",\n" +
                            "        Substr(df.file_name,1,80) \"File Name\",\n" +
                            "        Round(df.bytes/1024/1024,2) \"Size (M)\",\n" +
                            "        decode(e.used_bytes,NULL,0,Round(e.used_bytes/1024/1024,2)) \"Used (M)\",\n" +
                            "        decode(e.used_bytes,NULL,0,Round((e.used_bytes/df.bytes)*100,2)) \"% Used\"\n" +
                            "FROM    DBA_DATA_FILES DF,\n" +
                            "       (SELECT file_id,\n" +
                            "               sum(bytes) used_bytes\n" +
                            "        FROM dba_extents\n" +
                            "        GROUP by file_id) E,\n" +
                            "       (SELECT Max(bytes) free_bytes,\n" +
                            "               file_id\n" +
                            "        FROM dba_free_space\n" +
                            "        GROUP BY file_id) f\n" +
                            "WHERE    e.file_id (+) = df.file_id\n" +
                            "AND      df.file_id  = f.file_id (+)\n" +
                            ")\n" +
                            "UNION ALL\n" +
                            "SELECT \n" +
                            "    TABLESPACE_NAME \"TableSpace Name\",\n" +
                            "    FILE_NAME \"File Name\",\n" +
                            "    round(BYTES/1024/1024,2) \"SIZE (M)\",\n" +
                            "    round((BYTES/1024/1024)-(USER_BYTES/1024/1024),2) \"USED (M)\",\n" +
                            "    round((((BYTES/1024/1024)-(USER_BYTES/1024/1024))/(BYTES/1024/1024))*100,2) \"% USED\"\n" +
                            "    from dba_temp_files";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                if(datafiles.get(rs.getString(1)) != null)
                    datafiles.replace(rs.getString(1), new DataFileInfo(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5)));
                else datafiles.put(rs.getString(1), new DataFileInfo(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5)));
            }
            System.out.println("Datafiles: " + datafiles.toString());
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
