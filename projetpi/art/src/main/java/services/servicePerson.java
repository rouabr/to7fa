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
    public List<livraison> selectByname(String ch) throws SQLException {
        List<livraison> personList =new ArrayList<>();

        List<livraison> oeuv = new ArrayList<>();
        String req = "SELECT *  FROM livraison,commande where livraison.id_commande=commande.id_commande AND adresse_livraison LIKE ?";
        PreparedStatement statement = cnx.prepareStatement(req);

        statement.setString(1,  ch + "%");
        ResultSet rs = statement.executeQuery(); // Use the prepared statement here
        while (rs.next()) {
            Float a=rs.getFloat("frais_livraison")+rs.getFloat("prix_commande");
            livraison p = new livraison();
            p.setID(rs.getInt("id_livraison"));
            p.setDate(rs.getString("date_livraison"));
            p.setAdresse(rs.getString("adresse_livraison"));
            p.setStatus(rs.getString("status_livraison"));
            p.setFrais(a);
            p.setId_livreur(rs.getInt("id_livreur"));
            p.setId_commande(rs.getInt("id_commande"));
            personList.add(p);
        }
        return personList;
    }
    @Override
    public void insertOne(livraison person) throws SQLException {
        String req = "INSERT INTO livraison(date_livraison, adresse_livraison, status_livraison, frais_livraison, id_livreur, id_commande)\n" +
                "SELECT ?, ?, ?, ?, l.id_livreur, c.id_commande\n" +
                "FROM livreur l, commande c\n" +
                "WHERE l.nom_livreur = ? AND c.name_commande = ?;\n";
        try (PreparedStatement st = cnx.prepareStatement(req)) {
            st.setString(1, person.getDate());
            st.setString(2, person.getAdresse());
            st.setString(3, person.getStatus());
            st.setFloat(4, person.getFrais());
            st.setString(5, person.getNom_livreur());
            st.setString(6, person.getNom_coomande());

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
        String req = "UPDATE livraison\n" +
                "SET date_livraison = ?, adresse_livraison = ?, status_livraison = ?, frais_livraison = ?, id_livreur = (SELECT id_livreur FROM livreur WHERE nom_livreur = ?), id_commande = (SELECT id_commande FROM commande WHERE name_commande = ?)\n" +
                "WHERE id_livraison = ?;\n";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, liv.getDate());
            pst.setString(2, liv.getAdresse());
            pst.setString(3, liv.getStatus());
            pst.setFloat(4, liv.getFrais());
            pst.setString(5, liv.getNom_livreur());
            pst.setString(6, liv.getNom_coomande());
            pst.setInt(7, liv.getID());
            System.out.println(liv.getID());
            pst.executeUpdate();
        }
    }






    @Override
    public List<livraison> selectAll() throws SQLException {
        List<livraison> personList =new ArrayList<>();
        String req = "SELECT * FROM livraison,commande where livraison.id_commande=commande.id_commande";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            Float a=rs.getFloat("frais_livraison")+rs.getFloat("prix_commande");
            livraison p = new livraison();
            p.setID(rs.getInt("id_livraison"));
            p.setDate(rs.getString("date_livraison"));
            p.setAdresse(rs.getString("adresse_livraison"));
            p.setStatus(rs.getString("status_livraison"));
            p.setFrais(a);
            p.setId_livreur(rs.getInt("id_livreur"));
            p.setId_commande(rs.getInt("id_commande"));
            personList.add(p);
            // System.out.println(p.getNom());
        }

        return personList;
    }

    @Override
    public List<livraison> selectAlltri() throws SQLException {
        List<livraison> personList =new ArrayList<>();
        String req = "SELECT * FROM livraison,commande where livraison.id_commande=commande.id_commande ORDER BY adresse_livraison";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            Float a=rs.getFloat("frais_livraison")+rs.getFloat("prix_commande");
            livraison p = new livraison();
            p.setID(rs.getInt("id_livraison"));
            p.setDate(rs.getString("date_livraison"));
            p.setAdresse(rs.getString("adresse_livraison"));
            p.setStatus(rs.getString("status_livraison"));
            p.setFrais(a);
            p.setId_livreur(rs.getInt("id_livreur"));
            p.setId_commande(rs.getInt("id_commande"));
            personList.add(p);
            // System.out.println(p.getNom());
        }

        return personList;
    }

}
