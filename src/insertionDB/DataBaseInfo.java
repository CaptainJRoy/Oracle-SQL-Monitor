/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertionDB;

import database.Memory;
import dbmonitor.Monitor;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    
    
    private void save_info_memory(Memory m) {
        try{
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy/MM/dd hh:mm:ss");
            String dataStr = ft.format(date);
            m.write(dataStr);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("Erro na escrita de dados!");
        }
    }
    
    
    @Override
    public void run() {
        save_info_memory(this.m.mem);
    }
}
