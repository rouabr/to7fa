package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Musee;
import models.ReservationMusee;
import services.ServiceMusee;
import services.ServiceReservation;

public class ReservationBackFXML {

    @FXML
    private AnchorPane root;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<ReservationMusee, String> coldatereservation;

    @FXML
    private TableColumn<ReservationMusee,String> colnommusee;

    @FXML
    private TableColumn<ReservationMusee, Integer> colticketsreserves;

    @FXML
    private TableColumn<ReservationMusee, String> colusername;

    @FXML
    private DatePicker tfdatereservation;

    @FXML
    private Spinner<Integer> tfticketsreserves;

    @FXML
    private Button supprimerReservationbtn;

    @FXML
    private Button btnModifierReservation;

    @FXML
    private TableView<ReservationMusee> tvReservations;

    @FXML
    private ChoiceBox<String> tfnommuseereservation;


    @FXML
    public void showReservations(){
        ServiceReservation sr = new ServiceReservation();
        ObservableList<ReservationMusee> list = sr.getReservations();
        System.out.println(list);
        tvReservations.setItems(list);
        colnommusee.setCellValueFactory(new PropertyValueFactory<ReservationMusee,String>("nom_musee"));
        coldatereservation.setCellValueFactory(new PropertyValueFactory<ReservationMusee,String>("date_reservation"));
        colusername.setCellValueFactory(new PropertyValueFactory<ReservationMusee,String>("username"));
        colticketsreserves.setCellValueFactory(new PropertyValueFactory<ReservationMusee,Integer>("nbr_tickets_reserves"));
    }
    private void setupRootClickEvent() {
        root.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Node node = mouseEvent.getPickResult().getIntersectedNode();
                while (node != null) {
                    if (node == tvReservations) {
                        return;
                    }
                    node = node.getParent();
                }
                disableControls();
            }
        });
    }

    private void disableControls() {
        btnModifierReservation.setDisable(true);
        supprimerReservationbtn.setDisable(true);
        tfnommuseereservation.setDisable(true);
        tfticketsreserves.setDisable(true);
        tfdatereservation.setDisable(true);
    }
    @FXML
    void initialize() {
        assert btnModifierReservation != null : "fx:id=\"btnModifierReservation\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert coldatereservation != null : "fx:id=\"coldatereservation\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert colnommusee != null : "fx:id=\"colnommusee\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert colticketsreserves != null : "fx:id=\"colticketsreserves\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert colusername != null : "fx:id=\"colusername\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert supprimerReservationbtn != null : "fx:id=\"supprimerReservationbtn\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert tfdatereservation != null : "fx:id=\"tfdatereservation\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert tfnommuseereservation != null : "fx:id=\"tfnommuseereservation\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert tfticketsreserves != null : "fx:id=\"tfticketsreserves\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        assert tvReservations != null : "fx:id=\"tvReservations\" was not injected: check your FXML file 'ReservationBackFXML.fxml'.";
        ServiceMusee sm = new ServiceMusee();
        List<String> museumNames = sm.getAllMuseumNames();
        tfnommuseereservation.getItems().addAll(museumNames);
        btnModifierReservation.setDisable(true);
        supprimerReservationbtn.setDisable(true);
        tfnommuseereservation.setDisable(true);
        tfticketsreserves.setDisable(true);
        tfdatereservation.setDisable(true);
        tvReservations.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnModifierReservation.setDisable(false);
                supprimerReservationbtn.setDisable(false);
                tfnommuseereservation.setDisable(false);
                tfticketsreserves.setDisable(false);
                tfdatereservation.setDisable(false);
            } else {
                btnModifierReservation.setDisable(true);
                supprimerReservationbtn.setDisable(true);
                tfnommuseereservation.setDisable(true);
                tfticketsreserves.setDisable(true);
                tfdatereservation.setDisable(true);
            }
        });
        showReservations();
    }
    @FXML
    void supprimerReservation(ActionEvent event){
        ReservationMusee reservationSelectionne = tvReservations.getSelectionModel().getSelectedItem();

        // Vérifiez si un musée est sélectionné
        if (reservationSelectionne != null) {
            try {
                // Supprimez le musée en utilisant l'ID du musée sélectionné
                ServiceReservation sr = new ServiceReservation();
                sr.deleteOne(reservationSelectionne.getReservation_id());

                // Affichez un message de réussite ou faites d'autres actions nécessaires
                System.out.println("Reservation deleted successfully.");

                // Mettez à jour votre TableView pour refléter les changements
                showReservations();
            } catch (SQLException e) {
                // Gérez les exceptions SQL
                e.printStackTrace();
                // Affichez un message d'erreur à l'utilisateur si nécessaire
            }
        } else {
            // Si aucun musée n'est sélectionné, affichez un message d'erreur ou effectuez d'autres actions nécessaires
            System.out.println("No reservation selected.");
        }
    }
    @FXML
    void getDataReservation(MouseEvent event) {
        ReservationMusee reservation = tvReservations.getSelectionModel().getSelectedItem();
        if (reservation != null) {
            try {
                ServiceMusee sm = new ServiceMusee();
                List<String> museumNames = sm.getAllMuseumNames();
                tfnommuseereservation.setValue(reservation.getNom_musee()); // Set the value of the ChoiceBox
                String selectedMuseumName = tfnommuseereservation.getValue();

                Musee selectedMuseum = sm.getMuseeByName(selectedMuseumName);
                int nbr_tickets_disponibles = selectedMuseum.getNbr_tickets_disponibles();

// Create a new SpinnerValueFactory
                SpinnerValueFactory<Integer> valueFactory =
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, nbr_tickets_disponibles, reservation.getNbr_tickets_reserves());

// Set the SpinnerValueFactory to the Spinner
                tfticketsreserves.setValueFactory(valueFactory);

                // Convert java.util.Date to java.sql.Date
                java.util.Date utilDate = reservation.getDate_reservation();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                // Convert java.sql.Date to LocalDate
                LocalDate localDate = sqlDate.toLocalDate();

                // Set the LocalDate to tfdatereservation
                tfdatereservation.setValue(localDate);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("An error occurred while fetching data from the database.");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An error occurred.");
            }
        }
    }
    @FXML
    private void modifierReservation(ActionEvent event) {
        try {
            ReservationMusee reservation = tvReservations.getSelectionModel().getSelectedItem();

            if (reservation != null) {
                Musee musee = new Musee();
                ServiceMusee sm = new ServiceMusee();
                musee = sm.getMuseeByName(tfnommuseereservation.getValue());
                LocalDate localDate = tfdatereservation.getValue();
                java.sql.Date date = java.sql.Date.valueOf(localDate);

                ReservationMusee rm = new ReservationMusee(date, tfticketsreserves.getValue(), musee);
                ServiceReservation sr = new ServiceReservation();
                sr.modifyOne(reservation.getReservation_id(), date, tfticketsreserves.getValue(), musee.getId_musee(), tfnommuseereservation.getValue());
                showReservations();
                System.out.println("Reservation updated successfully.");
            } else {
                System.out.println("No reservation selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the reservation. Please try again later.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number for tickets.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the museum.");
        }
    }

}

