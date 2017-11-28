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
 * @author gamar
 */
public class Users implements Runnable {
    private Connection c;
    
        public Users(Connection c) {
        this.c = c;
    }
    
    @Override
    public void run(){
        try{
            String getUsers = "select username, default_tablespace, temporary_tablespace, local_temp_tablespace, last_login from dba_users";
            PreparedStatement ps = this.c.prepareStatement(getUsers);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            
            while(rs.next()) {
                System.out.println(rs.getString(1) + "\t\t\t\t\t" + rs.getString(2) + "\t\t\t\t" + rs.getString(3) + 
                        "\t\t" + rs.getString(4) + "\t\t" + rs.getDate(5));
            }
        }
        catch(Exception e){
             System.out.println("Error getting Users!");
            e.printStackTrace();
        }
        
    }
}
