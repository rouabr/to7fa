package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//first step:rendre le constructeur prv
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pidev";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    //second step: crer une instance static de meme type que la classe
    private static DBConnection instance;
    private Connection cnx;
    private DBConnection(){
        try {
            this.cnx = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected To DATABASE !");
        } catch (SQLException e) {
           System.err.println("Error: "+e.getMessage());
        }
    }
    //thrid step: crer une methode static pour recuperer l'instance
    public static DBConnection getInstance(){
        if (instance == null) instance = new DBConnection();
        return instance;
    }

    public Connection getCnx() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
