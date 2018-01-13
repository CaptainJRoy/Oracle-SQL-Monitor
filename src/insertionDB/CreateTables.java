/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insertionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author joaor
 */
public class CreateTables implements Runnable {
    private Connection c;
    
    public CreateTables(Connection c) {
        this.c = c;
    }
    
    private void clearTabela(String name) {
        try {
            String getTS =  "DELETE (SELECT * FROM " + name + ")";
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private void dropTabela(String name) {
        try {
            String getTS =  "DROP TABLE " + name;
            PreparedStatement ps = this.c.prepareStatement(getTS);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Impossivel remover tabela " + name + "!");
        }
    }

    private void criaTabela(String statement) {
        try {
            PreparedStatement ps = this.c.prepareStatement(statement);
            ResultSet rs = ps.executeQuery();
        }
        catch(Exception e) {
            System.out.println("Impossivel criar tabela!");
        }
    }
    
    @Override
    public void run() {
        clearTabela("MEMORY");
        dropTabela("MEMORY");
        clearTabela("CPU");
        dropTabela("CPU");
        clearTabela("TABLES");
        dropTabela("TABLES");
        clearTabela("TABLESPACES");
        dropTabela("TABLESPACES");
        clearTabela("DATAFILES");
        dropTabela("DATAFILES");
        clearTabela("USERS");
        dropTabela("USERS");
        clearTabela("GRANTS");
        dropTabela("GRANTS");
        clearTabela("SESSIONS");
        dropTabela("SESSIONS");
        criaTabela("CREATE TABLE MEMORY \n" +
                    "(\n" +
                    "  TIMESTAMP DATE NOT NULL \n" +
                    ", SGA NUMBER NOT NULL \n" +
                    ", PGA NUMBER NOT NULL \n" +
                    ", CONSTRAINT MEMORY_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    TIMESTAMP \n" +
                    "  )\n" +
                    ")");
        criaTabela("CREATE TABLE CPU \n" +
                    "(\n" +
                    "  TIMESTAMP DATE NOT NULL \n" +
                    ", NUM_CPU_CORES NUMBER NOT NULL \n" +
                    ", IOWAIT_TIME NUMBER NOT NULL \n" +
                    ", NICE_TIME NUMBER NOT NULL \n" +
                    ", BUSY_TIME NUMBER NOT NULL \n" +
                    ", USER_TIME NUMBER NOT NULL \n" +
                    ", NUM_CPUS NUMBER NOT NULL \n" +
                    ", IDDLE_TIME NUMBER NOT NULL \n" +
                    ", CONSTRAINT CPU_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    TIMESTAMP \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("CREATE TABLE DATAFILES \n" +
                    "(\n" +
                    "  FILENAME VARCHAR2(256) NOT NULL \n" +
                    ", SIZE_FILE NUMBER NOT NULL \n" +
                    ", USED NUMBER NOT NULL \n" +
                    ", PCT_USED NUMBER NOT NULL \n" +
                    ", CONSTRAINT DATAFILES_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    FILENAME \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("CREATE TABLE SESSIONS \n" +
                    "(\n" +
                    "  SID NUMBER NOT NULL \n" +
                    ", TIMESTAMP DATE NOT NULL \n" +
                    ", BOX VARCHAR2(256) NOT NULL \n" +
                    ", OS_USER VARCHAR2(256) NOT NULL \n" +
                    ", PROGRAM VARCHAR2(256) NOT NULL \n" +
                    ", CONSTRAINT SESSIONS_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    SID\n" +
                    "  , OS_USER\n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("CREATE TABLE GRANTS \n" +
                    "(\n" +
                    "  GRANTEE VARCHAR2(256) NOT NULL \n" +
                    ", PRIVILEGE VARCHAR2(256) NOT NULL \n" +
                    ", ADMIN_OPTION INT NOT NULL \n" +
                    ", COMMON INT NOT NULL \n" +
                    ", INHERITED INT NOT NULL \n" +
                    ", COLUMN1 DATE NOT NULL \n" +
                    ", CONSTRAINT GRANTS_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    GRANTEE \n" +
                    "  , PRIVILEGE \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("CREATE TABLE USERS \n" +
                    "(\n" +
                    "  USERNAME VARCHAR2(256) NOT NULL \n" +
                    ", DEFAULT_TABLESPACE VARCHAR2(256) NOT NULL \n" +
                    ", TEMPORARY_TABLESPACE VARCHAR2(256) NOT NULL \n" +
                    ", LAST_LOGIN DATE \n" +
                    ", SESSION_USERNAME VARCHAR2(256) NOT NULL \n" +
                    ", CONSTRAINT USERS_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    USERNAME \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("CREATE TABLE TABLESPACES \n" +
                    "(\n" +
                    "  TABLESPACE VARCHAR2(256) NOT NULL \n" +
                    ", PCT_USED NUMBER NOT NULL \n" +
                    ", TOTAL_MB NUMBER NOT NULL \n" +
                    ", USED_MB NUMBER NOT NULL \n" +
                    ", FREE_MB NUMBER NOT NULL \n" +
                    ", DATAFILES NUMBER NOT NULL \n" +
                    ", USERNAME VARCHAR(256) NOT NULL\n" +
                    ", DATAFILE_NAME VARCHAR2(256) \n" +
                    ", CONSTRAINT TABLESPACES_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    TABLESPACE \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("ALTER TABLE TABLESPACES\n" +
                    "ADD CONSTRAINT TABLESPACES_FK1 FOREIGN KEY\n" +
                    "(\n" +
                    "  DATAFILE_NAME \n" +
                    ")\n" +
                    "REFERENCES DATAFILES\n" +
                    "(\n" +
                    "  FILENAME \n" +
                    ")\n" +
                    "ENABLE");
        criaTabela("ALTER TABLE TABLESPACES\n" +
                    "ADD CONSTRAINT TABLESPACES_FK2 FOREIGN KEY\n" +
                    "(\n" +
                    "    USERNAME\n" +
                    ")\n" +
                    "REFERENCES USERS\n" +
                    "(\n" +
                    "    USERNAME\n" +
                    ")\n" +
                    "ENABLE");
        criaTabela("CREATE TABLE TABLES \n" +
                    "(\n" +
                    "  TIMESTAMP DATE NOT NULL \n" +
                    ", SCHEMA VARCHAR2(256) NOT NULL \n" +
                    ", OBJECT VARCHAR2(256) NOT NULL \n" +
                    ", OBJECT_SIZE NUMBER NOT NULL \n" +
                    ", TABLESPACE VARCHAR2(256) \n" +
                    ", CONSTRAINT TABLES_PK PRIMARY KEY \n" +
                    "  (\n" +
                    "    TIMESTAMP \n" +
                    "  , SCHEMA \n" +
                    "  , OBJECT \n" +
                    "  )\n" +
                    "  ENABLE \n" +
                    ")");
        criaTabela("ALTER TABLE TABLES\n" +
                    "ADD CONSTRAINT TABLES_FK1 FOREIGN KEY\n" +
                    "(\n" +
                    "  TABLESPACE\n" +
                    ")\n" +
                    "REFERENCES TABLESPACES\n" +
                    "(\n" +
                    "  TABLESPACE\n" +
                    ")\n" +
                    "ENABLE");
        System.out.println("Tabelas inicializadas com sucesso!");
        try {
            this.c.close();
        } catch (Exception e) {
            System.out.println("Erro a fechar ligação!");
        }
    }
}