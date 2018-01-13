/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Date;

/**
 *
 * @author joaor
 */
public class UsersInfo{
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