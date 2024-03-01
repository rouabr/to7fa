package controllers.backCommande;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.panierat;
import services.servicePanier;

import java.sql.SQLException;

public class updatePanier {
    @FXML
    private TextField IDOeuvre;

    @FXML
    private TextField IDPanier;

    @FXML
    private TextField IDUser;
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void updatePanier(ActionEvent event) {
        if ((!IDOeuvre.getText().matches("\\d{0,8}") || IDOeuvre.getText().length()==0) || (!IDUser.getText().matches("\\d{0,8}") || IDUser.getText().length()==0) ) {
            showAlert("Invalid Input", "Please enter digits only for ID "); }
            else{
                try {


                    panierat p = new panierat(Integer.parseInt(IDPanier.getText()), Integer.parseInt(IDUser.getText()), Integer.parseInt(IDOeuvre.getText()));
                    System.out.println(p.getId_oeuvre());
                    servicePanier sp = new servicePanier();
                    sp.Update(p);
                    System.out.println(p.getId_oeuvre());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Data updated successfully!");
                    alert.showAndWait();
                    IDPanier.setText("");
                    IDOeuvre.setText("");
                    IDUser.setText("");


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
        } }


