package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CaptchaFXML implements Initializable {
    @FXML
    private Label captchaLabel;

    @FXML
    private TextField captchaCode;

    @FXML
    private Button refreshCaptcha;

    @FXML
    private Button submit;
    @FXML
    private Label msg;

    public String Captcha;
    private int selectedMuseeId;
    private int idUser;
    private int availableTickets;


    public String GenerateCaptcha() {

        char[] data = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        int max = 36;
        String captcha = "";
        int min = 0;
        for (int i = 0; i < 6; i++) {
            int rand = (int) Math.floor(Math.random() * (max - min + 1) + min);
            captcha += data[rand];
        }
        System.out.println(captcha);
        return captcha;

    }
    public void setData(int selectedMuseeId,int idUser) {
        this.selectedMuseeId = selectedMuseeId;
        this.idUser = idUser;
    }
    public void setSelectedMuseeId(int selectedMuseeId) {
        this.selectedMuseeId = selectedMuseeId;
    }
    public void setMaxTickets(int maxTickets) {
        this.availableTickets = maxTickets;
    }

    @FXML
    void onRefreshCaptcha(ActionEvent e) {
        Captcha = GenerateCaptcha();
        captchaLabel.setText(Captcha);
        System.out.println(Captcha);
        captchaCode.setText("");
    }

    @FXML
    void onSubmit(ActionEvent event) throws IOException {
        if (captchaCode.getText().equals(String.valueOf(Captcha))) {
            msg.setText("You have entered the correct code!");
            msg.setTextFill(Color.GREEN);
            // Load ReservationFrontFXML if captcha is correct
            loadReservationFrontFXML();
            Stage stage = (Stage) captchaLabel.getScene().getWindow();
            stage.close();
            //loadReservationFrontFXML();

        } else {
            msg.setText("You have entered the wrong code");
            msg.setTextFill(Color.RED);
            // Check if this is the 3rd attempt
            if (thirdAttempt()) {
                // Exit after the 3rd attempt
                Stage stage = (Stage) captchaLabel.getScene().getWindow();
                stage.close();
            } else {
                // Generate new captcha for another attempt
                Captcha = GenerateCaptcha();
                captchaLabel.setText(Captcha);
                captchaCode.setText("");
            }
        }
    }

    private void loadReservationFrontFXML() {
        try {
            // Load ReservationFrontFXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationFrontFXML.fxml"));
            Parent root = loader.load();

            ReservationFrontFXML controller = loader.getController();
            // Pass any necessary data to ReservationFrontFXML controller if needed
           // controller.initialize(); // Call any initialization method if needed
            controller.setMaxTickets(availableTickets);
            controller.setData(selectedMuseeId,idUser);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Make reservation");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int attemptCount = 0;

    private boolean thirdAttempt() {
        attemptCount++;
        return attemptCount >= 3;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Generate the initial captcha
        Captcha = GenerateCaptcha();
        captchaLabel.setText(Captcha);
    }
}