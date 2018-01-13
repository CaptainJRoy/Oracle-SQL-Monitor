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
import java.util.HashMap;

/**
 *
 * @author Bruno'
 */
public class Grants implements Runnable {
    private Connection c;
    public HashMap<String, UsersGrantInfo> usersinfo;
    
        public Grants(Connection c) {
        this.c = c;
        this.usersinfo = new HashMap<>();
        }
    
    @Override
    public void run(){
        try{
            String getPrivilegios = "select * from DBA_SYS_PRIVS order by GRANTEE";
            PreparedStatement ps = this.c.prepareStatement(getPrivilegios);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                if(usersinfo.get(rs.getString(1)) != null) {
                    usersinfo.get(rs.getString(1)).privilege.add(rs.getString(2));
                    usersinfo.get(rs.getString(1)).admin_option.add(rs.getBoolean(3));
                    usersinfo.get(rs.getString(1)).common.add(rs.getBoolean(4));
                    usersinfo.get(rs.getString(1)).inherited.add(rs.getBoolean(5));
                }
                else usersinfo.put(rs.getString(1),
                        new UsersGrantInfo(rs.getString(1),rs.getString(2),rs.getBoolean(3),rs.getBoolean(4),rs.getBoolean(5)));
            }
            
            System.out.println("Grants: " + usersinfo.toString());
        }
        catch(Exception e){
            System.out.println("Error getting Privileges!");
            e.printStackTrace();
        }
        
    }
}