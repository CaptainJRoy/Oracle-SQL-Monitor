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
        sb.append(",");
        sb.append(getSessions());
        sb.append(",");
        sb.append(getGrants());
        sb.append(",");
        sb.append(getDatafiles());
        sb.append(",");
        sb.append(getUsers());
        sb.append(",");
        sb.append(getTablespaces());
        sb.append(",");
        sb.append(getTables());
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
            sb.append("{\"memory\":[");
            while(rs.next()) {
                sb.append("{\"timestamp\":\"");
                sb.append(rs.getDate(1));
                sb.append("\",\"sga\":");
                sb.append(rs.getFloat(2));
                sb.append(",\"pga\":");
                sb.append(rs.getFloat(3));
                sb.append("},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error memory";
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
            sb.append("{\"cpu\":[");
            while(rs.next()) {
                sb.append("{\"timestamp\":\"");
                sb.append(rs.getDate(1));
                sb.append("\",\"num_cpu_cores\":");
                sb.append(rs.getFloat(2));
                sb.append(",\"iowait_time\":");
                sb.append(rs.getFloat(3));
                sb.append(",\"nice_time\":");
                sb.append(rs.getFloat(4));
                sb.append(",\"busy_time\":");
                sb.append(rs.getFloat(5));
                sb.append(",\"user_time\":");
                sb.append(rs.getFloat(6));
                sb.append(",\"num_cpus\":");
                sb.append(rs.getFloat(7));
                sb.append(",\"iddle_time\":");
                sb.append(rs.getFloat(8));
                sb.append("},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error cpu";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sessions")
    public String getSessions() {
        try {
            String run =  "select * from sessions";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"sessions\":[");
            while(rs.next()) {
                sb.append("{\"sid\":");
                sb.append(rs.getFloat(1));
                sb.append(",\"timestamp\":\"");
                sb.append(rs.getDate(2));
                sb.append("\",\"box\":\"");
                sb.append(rs.getString(3));
                sb.append("\",\"os_user\":\"");
                sb.append(rs.getString(4));
                sb.append("\",\"program\":\"");
                sb.append(rs.getString(5));
                sb.append("\"},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error sessions";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("grants")
    public String getGrants() {
        try {
            String run =  "select * from grants";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"grants\":[");

            while(rs.next()) {
                sb.append("{\"grantee\":\"");
                sb.append(rs.getString(1));
                sb.append("\",\"privilege\":");
                sb.append(rs.getString(2));
                sb.append("\",\"admin_option\":");
                sb.append(rs.getFloat(3) == 1);
                sb.append(",\"common\":");
                sb.append(rs.getFloat(4) == 1);
                sb.append(",\"inherited\":");
                sb.append(rs.getFloat(5) == 1);
                sb.append(",\"timestamp\":\"");
                sb.append(rs.getDate(6));
                sb.append("\"},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString() + '\n');
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error grants";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("datafiles")
    public String getDatafiles() {
        try {
            String run =  "select * from datafiles";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"datafiles\":[");

            while(rs.next()) {
                sb.append("{\"filename\":\"");
                sb.append(rs.getString(1));
                sb.append("\",\"size_file\":");
                sb.append(rs.getFloat(2));
                sb.append(",\"used\":");
                sb.append(rs.getFloat(3));
                sb.append(",\"pct_used\":");
                sb.append(rs.getFloat(4));
                sb.append("},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString() + '\n');
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error datafiles";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("users")
    public String getUsers() {
        try {
            String run =  "select * from users";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"users\":[");

            while(rs.next()) {
                sb.append("{\"username\":\"");
                sb.append(rs.getString(1));
                sb.append("\",\"default_tablespace\":\"");
                sb.append(rs.getString(2));
                sb.append("\",\"temporary_tablespace\":\"");
                sb.append(rs.getString(3));
                sb.append("\",\"last_login\":\"");
                sb.append(rs.getDate(4));
                sb.append("\",\"session_username\":\"");
                sb.append(rs.getString(5));
                sb.append("\"},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString() + '\n');
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error users";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tablespaces")
    public String getTablespaces() {
        try {
            String run =  "select * from tablespaces";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"tablespaces\":[");
            while(rs.next()) {
                sb.append("{\"tablespace\":\"");
                sb.append(rs.getString(1));
                sb.append("\",\"pct_used\":");
                sb.append(rs.getFloat(2));
                sb.append(",\"total_mb\":");
                sb.append(rs.getFloat(3));
                sb.append(",\"used_mb\":");
                sb.append(rs.getFloat(4));
                sb.append(",\"free_mb\":");
                sb.append(rs.getFloat(5));
                sb.append(",\"datafiles\":");
                sb.append(rs.getFloat(6));
                sb.append(",\"username\":\"");
                sb.append(rs.getString(7));
                sb.append("\",\"datafile_name\":\"");
                sb.append(rs.getString(8));
                sb.append("\"},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error tablespaces";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("tables")
    public String getTables() {
        try {
            String run =  "select * from tables";
            PreparedStatement ps = this.c.prepareStatement(run);
            ResultSet rs = ps.executeQuery();
                        
            StringBuilder sb = new StringBuilder();
            sb.append("{\"tables\":[");
            while(rs.next()) {
                sb.append("{\"timestamp\":\"");
                sb.append(rs.getDate(1));
                sb.append("\",\"schema\":\"");
                sb.append(rs.getString(2));
                sb.append("\",\"object\":\"");
                sb.append(rs.getString(3));
                sb.append("\",\"object_size\":");
                sb.append(rs.getFloat(4));
                sb.append(",\"tablespace\":\"");
                sb.append(rs.getString(5));
                sb.append("\"},");
            }
            sb.setCharAt(sb.length()-1, ']');
            sb.append("}");
            
            System.out.println(sb.toString());
            return sb.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error tables";
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
