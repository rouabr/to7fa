package controllers.backCommande;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import services.serviceCommande;
import services.servicePanier;

import java.sql.SQLException;

public class delete {
    @FXML
    private TextField id;

    @FXML
    void delete_commande(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete?");

        // Show the alert and wait for user response
        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    serviceCommande dbHandler = new serviceCommande();
                    dbHandler.deleteOne( Integer.parseInt(id.getText()));
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
