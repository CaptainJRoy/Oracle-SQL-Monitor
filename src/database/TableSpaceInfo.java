package database;

public class TableSpaceInfo {
        public String tablespace;
        public float pct_used;
        public float total_mb;
        public float used_mb;
        public float free_mb;
        public float datafiles;
        
        public TableSpaceInfo(String t, float p, float tot, float u, float f, float d) {
            this.tablespace = t;
            this.pct_used = p;
            this.total_mb = tot;
            this.used_mb = u;
            this.free_mb = f;
            this.datafiles = d;
        }
    }