package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import models.livraison;
import services.servicePerson;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class item {

    @FXML
    private Label adress_livraison;

    @FXML
    private Label id_livraison;

    @FXML
    private Label nom_commande;
    private livraison livr;

    @FXML
    private Label nom_transporteur;

    public void setData(livraison liv) {

        this.livr=liv;
        System.out.println("koko");

        adress_livraison.setText(liv.getAdresse());
        System.out.println("vbn");
        id_livraison.setText(Integer.toString(liv.getID()));
        System.out.println("lmd");
        nom_commande.setText(liv.getNom_commande());
        nom_transporteur.setText(liv.getName());

    }

    @FXML
    void deletebtn(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete?");

        // Show the alert and wait for user response
        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    servicePerson dbHandler = new servicePerson();
                    dbHandler.deleteOne( Integer.parseInt(id_livraison.getText()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Deletion Status");
                    alert.setHeaderText("Deletion successful.");
                    alert.showAndWait();

                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error occurred while deleting the record.");
                    alert.setContentText("Please try again later.");
                    alert.showAndWait();
                    e.printStackTrace(); // Handle the exception according to your application's error handling strategy
                }
            }
        });

    }

    }


