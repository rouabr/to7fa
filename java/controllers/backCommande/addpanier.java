package controllers.backCommande;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.commande;
import models.panierat;
import services.serviceCommande;
import services.servicePanier;

import java.sql.SQLException;

public class addpanier {
    @FXML
    private TextField IDOeuvre;

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
    void add_commande(ActionEvent event) {
        if ((!IDOeuvre.getText().matches("\\d{0,8}") || IDOeuvre.getText().length()==0) || (!IDUser.getText().matches("\\d{0,8}") || IDUser.getText().length()==0) ) {
            showAlert("Invalid Input", "Please enter digits only for ID ");
        }
        else {
            try {
                servicePanier sp = new servicePanier();
                if (sp.searchByIdOeuvre(Integer.parseInt(IDOeuvre.getText())) != 0)
                    showAlert("probleme id ouvre", "this Id already exists ");
                else {


                    panierat p = new panierat(Integer.parseInt(IDUser.getText()), Integer.parseInt(IDOeuvre.getText()));
                    System.out.println(p.getId_oeuvre());

                    sp.insertOne(p);
                    System.out.println(p.getId_oeuvre());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Data inserted successfully!");
                    alert.showAndWait();

                    IDOeuvre.setText("");
                    IDUser.setText("");

                }
                } catch(SQLException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                    alert.show();
                } catch(NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                    alert.show();
                }

        }
    }
    }


