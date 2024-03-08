package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Livreur;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServiceLivreur implements CRUD<Livreur>{
    private Connection cnx;
    public ServiceLivreur(){
        cnx= DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Livreur livreur) throws SQLException {
        String livreurSql = "INSERT INTO livreur (nom_livreur, prenom_livreur, adresse, telephone, matricule) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement livreurStatement = cnx.prepareStatement(livreurSql)) {
            System.out.println("kk");
            livreurStatement.setString(1, livreur.getNom_livreur());
            livreurStatement.setString(2, livreur.getPrenom_livreur());
            livreurStatement.setString(3, livreur.getAdresse());
            livreurStatement.setString(4, livreur.getTelephone());
            livreurStatement.setString(5, livreur.getMatricule());
            System.out.println("oo");
            livreurStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOne(int i) throws SQLException {
        String sql = "DELETE FROM livreur WHERE id_livreur = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, i);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Handle case where the museum with the specified ID does not exist
                throw new SQLException("Museum with ID " + i + " not found.");
            }
            System.out.println("Museum with ID " + i + " deleted successfully.");
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately (log, inform user, etc.)
            e.printStackTrace();
        }
    }

    @Override
    public void updateOne(Livreur livreur) throws SQLException {
        String sql = "UPDATE livreur SET nom_livreur = ?, prenom_livreur = ?, adresse = ?, telephone = ?, matricule = ? WHERE id_livreur = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, livreur.getNom_livreur());
            statement.setString(2, livreur.getPrenom_livreur());
            statement.setString(3, livreur.getAdresse());
            statement.setString(4, livreur.getTelephone());
            statement.setString(5, livreur.getMatricule());
            statement.setInt(6, livreur.getId_livreur());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Livreur not found.");
            }
            System.out.println("Livreur updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Livreur> getLivreurs() {
        ObservableList<Livreur> livreurs = FXCollections.observableArrayList();
        String query = "SELECT * FROM livreur ";
        ResultSet rs = null;
        try (Connection cnx = DBConnection.getInstance().getCnx();
             PreparedStatement reservationStatement = cnx.prepareStatement(query)) {

            rs = reservationStatement.executeQuery();

            while (rs.next()) {
                Livreur livreur = new Livreur();
                livreur.setId_livreur(rs.getInt("id_livreur"));
                livreur.setNom_livreur(rs.getString("nom_livreur"));
                livreur.setPrenom_livreur(rs.getString("prenom_livreur"));
                livreur.setAdresse(rs.getString("adresse"));
                livreur.setMatricule(rs.getString("matricule"));
                livreur.setTelephone(rs.getString("telephone"));


                // Continue setting other properties of reservation from the result set
                livreurs.add(livreur);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des réservations.", e);
        }
        return livreurs;

    }
    @Override
    public List<Livreur> selectAll() throws SQLException {
        return null;
    }

    @Override
    public List<Livreur> selectAlltri() throws SQLException {
        return null;
    }

    @Override
    public List<Livreur> selectByname(String ch) throws SQLException {
        return null;
    }
}
