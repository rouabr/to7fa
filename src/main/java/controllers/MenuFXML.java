package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MenuFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btngestionmusee;

    @FXML
    private Button btngestionreservation;
    @FXML
    void onGestionReservation(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationBackFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Gestion reservations");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
    }

    @FXML
    void onGestionmusee(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonneFXML.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        stage.setTitle("Gestion musées");

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));

    }

    @FXML
    void initialize() {
        assert btngestionmusee != null : "fx:id=\"btngestionmusee\" was not injected: check your FXML file 'menuFXML.fxml'.";
        assert btngestionreservation != null : "fx:id=\"btngestionreservation\" was not injected: check your FXML file 'menuFXML.fxml'.";

    }

}