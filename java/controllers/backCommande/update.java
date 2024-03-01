package controllers.backCommande;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.commande;
import services.serviceCommande;

import java.sql.Date;
import java.sql.SQLException;

public class update {

    @FXML
    private TextField ID;

    @FXML
    private TextField id_oeuvre;

    @FXML
    private TextField name;

    @FXML
    private TextField payment;

    @FXML
    private TextField price;
    private Date datee;
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Update_Command(ActionEvent event) {
        if ((!ID.getText().matches("\\d{0,8}") || ID.getText().length() == 0)) {
            showAlert("Invalid Input", "Please enter digits only for ID ");
        }
       else  if ((!id_oeuvre.getText().matches("\\d{0,8}") || id_oeuvre.getText().length() == 0)) {
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
                commande p = new commande(Integer.parseInt(ID.getText()), Float.parseFloat(price.getText()), name.getText(), datee, payment.getText(), Integer.parseInt(id_oeuvre.getText()));
                System.out.println(p.getId_oeuvre());
                serviceCommande sp = new serviceCommande();
                sp.updateOne(p);
                System.out.println(p.getId_oeuvre());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Data Updated successfully!");
                alert.showAndWait();
                ID.setText("");
                name.setText("");
                payment.setText("");
                price.setText("");
                id_oeuvre.setText("");


            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
                alert.show();
            }

        }
    }

    }

