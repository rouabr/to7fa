package services;

import models.Musee;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMusee implements CRUD<Musee> {
    private Connection cnx;

    public ServiceMusee() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public Boolean insertOne(Musee musee) throws SQLException {
        boolean isInserted = false;
        String checkSql = "SELECT * FROM musee WHERE nom_musee = ?";

        try (PreparedStatement checkStatement = cnx.prepareStatement(checkSql)) {
            checkStatement.setString(1, musee.getNom_musee());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // nom_musee already exists, show an alert or throw an exception

                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String museeSql = "INSERT INTO musee (nom_musee, adresse, ville, nbr_tickets_disponibles, description, image_musee) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement museeStatement = cnx.prepareStatement(museeSql)) {
            museeStatement.setString(1, musee.getNom_musee());
            museeStatement.setString(2, musee.getAdresse());
            museeStatement.setString(3, musee.getVille());
            museeStatement.setInt(4, musee.getNbr_tickets_disponibles());
            museeStatement.setString(5, musee.getDescription());
            museeStatement.setString(6, musee.getImage_path());
            museeStatement.executeUpdate();
            isInserted = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInserted;
    }

    public void deleteOne(int id_musee) throws SQLException {
        String sql = "DELETE FROM musee WHERE id_musee = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, id_musee);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Handle case where the museum with the specified ID does not exist
                throw new SQLException("Museum with ID " + id_musee + " not found.");
            }
            System.out.println("Museum with ID " + id_musee + " deleted successfully.");
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately (log, inform user, etc.)
            e.printStackTrace();
        }
    }
    public void displayOne() throws SQLException {
        String sql = "SELECT * FROM musee";

        try (PreparedStatement statement = cnx.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id_musee = resultSet.getInt("id_musee");
                String nom_musee = resultSet.getString("nom_musee");
                String adresse = resultSet.getString("adresse");
                String ville = resultSet.getString("ville");
                int nbr_tickets_disponibles = resultSet.getInt("nbr_tickets_disponibles");
                String description = resultSet.getString("description");

                System.out.println("ID: " + id_musee);
                System.out.println("Nom: " + nom_musee);
                System.out.println("Adresse: " + adresse);
                System.out.println("Ville: " + ville);
                System.out.println("Nombre de tickets disponibles: " + nbr_tickets_disponibles);
                System.out.println("Description: " + description);
                System.out.println();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately (log, inform user, etc.)
            e.printStackTrace();
        }
    }
    public void updateOne(int id_musee, Musee updatedMuseum, String imagePath) throws SQLException {
        String checkSql = "SELECT * FROM musee WHERE id_musee = ?";
        try (PreparedStatement checkStatement = cnx.prepareStatement(checkSql)) {
            checkStatement.setInt(1, id_musee);
            ResultSet resultSet = checkStatement.executeQuery();

            if (!resultSet.next()) {
                // id_musee does not exist, insert a new record
                insertOne(updatedMuseum);
                return;
            }
        }

        String sql = "UPDATE musee SET nom_musee = ?, adresse = ?, ville = ?, nbr_tickets_disponibles = ?, description = ?, image_musee = ? WHERE id_musee = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, updatedMuseum.getNom_musee());
            statement.setString(2, updatedMuseum.getAdresse());
            statement.setString(3, updatedMuseum.getVille());
            statement.setInt(4, updatedMuseum.getNbr_tickets_disponibles());
            statement.setString(5, updatedMuseum.getDescription());
            statement.setString(6, imagePath);
            statement.setInt(7, id_musee);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Museum with ID " + id_musee + " not found.");
            }
            System.out.println("Museum with ID " + id_musee + " updated successfully.");
        }
    }
    @Override
    public Musee getUserByUsername(String s) throws SQLException {
        // Implémentation pour récupérer un musée par le nom d'utilisateur
        return null; // Ou retourner le musée correspondant au nom d'utilisateur s
    }
    @Override
    public Musee getMusee(int id) throws SQLException {
        Musee musee = null;
        String query = "SELECT * FROM musee WHERE id_musee = ?";
        if (cnx == null || cnx.isClosed()) {
            // reconnect to the database
            cnx = DBConnection.getInstance().getCnx();
        }
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    musee = new Musee();
                    musee.setId_musee(rs.getInt("id_musee"));
                    musee.setNom_musee(rs.getString("nom_musee"));
                    musee.setAdresse(rs.getString("adresse"));
                    musee.setVille(rs.getString("ville"));
                    musee.setNbr_tickets_disponibles(rs.getInt("nbr_tickets_disponibles"));
                    musee.setDescription(rs.getString("description"));
                    musee.setImage_path(rs.getString("image_musee"));
                }
            }
        }
        return musee;
    }
    public Musee getMuseeByName(String name) throws SQLException {
        Musee musee = null;
        String query = "SELECT * FROM musee WHERE nom_musee = ?";
        if (cnx == null || cnx.isClosed()) {
            // reconnect to the database
            cnx = DBConnection.getInstance().getCnx();
        }
        try (PreparedStatement st = cnx.prepareStatement(query)) {
            st.setString(1, name);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    musee = new Musee();
                    musee.setId_musee(rs.getInt("id_musee"));
                    musee.setNom_musee(rs.getString("nom_musee"));
                    musee.setAdresse(rs.getString("adresse"));
                    musee.setVille(rs.getString("ville"));
                    musee.setNbr_tickets_disponibles(rs.getInt("nbr_tickets_disponibles"));
                    musee.setDescription(rs.getString("description"));
                    musee.setImage_path(rs.getString("image_musee"));
                }
            }
        }
        return musee;
    }
    public int getAvailableTickets(int museeId) {
        int availableTickets = 0;
        String query = "SELECT nbr_tickets_disponibles FROM Musee WHERE id_musee = ?";
        try {
            if (cnx == null || cnx.isClosed()) {
                // reconnect to the database
                cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
            }
            try (PreparedStatement ps = cnx.prepareStatement(query)) {
                ps.setInt(1, museeId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        availableTickets = rs.getInt("nbr_tickets_disponibles");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while getting available tickets: " + e.getMessage());
        }
        return availableTickets;
    }
    public List<String> getAllMuseumNames() {
        List<String> names = new ArrayList<>();
        try {
            // Establish a connection to your database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");

            // Create a statement
            Statement statement = connection.createStatement();

            // Execute a query to get all museum names
            ResultSet resultSet = statement.executeQuery("SELECT nom_musee FROM Musee");

            // Iterate over the result set and add each museum name to the list
            while (resultSet.next()) {
                names.add(resultSet.getString("nom_musee"));
            }

            // Close the connections
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }
}

