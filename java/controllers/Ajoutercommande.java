package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.oeuvre;
import services.serviceCommande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.ajoutercommande2.*;


public class Ajoutercommande  implements Initializable {

    @FXML
    private Label NameArticle;

    @FXML
    private Button backo;

    @FXML
    private Label prixArticle;
    @FXML
    private Label TotalArticle;
    @FXML
    private Label alertcard;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tId;

    @FXML
    private TextField stringpayment;

    @FXML
    void ajouter(ActionEvent event) {

    }

    @FXML
    private Button Nextstep;
    public static Label StaticName;
    public static Label StaticPrice;
    public static Label StaticTotal;



    @FXML
    void backTopan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));

    }

    private List<oeuvre> ouevs = new ArrayList<>();

    private List<oeuvre> getData() {
        serviceCommande sp = new serviceCommande();
        List<oeuvre> ouevs = new ArrayList<>();
        oeuvre ouev;
        try {
            for (oeuvre item : sp.selectoeuvre()) {

                ouev = new oeuvre();
                ouev.setName(item.getName());
                ouev.setPrice(item.getPrice());
                ouev.setImgSrc(item.getImgSrc());
                ouev.setColor("ba8f7c");
                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }


    @FXML
    void addCommande(ActionEvent event) throws IOException {
        if (!stringpayment.getText().matches("\\d{0,8}") || stringpayment.getText().length() != 8) {
            Image image = new Image(getClass().getResourceAsStream("/img/card.png"));
           // alertimgcard.setImage(image);
            alertcard.setText("Please Check Your Card !");

        }else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutercommande2.fxml"));
            Stage window = (Stage) Nextstep.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticNameVeri.setText(StaticName.getText());
            StaticPriceVeri.setText(StaticPrice.getText());
            StaticCardVeri.setText(stringpayment.getText());

        }


    }
    @FXML
    void cancel(ActionEvent event) {
        stringpayment.setText("");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
StaticName=NameArticle;
StaticPrice=prixArticle;
StaticTotal=TotalArticle;


    }
}
