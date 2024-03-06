package controllers;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import models.Musee;
import models.User;
import services.Payment;
import services.ServiceMusee;
import services.ServiceReservation;
import services.ServiceUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationFrontFXML {

    @FXML
    private DatePicker tfdate;

    @FXML
    private Spinner<Integer> tfnbrtickets;
    @FXML
    private Button reservationbtn;
    private int selectedMuseeId;
    private int idUser;
    long prix= 150;

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
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

        // Vérifier si la date sélectionnée est antérieure à la date actuelle
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = tfdate.getValue();

        if (selectedDate == null || selectedDate.isBefore(currentDate)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Date Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a date on or after today.");
            alert.showAndWait();
            return; // Arrêter le traitement si la date est invalide
        }

        // Convert LocalDate to java.sql.Date
        java.sql.Date date = java.sql.Date.valueOf(selectedDate);

        int nbrTickets = tfnbrtickets.getValue();

        Payment p = new Payment();
        long priceLong = (long) ((prix * 0.32) * 100) * tfnbrtickets.getValue();
        p.processPayment(priceLong);
        webEngine.load("https://dashboard.stripe.com/test/payments");
        StackPane root = new StackPane();
        root.getChildren().addAll(webView);

        // Create a Scene and add the StackPane to it
        Scene scene = new Scene(root, 800, 600);
        Stage primaryStage = new Stage();
        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Load Web Page on Button Click");
        primaryStage.show();

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
