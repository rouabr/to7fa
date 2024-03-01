package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.commande;
import models.oeuvre;
import utils.DBconnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class serviceCommande implements CRUD<commande,oeuvre> {
    private static Connection cnx;

    public serviceCommande() {
        cnx = DBconnection.getInstance().getcnx();


    }

    @Override
    public void insertOne(commande com) throws SQLException {
           /* String req = "INSERT INTO commande(type_commande, name_commande,date_commande,payment) VALUES ('" + com.gettype_commande() + "', '" + com.getname_commande() + "', '" + com.getdate_commande() +"', "+ com.getpayment() + ")";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);*/
        String req = "INSERT INTO commande(prix_commande, name_commande, date_commande, payment,id_oeuvre) VALUES (?, ?, NOW(), ?,?)";

        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setFloat(1, com.getprix_commande());
            pstmt.setString(2, com.getname_commande());
            pstmt.setString(3, com.getpayment());
            pstmt.setInt(4, com.getId_oeuvre());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int personId) throws SQLException {
        String req = "DELETE FROM commande WHERE id_commande  = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setInt(1, personId);
            pst.executeUpdate();
        }
    }



    @Override
    public void updateOne(commande com)  {
        String req = "UPDATE commande SET prix_commande= ?, name_commande = ?, date_commande = NOW(), payment = ?,id_oeuvre =? WHERE id_commande = ?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setFloat(1, com.getprix_commande());
            pst.setString(2, com.getname_commande());
            pst.setString(3,  com.getpayment());
            pst.setInt(4, com.getId_oeuvre());
            pst.setInt(5, com.getID());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    /*   public void insertOne1(person person) throws SQLException {
        String req = "INSERT INTO 'person'(' nom' , 'prenom', 'age') VALUES "
                + "(?,?,?)";
        PreparedStatement ps = cnx.prepareStatement((req));
        ps.setString();
        ps.executeUpdate(req);
    }*/
    @Override
    public List<commande> selectAll() throws SQLException {
        List<commande> personList =new ArrayList<>();
        String req = "SELECT * FROM commande";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            commande p = new commande();
            p.setID(rs.getInt("id_commande"));
            p.setprix_commande(rs.getFloat("prix_commande"));
            p.setname_commande(rs.getString("name_commande"));
            p.setdate_commande(rs.getDate("date_commande"));
            p.setpayment(rs.getString("payment"));
            personList.add(p);
            // System.out.println(p.getNom());
        }

        return personList;
    }


    @Override
    public  ObservableList<commande> selectAllTableview() throws SQLException, ParseException {
        ObservableList<commande> commandes = FXCollections.observableArrayList();
        String query = "SELECT * FROM commande" ;

        try {

            Statement st = cnx.createStatement();
            ResultSet rs =st.executeQuery(query);

            while (rs.next()) {


                commande p = new commande();
                p.setID(rs.getInt("id_commande"));
                p.setprix_commande(rs.getFloat("prix_commande"));
                p.setname_commande(rs.getString("name_commande"));
                p.setdate_commande(rs.getDate("date_commande"));
                p.setpayment(rs.getString("payment"));
                p.setId_oeuvre(rs.getInt("id_oeuvre"));

                commandes.add(p);
                System.out.println("vov");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des commande.", e);
        }
        System.out.println("lol");
        return commandes;
    }

    @Override
    public List<oeuvre> selectoeuvre() throws SQLException {
        List<oeuvre> oeuvs =new ArrayList<>();
        String req = "SELECT * FROM oeuvre,panier where oeuvre.id_oeuvre=panier.id_oeuvre ";
        Statement st = cnx.createStatement();
        ResultSet rs =st.executeQuery(req);
        while (rs.next()) {
            oeuvre p = new oeuvre();
            p.setId_oeuvre(rs.getInt("id_oeuvre"));

            p.setName(rs.getString("name_oeuvre"));
            p.setPrice(rs.getFloat("prix_oeuvre"));

            p.setImgSrc(rs.getString("img_oeuvre"));

            oeuvs.add(p);
            // System.out.println(p.getNom());
        }

        return oeuvs;
    }

}
