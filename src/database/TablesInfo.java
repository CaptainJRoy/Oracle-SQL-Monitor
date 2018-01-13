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
public class TablesInfo {
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