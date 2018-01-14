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
public class SessionsInfo{
        public int sid;
        public String box;
        public String username;
        public String os_user;
        public String program;
    
        
        public SessionsInfo(int sid, String box, String username, String os_user, String program){
            this.sid = sid;
            this.box = box;
            this.username = username;
            this.os_user = os_user;
            this.program = program;
        }
    }