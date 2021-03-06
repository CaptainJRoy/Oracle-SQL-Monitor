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
 * @author Bruno'
 */
public class Sessions implements Runnable {
    private Connection c;
    public HashMap<String, SessionsInfo> sessions;
    
    
     public Sessions(Connection c) {
        this.c = c;
        this.sessions = new HashMap<>();
    }
     
     private void read_Sessions(){
         try{
             String getTS = "select \n" +
                            "sid, \n" +
                            "substr(b.machine,1,15) box, \n" +
                            "substr(b.username,1,10) username, \n" +
                            "substr(b.osuser,1,8) os_user,  \n" +
                            "substr(b.program,1,28) program \n" +
                            "from v$session b, v$process a \n" +
                            "where b.paddr = a.addr \n" +
                            "order by sid asc";
            
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
            
            
            while(rs.next()) {
                if(sessions.get(rs.getString(1)) != null)
                    sessions.replace(rs.getString(1), new SessionsInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                else sessions.put(rs.getString(1), new SessionsInfo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            System.out.println("Sesions: " + sessions.toString());
        }
        catch (Exception e) {
            System.out.println("Error getting Datafiles!");
            e.printStackTrace();
         }
     }
     
     @Override
    public void run() {
        this.read_Sessions();
    }
}
