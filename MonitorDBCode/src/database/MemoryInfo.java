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
public class MemoryInfo{
        public float sga;
        public float pga;
           
        public MemoryInfo(float sga, float pga){
            this.sga = sga;
            this.pga = pga;
        }
        
        public String to_string(){
            StringBuilder sb = new StringBuilder();
            sb.append(sga);
            sb.append(", ");
            sb.append(pga);
            return sb.toString();
        }
    }