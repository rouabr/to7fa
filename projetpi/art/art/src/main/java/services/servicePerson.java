package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.livraison;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicePerson implements CRUD<livraison>{
    private Connection cnx;
    public servicePerson(){
        cnx= DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(livraison person) throws SQLException {
        String req = "INSERT INTO livraison(date_livraison, adresse_livraison, status_livraison, frais_livraison, name_transporteur, nom_commande) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, person.getDate());
            st.setString(2, person.getAdresse());
            st.setString(3, person.getStatus());
            st.setFloat(4, person.getFrais());
            st.setString(5, person.getName());
            st.setString(6, person.getNom_commande());

            st.executeUpdate();
        }
    }


    @Override
    public void deleteOne(int personId) throws SQLException {
        String req = "DELETE FROM livraison WHERE id_livraison = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, personId);
            pst.executeUpdate();
        }
    }


    @Override
    public void updateOne(livraison liv) throws SQLException {
        String req = "UPDATE livraison SET date_livraison = ?, adresse_livraison = ?, status_livraison = ?, frais_livraison = ?, name_transporteur = ?,  WHERE id_livraison = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, liv.getDate());
            pst.setString(2, liv.getAdresse());
            pst.setString(3, liv.getStatus());
            pst.setFloat(4, liv.getFrais());
            pst.setString(5, liv.getNom_commande());
            pst.setString(6, liv.getName());
            pst.setInt(7, liv.getID());
            System.out.println(liv.getID());
            pst.executeUpdate();
        }
    }






    @Override
    public List<livraison> selectAll() throws SQLException {
        List<livraison> personList =new ArrayList<>();
        String req = "SELECT * FROM livraison";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            livraison p = new livraison();
            p.setID(rs.getInt("id_livraison"));
            p.setDate(rs.getString("date_livraison"));
            p.setAdresse(rs.getString("adresse_livraison"));
            p.setStatus(rs.getString("status_livraison"));
            p.setFrais(rs.getFloat("frais_livraison"));
            p.setName(rs.getString("name_transporteur"));
            personList.add(p);
            // System.out.println(p.getNom());
        }

        return personList;
    }

}
