package com.example.bd_laba5_and6;
import java.net.URL;
import java.sql.*;


public class DatabaseHandler extends Const  {

    Connection dbConnection;
    ResultSet resSet;
    public Connection getDbConnection() throws ClassNotFoundException,SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        dbConnection=DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        return dbConnection;
    }
    public ResultSet selectPatient() throws  SQLException,ClassNotFoundException
    {
        String select= "select * from "+PATIENT;
        PreparedStatement preparedStatement=getDbConnection().prepareStatement(select);
        resSet=preparedStatement.executeQuery();
        return resSet;

    }

}
