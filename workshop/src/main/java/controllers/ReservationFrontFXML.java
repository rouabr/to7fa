package controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Musee;
import models.User;
import services.ServiceMusee;
import services.ServiceReservation;
import services.ServiceUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationFrontFXML {

    @FXML
    private DatePicker tfdate;

    @FXML
    private Spinner<Integer> tfnbrtickets;
    @FXML
    private Button reservationbtn;
    private int selectedMuseeId;
    private int idUser;


    public void setData(int selectedMuseeId,int idUser) {
        this.selectedMuseeId = selectedMuseeId;
        this.idUser = idUser;
    }
    public void setSelectedMuseeId(int selectedMuseeId) {
        this.selectedMuseeId = selectedMuseeId;
    }
    public void setMaxTickets(int maxTickets) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, maxTickets);
        tfnbrtickets.setValueFactory(valueFactory);
    }
    @FXML
    void onReservationButtonClick(MouseEvent event) throws SQLException {
        // Créer une instance de ServiceReservation

        ServiceReservation sr = new ServiceReservation();


        // Convert LocalDate to java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(tfdate.getValue());

        int nbrTickets = tfnbrtickets.getValue();



        // Appeler la méthode insertOne

            sr.insertOne(date, nbrTickets, idUser, selectedMuseeId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reservation Successful");
        alert.setHeaderText(null);
        alert.setContentText("Reservation created successfully. Check your email for more information.");
        alert.showAndWait();
        Stage stage = (Stage) reservationbtn.getScene().getWindow();
        stage.close();
    }

}
