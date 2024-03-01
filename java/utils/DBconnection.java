package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static final  String URL = "jdbc:mysql://localhost:3306/to7fa";
    private static final  String USER = "root";
    private static final  String PASSWORD = "";

    private static DBconnection instance;

    private Connection cnx;

    public Connection getcnx()
    {
        return cnx ;
    }

    private DBconnection () {

        try {
            this.cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.err.println(" connect to databse");
        } catch (SQLException e) {
            System.err.println("error: "+e.getMessage());
        }


    }
    public static DBconnection getInstance()
    {  if (instance==null) instance = new DBconnection() ;
        return instance; }
}
