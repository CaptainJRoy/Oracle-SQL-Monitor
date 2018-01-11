/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author gamar
 */
public class Users implements Runnable {
    class UsersInfo{
        public String userName;
        public String default_tablespace;
        public String temporary_tablespace;
        public String local_temp_tablespace;
        public Date last_login;
        
        public UsersInfo(String userName, String defTs, String tempTs, String localTempTs, Date lastLogin){
            this.userName = userName;
            this.default_tablespace = defTs;
            this.temporary_tablespace = tempTs;
            this.local_temp_tablespace = localTempTs;
            this.last_login = lastLogin;
           
        }
        public String to_String(){
            StringBuilder sb = new StringBuilder();
            sb.append("Username: ");
            sb.append(userName);
            sb.append("\nDefault Tablespace:");
            sb.append(default_tablespace);
            sb.append("\nTemporary Tablespace:");
            sb.append(temporary_tablespace);
            sb.append("\nLocal Temporary Tablespace:");
            sb.append(local_temp_tablespace);
            sb.append("\nLastLogin:");
            sb.append(last_login);
            return sb.toString();
            }
        }
    
    private Connection c;
    private HashMap<String, UsersInfo> usersinfo;
    
        public Users(Connection c) {
        this.c = c;
        this.usersinfo = new HashMap<>();
    }
    
    @Override
    public void run(){
        try{
            String getUsers = "select username, default_tablespace, temporary_tablespace, local_temp_tablespace, last_login from dba_users";
            PreparedStatement ps = this.c.prepareStatement(getUsers);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                if(usersinfo.get(rs.getString(1)) != null)
                    usersinfo.replace(rs.getString(1),
                        new UsersInfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5)));
                else usersinfo.put(rs.getString(1),
                        new UsersInfo(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5)));
            }
            
            System.out.println("Users: " + usersinfo.toString());
        }
        catch(Exception e){
            System.out.println("Error getting Users!");
            e.printStackTrace();
        }
        
    }
}
