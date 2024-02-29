package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pidev";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    //second step: creer une instance static de meme type que la place
    private static DBConnection instance;
    private Connection cnx;
    //first step : rendre le constructeur privé
    private DBConnection(){
        try{
        this.cnx = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("connected to database");
    }catch(SQLException e){
            System.err.println("error:"+e.getMessage());
    }
}//third step : creer une methode static pour recuperer l'instance
    public static DBConnection getInstance(){
        if(instance == null) instance = new DBConnection();
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
