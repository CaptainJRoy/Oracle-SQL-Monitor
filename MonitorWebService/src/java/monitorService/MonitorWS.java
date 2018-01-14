/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorService;

import database.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author joaor
 */
@Path("access")
public class MonitorWS {

    @Context
    private UriInfo context;
    private Connection c;

    /**
     * Creates a new instance of MonitorWS
     */
    public MonitorWS() {
        try {
            this.c = new Connect().connect();
        } catch (SQLException ex) {
            Logger.getLogger(MonitorWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves representation of an instance of monitorService.MonitorWS
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append(getMemory());
        sb.append(",");
        sb.append(getCPU());
        sb.append("}");
        return sb.toString();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("memory")
    public String getMemory() {
        try {
            String run =  "select * from memory";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
            
            StringBuilder sb = new StringBuilder();
            sb.append("{memory:[");
            while(rs.next()) {
                sb.append("{timestamp:");
                sb.append(rs.getDate(1));
                sb.append(",sga:");
                sb.append(rs.getFloat(2));
                sb.append(",pga:");
                sb.append(rs.getFloat(3));
                sb.append("},");
            }
            sb.append("null]}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("cpu")
    public String getCPU() {
        try {
            String run =  "select * from cpu";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{cpu:[");
            while(rs.next()) {
                sb.append("{timestamp:");
                sb.append(rs.getDate(1));
                sb.append(",num_cpu_cores:");
                sb.append(rs.getFloat(2));
                sb.append(",iowait_time:");
                sb.append(rs.getFloat(3));
                sb.append(",nice_time:");
                sb.append(rs.getFloat(4));
                sb.append(",busy_time:");
                sb.append(rs.getFloat(5));
                sb.append(",user_time:");
                sb.append(rs.getFloat(6));
                sb.append(",num_cpus:");
                sb.append(rs.getFloat(7));
                sb.append(",idle_time:");
                sb.append(rs.getFloat(8));
                sb.append("},");
            }
            sb.append("null]}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    /**
     * PUT method for updating or creating an instance of MonitorWS
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
