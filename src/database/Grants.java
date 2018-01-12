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
    class UsersInfo{
        public String grantee;
        public ArrayList<String> privilege = new ArrayList<>();
        public ArrayList<Boolean> admin_option = new ArrayList<>();
        public ArrayList<Boolean> common = new ArrayList<>();
        public ArrayList<Boolean> inherited = new ArrayList<>();
    
        
        public UsersInfo(String grantee, String privilege, boolean admin_option, boolean common, boolean inherited){
            this.grantee = grantee;
            this.privilege.add(privilege);
            this.admin_option.add(admin_option);
            this.common.add(common);
            this.inherited.add(inherited);
        }
        
        public String to_String(){
            StringBuilder sb = new StringBuilder();
            sb.append("Grantee: ");
            sb.append(grantee);
            sb.append("\nPrivilege:");
            sb.append(privilege);
            sb.append("\nAdmin_option:");
            sb.append(admin_option);
            sb.append("\nCommon:");
            sb.append(common);
            sb.append("\nInherited:");
            sb.append(inherited);
            return sb.toString();
         }
    }
    
    private Connection c;
    public HashMap<String, UsersInfo> usersinfo;
    
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
                        new UsersInfo(rs.getString(1),rs.getString(2),rs.getBoolean(3),rs.getBoolean(4),rs.getBoolean(5)));
            }
            
            System.out.println("Grants: " + usersinfo.toString());
        }
        catch(Exception e){
            System.out.println("Error getting Privileges!");
            e.printStackTrace();
        }
        
    }
}