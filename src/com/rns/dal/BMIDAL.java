package com.rns.dal;

import com.rns.util.ApplicationProperty;

import java.sql.*;
import org.apache.log4j.Logger;

public class BMIDAL {
    private static Logger logger = Logger.getLogger(BMIDAL.class);
    Connection databaseConnection;
    String connectionHost = ApplicationProperty.getInstance().GetPropertyValue("database.host");
    String databaseName = ApplicationProperty.getInstance().GetPropertyValue("database.schema");
    String userName = ApplicationProperty.getInstance().GetPropertyValue("client.username");
    private static BMIDAL instance;

    protected BMIDAL(){
        startConnection();
    }
    public static BMIDAL getInstance(){
        if(instance == null){
            instance = new BMIDAL();
        }
        return instance;
    }

    private void startConnection(){
        try {
            logger.info("Establishing connection as user: " + userName);
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            databaseConnection = DriverManager.getConnection(connectionHost+databaseName, userName, "");
            logger.info("Connection Successful");
        } catch (Exception e) {
            logger.warn("Connection failed");
            e.printStackTrace();
        }
    }
    public void stopConnection(){

        try {
            if(!databaseConnection.isClosed()){
                logger.info("Closing connection");
                databaseConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet readBMIRecords(){
        ResultSet r = null;
            try {
                logger.info("Reading BMI records");
                Statement st = databaseConnection.createStatement();
                r = st.executeQuery("SELECT * FROM bmi");
            }
            catch (SQLException e){
                logger.warn("BMI record reading failed");
                e.printStackTrace();
            }
        return r;
    }
}
