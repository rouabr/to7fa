package services;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Categorie;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategorie implements CRUD<Categorie> {
    private Connection cnx;

    public ServiceCategorie() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Categorie categorie) throws SQLException {
        String sql = "INSERT INTO categorie (nom_cat) VALUES (?)";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, categorie.getNom_cat());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public ObservableList<Categorie> getCategories() {
        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        String query = "SELECT * FROM categorie";
        ResultSet rs = null;
        try (Connection cnx = DBConnection.getInstance().getCnx();
             PreparedStatement reservationStatement = cnx.prepareStatement(query)) {

            rs = reservationStatement.executeQuery();

            while (rs.next()) {
                Categorie categorie = new Categorie();
                categorie.setId_cat(rs.getInt("id_cat"));
                categorie.setNom_cat(rs.getString("nom_cat"));
                categories.add(categorie);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des categories.", e);
        }
        return categories;

    }
    @Override
    public List<Categorie> selectAll() throws SQLException {
        List<Categorie> categorieList = new ArrayList<>();
        String req = "SELECT * FROM categorie";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Categorie categorie = new Categorie(
                        rs.getInt("id_cat"),
                        rs.getString("nom_cat")
                );
                categorieList.add(categorie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return categorieList;
    }

    @Override
    public void updateOne(Categorie categorie) throws SQLException {
        String sql = "UPDATE categorie SET nom_cat = ? WHERE id_cat = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, categorie.getNom_cat());
            statement.setInt(2, categorie.getId_cat());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String sql = "DELETE FROM categorie WHERE id_cat = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public String getNomCategorieById(int id) {
        String nomCategorie = null;
        String sql = "SELECT nom_cat FROM categorie WHERE id_cat = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomCategorie = resultSet.getString("nom_cat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve category name from the database.");
        }
        System.out.println("aa");

        return nomCategorie;
    }


    public Categorie getCategorieByNom(String nomCategorie) {
        Categorie categorie = null;
        String query = "SELECT * FROM categorie WHERE nom_cat = ?";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            st.setString(1, nomCategorie);
            rs = st.executeQuery();

            if (rs.next()) {
                categorie = new Categorie();
                categorie.setId_cat(rs.getInt("id_cat"));
                categorie.setNom_cat(rs.getString("nom_cat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de la catégorie par son nom.", e);
        } finally {
            // Fermeture des ressources
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categorie;
    }
    public Categorie getCategorieById(int idCategorie) {
        Categorie categorie = null;
        String query = "SELECT * FROM categorie WHERE id_cat = ?";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            st.setInt(1, idCategorie);
            rs = st.executeQuery();

            if (rs.next()) {
                categorie = new Categorie();
                categorie.setId_cat(rs.getInt("id_cat"));
                categorie.setNom_cat(rs.getString("nom_cat"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération de la catégorie par son ID.", e);
        } finally {
            // Fermeture des ressources
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return categorie;
    }


}



