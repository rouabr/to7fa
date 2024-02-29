package test;

import models.Musee;
import services.ServiceMusee;
import utils.DBConnection;

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




    }

}
