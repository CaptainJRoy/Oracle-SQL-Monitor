/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author joaor
 */
public class Table implements Runnable {
    class TablesInfo {
        public String schema;
        public String object;
        public float object_size;
        public String tablespace;
        
        public TablesInfo(String s, String o, float obj, String u) {
            this.schema = s;
            this.object = o;
            this.object_size = obj;
            this.tablespace = u;
        }
    }
    
    
    private Connection c;
    public ArrayList<TablesInfo> tables = new ArrayList<>();

    
    public Table(Connection c) {
        this.c = c;
    }
    
    
    private void read_Tablespaces() {
        try {
            String getTS =  "select\n" +
                            "  owner as \"Schema\"\n" +
                            "  , segment_name as \"Object Name\"\n" +
                            "  , round(bytes/1024/1024,2) as \"Object Size (Mb)\"\n" +
                            "  , tablespace_name as \"Tablespace\"\n" +
                            "from dba_segments\n" +
                            "where segment_type = 'TABLE'\n" +
                            "order by owner";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                tables.add(new TablesInfo(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4)));
            }
            
            System.out.println("Tables: " + tables.toString());
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
