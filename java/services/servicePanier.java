package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.commande;
import models.oeuvre;
import models.panierat;
import utils.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class servicePanier implements CRUD_panier<panierat,oeuvre>{
    private Connection cnx;

    public servicePanier() {
        cnx = DBconnection.getInstance().getcnx();


    }

    @Override
    public int searchByIdOeuvre(int id_oeuvre) throws SQLException {
        String req = "SELECT COUNT(*) FROM panier WHERE id_oeuvre = ?";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, id_oeuvre);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0 ? 1 : 0;
            } else {
                return 0;
            }
        }
    }

    @Override
    public void insertOne(panierat pan) throws SQLException {

        String req = "INSERT INTO panier(id_user,id_oeuvre) VALUES (?,?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
                System.out.println(pan.getId_user());
            pstmt.setInt(1, pan.getId_user());
            pstmt.setInt(2, pan.getId_oeuvre());

            pstmt.executeUpdate();
        }
    }
    @Override
    public List<panierat> selectAll() throws SQLException {
        List<panierat> panie =new ArrayList<>();
        String req = "SELECT * FROM panier";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            panierat p = new panierat();
            p.setId_panier(rs.getInt("id_panier"));
            p.setId_user(rs.getInt("id_user"));
            p.setId_oeuvre(rs.getInt("id_oeuvre"));
            panie.add(p);
            // System.out.println(p.getNom());
        }

        return panie;
    }

    @Override
    public List<oeuvre> selectAlloeuvre() throws SQLException {
        List<oeuvre> oeuv =new ArrayList<>();
        String req = "SELECT * FROM oeuvre,panier where oeuvre.id_oeuvre=panier.id_oeuvre ";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            oeuvre p = new oeuvre();
            p.setId_oeuvre(rs.getInt("id_oeuvre"));
            System.out.println("id ==== "+p.getId_oeuvre());
            p.setName(rs.getString("name_oeuvre"));
            p.setPrice(rs.getFloat("prix_oeuvre"));
            p.setImgSrc(rs.getString("img_oeuvre"));

            oeuv.add(p);

            // System.out.println(p.getNom());
        }

        return oeuv;
    }
    @Override
    public ObservableList<panierat> selectAlloeuvreTableview() throws SQLException {
        ObservableList<panierat> paniers = FXCollections.observableArrayList();
        String req = "SELECT * FROM oeuvre,panier where oeuvre.id_oeuvre=panier.id_oeuvre ";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            panierat p = new panierat();
            p.setId_panier(rs.getInt("id_panier"));
            p.setId_user(rs.getInt("id_user"));
            p.setId_oeuvre(rs.getInt("id_oeuvre"));

            paniers.add(p);

            // System.out.println(p.getNom());
        }

        return paniers;
    }


    @Override
    public void deleteOne(int id_panier,int id_oeuvre) throws SQLException {
        String req = "DELETE FROM panier WHERE id_oeuvre = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
        //    pst.setInt(1, id_panier);
            pst.setInt(1, id_oeuvre);
            pst.executeUpdate();
        }
    }

    @Override
    public void deleteTwo(int id_panier) throws SQLException {
        String req = "DELETE FROM panier WHERE id_panier = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            //    pst.setInt(1, id_panier);
            pst.setInt(1, id_panier);
            pst.executeUpdate();
        }
    }
    @Override
    public List<oeuvre> selectByname(String ch) throws SQLException {
        List<oeuvre> oeuv = new ArrayList<>();
        String req = "SELECT * FROM oeuvre,panier WHERE oeuvre.id_oeuvre=panier.id_oeuvre AND name_oeuvre LIKE ?";
        PreparedStatement statement = cnx.prepareStatement(req);
        statement.setString(1,  ch + "%");
        ResultSet rs = statement.executeQuery(); // Use the prepared statement here
        while (rs.next()) {
            oeuvre p = new oeuvre();
            p.setId_oeuvre(rs.getInt("id_oeuvre"));
            System.out.println("id ==== " + p.getId_oeuvre());
            p.setName(rs.getString("name_oeuvre"));
            p.setPrice(rs.getFloat("prix_oeuvre"));
            p.setImgSrc(rs.getString("img_oeuvre"));
            oeuv.add(p);
        }
        return oeuv;
        }
    @Override
    public void Update(panierat pan) throws SQLException{

        String req = "UPDATE panier SET id_panier= ?, id_user= ?,id_oeuvre= ? ";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1,pan.getId_panier());
            pstmt.setInt(2, pan.getId_user());
            pstmt.setInt(3, pan.getId_oeuvre());

            pstmt.executeUpdate();
        }

    }

}

