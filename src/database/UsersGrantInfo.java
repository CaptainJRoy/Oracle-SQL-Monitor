/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.ArrayList;

/**
 *
 * @author joaor
 */
public class UsersGrantInfo{
        public String grantee;
        public ArrayList<String> privilege = new ArrayList<>();
        public ArrayList<Boolean> admin_option = new ArrayList<>();
        public ArrayList<Boolean> common = new ArrayList<>();
        public ArrayList<Boolean> inherited = new ArrayList<>();
    
        
        public UsersGrantInfo(String grantee, String privilege, boolean admin_option, boolean common, boolean inherited){
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