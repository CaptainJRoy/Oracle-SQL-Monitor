/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author joaor
 */
public class DataFileInfo {
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