package controllers.backCommande;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.commande;
import services.serviceCommande;

import java.io.IOException;
import java.sql.SQLException;

public class add {
    @FXML
    private TextField id_oeuvre;

    @FXML
    private TextField name;

    @FXML
    private TextField payment;

    @FXML
    private TextField price;
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void add_commande(ActionEvent event) {
        if ((!id_oeuvre.getText().matches("\\d{0,8}") || id_oeuvre.getText().length() == 0)) {
            showAlert("Invalid Input", "Please enter digits only for ID ");
        } else if ((name.getText().length() == 0)) {
            showAlert("Invalid Input", "Please enter the name ");
        } else if ((!payment.getText().matches("\\d{0,8}") || payment.getText().length() != 8)) {
            showAlert("Invalid Input", "Please enter the payment ");
        }
        //   else    if ((!price.getText().matches("\\d{0,8}") || price.getText().length()==0) ) {  showAlert("Invalid Input", "Please enter the payment "); }
        else {

            try {
                System.out.println("hello");
                commande p = new commande(Float.parseFloat(price.getText()), name.getText(), payment.getText(), Integer.parseInt(id_oeuvre.getText()));
                System.out.println(p.getId_oeuvre());
                serviceCommande sp = new serviceCommande();
                sp.insertOne(p);
                System.out.println(p.getId_oeuvre());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Data inserted successfully!");
                alert.showAndWait();
                name.setText("");
                payment.setText("");
                price.setText("");
                id_oeuvre.setText("");


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            }

        }
    }
}
