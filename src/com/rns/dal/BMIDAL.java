package com.rns.dal;

import com.rns.util.ApplicationProperty;

import java.sql.*;
import org.apache.log4j.Logger;
import com.rns.util.PasswordHasher;

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
    public Connection getDatabaseConnection() {
        return databaseConnection;
    }

    public boolean login(String username, String password){
        try {
            PreparedStatement compareUsername = databaseConnection.prepareStatement("SELECT * FROM users WHERE username = ?");
            compareUsername.setString(1, username);
            ResultSet saltfinder = compareUsername.executeQuery();
            if(saltfinder.next()) {
                String salt = saltfinder.getString("salt");
                if (saltfinder.getString("password").equals(PasswordHasher.md5Hex(salt + password))) {
                    logger.info("Login Successful");
                    return true;
                }
            }
            logger.info("Login Failed");
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("SQL Failed");
            return false;
        }

    }
    public void addUser(String username, String pwd){
        if (isUnique(username,"users")){
            try {
                logger.info("Username is unique, adding user to database");
                String salt = PasswordHasher.generateSalt();
                PreparedStatement newUser = databaseConnection.prepareStatement("INSERT INTO users (username, password, salt) VALUES(?,?,?)");
                newUser.setString(1, username);
                newUser.setString(2,PasswordHasher.md5Hex(salt+pwd) );
                newUser.setString(3, salt);
                newUser.executeUpdate();
                logger.info("User added successfully to database");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            logger.warn("Username is not unique!");
        }
    }
    public boolean isUnique(String entry, String table){
        Statement st;
        try {
            st = databaseConnection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT username FROM users");
            while (resultSet.next()){
                if (resultSet.getString("username").equals(entry))
                    return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.warn("Query to check if entry is unique has failed");
        return false;
    }
}
