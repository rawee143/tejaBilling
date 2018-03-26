/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import Pojo.*;

/**
 *
 * @author ranjan
 */
public class DataBase_Connection {

    protected static Connection connectionInstance;

    protected String dbUserName ="root";
    protected String dbPassWord = "";
    protected String dbUrl ="jdbc:mysql://localhost:3307/draviTejaComm";
    protected String dbDriver ="com.mysql.jdbc.Driver";
    protected static String configFileName;
    protected Statement smtInstance;
    protected Connection conInstance;

    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    public String getDbPassWord() {
        return dbPassWord;
    }

    public void setDbPassWord(String dbPassWord) {
        this.dbPassWord = dbPassWord;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public static String getConfigFileName() {
        return configFileName;
    }

    public static void setConfigFileName(String configFileName) {
        DataBase_Connection.configFileName = configFileName;
    }

    public static Connection getConnectionInstance() {
        if (connectionInstance == null) {
            connectionInstance = new DataBase_Connection().getConnection();
        }
        return connectionInstance;
    }

    public DataBase_Connection() {

    }



    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionInstance = DriverManager.getConnection("jdbc:mysql://localhost:3307/draviTejaComm", "root", "");
            return connectionInstance;
            
        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {

        }
        return connectionInstance;
    }

    public static Connection getManualConnection(String DB_Driver, String DB_url,
            String DB_userName, String DB_Password) throws ClassNotFoundException, SQLException {
        if (connectionInstance == null) {

            Class.forName(DB_Driver);
            connectionInstance = DriverManager.getConnection(DB_url, DB_userName, DB_Password);

            //System.out.println("connection is initiated");

            return connectionInstance;

        } else {
            //System.out.println("DATABASE is already connected");

        }
        return connectionInstance;
    }

    public static void closeConnection() throws SQLException {
        if (connectionInstance != null) {
            connectionInstance.close();
            //System.out.println("DataBase Connection is closed");
        }

    }

    public static void closeConnectionManually(Connection ConnectionObject) throws SQLException {
        if (ConnectionObject != null) {
            ConnectionObject.close();
            //System.out.println("DataBase Connection is closed");
        }

    }
    

    public static void main(String argc[]) {
        DataBase_Connection db = new DataBase_Connection();
        db.getConnection();

        System.out.println(db.toString());

    }
}
