package test;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;

import models.Categorie;
import models.Oeuvre;
import services.ServiceCategorie;
import services.ServiceOeuvre;
import utils.DBConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args){




        DBConnection cn1 =  DBConnection.getInstance();
        DBConnection cn2 =  DBConnection.getInstance();
        DBConnection cn3 =  DBConnection.getInstance();
        DBConnection cn4 =  DBConnection.getInstance();
        System.out.println(cn1.hashCode());
        System.out.println(cn2.hashCode());
        System.out.println(cn3.hashCode());
        //Oeuvre oeuvre = new Oeuvre("peinture", "davenchi ",500,"15-02-2024","disponible",987,"lien");
        Categorie categorie = new Categorie(2,"katoussa");
        ServiceCategorie sc = new ServiceCategorie();
        ServiceOeuvre service = new ServiceOeuvre();
        try {
            sc.insertOne(categorie);
            // Call the insertOne method to insert the person into the database
            //service.insertOne(oeuvre);
            System.out.println("oeuvre inserted successfully.");
            service.applyPromotions();
            System.out.println("Promotions applied successfully.");
        } catch (SQLException e) {
            // Handle any exceptions that occur during the insertion process
            e.printStackTrace();

            System.err.println("Failed to insert oeuvre or apply promotions.");
        }

    }
}