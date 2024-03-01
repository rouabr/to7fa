package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class AjouterPersonne {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea tfage;

    @FXML
    private TextArea tfnom;

    @FXML
    private TextArea tfprenom;

    @FXML
    private Button tfsave;

    @FXML
    void AjouterPersonne(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert tfage != null : "fx:id=\"tfage\" was not injected: check your FXML file 'AjouterPersonne.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'AjouterPersonne.fxml'.";
        assert tfprenom != null : "fx:id=\"tfprenom\" was not injected: check your FXML file 'AjouterPersonne.fxml'.";
        assert tfsave != null : "fx:id=\"tfsave\" was not injected: check your FXML file 'AjouterPersonne.fxml'.";

    }

}
