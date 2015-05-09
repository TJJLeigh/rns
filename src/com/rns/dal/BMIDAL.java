package com.rns.dal;

import com.rns.util.ApplicationProperty;

import java.sql.*;

public class BMIDAL {
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
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            databaseConnection = DriverManager.getConnection(connectionHost+databaseName, userName, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stopConnection(){

        try {
            if(!databaseConnection.isClosed()){
                databaseConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet readBMIRecords(){
        ResultSet r = null;
            try {
                Statement st = databaseConnection.createStatement();
                r = st.executeQuery("SELECT * FROM bmi");
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        return r;
    }
}
