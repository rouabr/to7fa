package services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Musee;
import models.User;
import models.ReservationMusee;
import utils.DBConnection;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ServiceReservation {
    public Connection cnx;


    public void insertOne(Date date_reservation, int nbr_tickets_reserves, int iduser, int idmusee) throws SQLException {
        // Define the date format you want
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // Format the date_reservation into the desired string format
        String formattedDate = sdf.format(date_reservation);

        String sql = "INSERT INTO reservation_musee (date_reservation, nbr_tickets_reserves, id_user, id_musee) VALUES (?, ?, ?, ?)";
        try (Connection cnx = DBConnection.getInstance().getCnx();
             PreparedStatement reservationStatement = cnx.prepareStatement(sql)) {

            // Set the values in the SQL query
            reservationStatement.setString(1, formattedDate); // Use setString instead of setDate
            reservationStatement.setInt(2, nbr_tickets_reserves);
            reservationStatement.setInt(3, iduser);
            reservationStatement.setInt(4, idmusee);

            // Execute the insertion query
            reservationStatement.executeUpdate();
            // Get the current number of tickets available
            String sqlGetTickets = "SELECT nbr_tickets_disponibles FROM musee WHERE id_musee = ?";
            PreparedStatement getTicketsStatement = cnx.prepareStatement(sqlGetTickets);
            getTicketsStatement.setInt(1, idmusee);
            ResultSet rs = getTicketsStatement.executeQuery();
            if (rs.next()) {
                int currentTickets = rs.getInt("nbr_tickets_disponibles");

                // Subtract the reserved tickets from the current number of tickets
                int newTickets = currentTickets - nbr_tickets_reserves;

                // Update the number of tickets in the database
                String sqlUpdateTickets = "UPDATE musee SET nbr_tickets_disponibles = ? WHERE id_musee = ?";
                PreparedStatement updateTicketsStatement = cnx.prepareStatement(sqlUpdateTickets);
                updateTicketsStatement.setInt(1, newTickets);
                updateTicketsStatement.setInt(2, idmusee);
                updateTicketsStatement.executeUpdate();
            }
            System.out.println("Reservation added successfully.");
        } catch (SQLException e) {
            // Handle SQL exceptions appropriately (logging, informing the user, etc.)
            e.printStackTrace();
        }
    }

    public void modifyOne(int reservationId, Date newDate, int newNbrTickets, int museeId, String museeName) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(newDate);
        cnx = DBConnection.getInstance().getCnx();

        // Get the current number of reserved tickets for this reservation
        String sqlGetTickets = "SELECT nbr_tickets_reserves FROM reservation_musee WHERE reservation_id = ?";
        PreparedStatement getTicketsStatement = cnx.prepareStatement(sqlGetTickets);
        getTicketsStatement.setInt(1, reservationId);
        ResultSet rs = getTicketsStatement.executeQuery();
        if (rs.next()) {
            int currentReservedTickets = rs.getInt("nbr_tickets_reserves");

            // Calculate the difference between the old and new number of reserved tickets
            int ticketDifference = newNbrTickets - currentReservedTickets;

            // Update the reservation
            String sql = "UPDATE reservation_musee SET date_reservation = ?, nbr_tickets_reserves = ?, id_musee = ? WHERE reservation_id = ?";
            try (Connection cnx = DBConnection.getInstance().getCnx();
                 PreparedStatement reservationStatement = cnx.prepareStatement(sql)) {

                reservationStatement.setString(1, formattedDate);
                reservationStatement.setInt(2, newNbrTickets);
                reservationStatement.setInt(3, museeId); // Correction : Paramètre 3 pour id_musee
                reservationStatement.setInt(4, reservationId); // Correction : Paramètre 4 pour id_reservation

                reservationStatement.executeUpdate();
                // Update the number of available tickets in the musee table
                String sqlUpdateTickets = "UPDATE musee SET nbr_tickets_disponibles = nbr_tickets_disponibles - ? WHERE id_musee = ?";
                PreparedStatement updateTicketsStatement = cnx.prepareStatement(sqlUpdateTickets);
                updateTicketsStatement.setInt(1, ticketDifference);
                updateTicketsStatement.setInt(2, museeId);
                updateTicketsStatement.executeUpdate();

                System.out.println("Reservation updated successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }  }
    }
    public void deleteOne(int reservationId) throws SQLException {
        String sqlGetReservation = "SELECT nbr_tickets_reserves, id_musee FROM reservation_musee WHERE reservation_id = ?";
        try (Connection cnx = DBConnection.getInstance().getCnx();
             PreparedStatement getReservationStatement = cnx.prepareStatement(sqlGetReservation)) {

            getReservationStatement.setInt(1, reservationId);
            ResultSet rs = getReservationStatement.executeQuery();
            if (rs.next()) {
                int reservedTickets = rs.getInt("nbr_tickets_reserves");
                int museeId = rs.getInt("id_musee");

                String sqlGetTickets = "SELECT nbr_tickets_disponibles FROM musee WHERE id_musee = ?";
                PreparedStatement getTicketsStatement = cnx.prepareStatement(sqlGetTickets);
                getTicketsStatement.setInt(1, museeId);
                ResultSet rsTickets = getTicketsStatement.executeQuery();
                if (rsTickets.next()) {
                    int currentTickets = rsTickets.getInt("nbr_tickets_disponibles");

                    // Add the reserved tickets to the current number of tickets
                    int newTickets = currentTickets + reservedTickets;
                    // Update the number of tickets in the database
                    String sqlUpdateTickets = "UPDATE musee SET nbr_tickets_disponibles = ? WHERE id_musee = ?";
                    PreparedStatement updateTicketsStatement = cnx.prepareStatement(sqlUpdateTickets);
                    updateTicketsStatement.setInt(1, newTickets);
                    updateTicketsStatement.setInt(2, museeId);
                    updateTicketsStatement.executeUpdate();
                }

                // Delete the reservation
                String sqlDeleteReservation = "DELETE FROM reservation_musee WHERE reservation_id = ?";
                PreparedStatement deleteReservationStatement = cnx.prepareStatement(sqlDeleteReservation);
                deleteReservationStatement.setInt(1, reservationId);
                deleteReservationStatement.executeUpdate();

                System.out.println("Reservation deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ReservationMusee> getReservations() {
        ObservableList<ReservationMusee> reservations = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservation_musee";
        ResultSet rs = null;
        try (Connection cnx = DBConnection.getInstance().getCnx();
             PreparedStatement reservationStatement = cnx.prepareStatement(query)) {

            rs = reservationStatement.executeQuery();

            while (rs.next()) {
                ReservationMusee reservation = new ReservationMusee();
                reservation.setReservation_id(rs.getInt("reservation_id"));
                reservation.setDate_reservation(rs.getDate("date_reservation"));
                reservation.setNbr_tickets_reserves(rs.getInt("nbr_tickets_reserves"));
                int userId = rs.getInt("id_user");
                System.out.println(userId);
                ServiceUser su = new ServiceUser();
                User user = su.getUserById(userId);
                int museeId = rs.getInt("id_musee");
                System.out.println(museeId);
                ServiceMusee sm = new ServiceMusee();
                Musee musee = sm.getMusee(museeId);
                reservation.setUser(user);
                reservation.setMusee(musee);

                // Continue setting other properties of reservation from the result set
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des réservations.", e);
        }
        return reservations;

    }
    public ObservableList<ReservationMusee> getReservationsByMuseum(String museeName) {
        ObservableList<ReservationMusee> reservations = FXCollections.observableArrayList();
        try (Connection connection = DBConnection.getInstance().getCnx()) {
            // Obtenir la date courante
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            String query = "SELECT rm.reservation_id, rm.date_reservation, rm.nbr_tickets_reserves, rm.id_user, rm.id_musee " +
                    "FROM reservation_musee rm " +
                    "JOIN musee m ON rm.id_musee = m.id_musee " +
                    "WHERE m.nom_musee = ? AND DATE(rm.date_reservation) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, museeName);
            statement.setDate(2, currentDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int reservationId = resultSet.getInt("reservation_id");
                Date dateReservation = resultSet.getDate("date_reservation");
                int nbrTicketsReserves = resultSet.getInt("nbr_tickets_reserves");
                System.out.println("nbrTicketsReserves: " + nbrTicketsReserves);
                int userId = resultSet.getInt("id_user");
                int museeId = resultSet.getInt("id_musee");

                // You need to get the User and Musee objects associated with these IDs
                ServiceMusee sm = new ServiceMusee();
                ServiceUser su = new ServiceUser();
                User user = su.getUserById(userId);
                Musee musee = sm.getMusee(museeId);

                ReservationMusee reservation = new ReservationMusee(reservationId, dateReservation, nbrTicketsReserves, user, musee);
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}