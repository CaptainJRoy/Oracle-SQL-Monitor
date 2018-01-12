/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertionDB;

import dbmonitor.Monitor;
import java.sql.Connection;

/**
 *
 * @author joaor
 */
public class DataBaseInfo implements Runnable {
    private Connection c;
    private Monitor m;
    
    public DataBaseInfo(Connection c, Monitor m) {
        this.c = c;
        this.m = m;
    }
    
    
    private void save_info() {
        try{
            /*String info = "";
            PreparedStatement ps = this.c.prepareStatement(info);            CHANGEMEEEEEE
            */
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados!");
        }
    }
    
    
    @Override
    public void run() {
        try{
            save_info();
        }
        catch(Exception e) {
            System.out.println("Error saving records!");
            e.printStackTrace();
        }
    }
}
