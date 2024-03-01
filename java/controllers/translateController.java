package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class translateController {
    private translator translationController = new translator("AIzaSyD3C3qMgEY3GXpKdN0ExrtNjiFWZigv1tg");

    @FXML
    private TextField englishTextField;

    @FXML
    private Label frenchLabel;

    @FXML
    void translateButtonClicked(ActionEvent event) {
        String englishText = englishTextField.getText();
        String translatedText = translationController.translateText(englishText);
        frenchLabel.setText(translatedText);
    }
}
