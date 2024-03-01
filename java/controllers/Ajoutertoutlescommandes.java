package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.oeuvre;
import services.servicePanier;
import tn.esprit.fxMain;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static controllers.Ajoutertoutlescommandes2.StaticCardVeri;


public class Ajoutertoutlescommandes implements Initializable {

    @FXML
    private Button addCommande;
    private List<oeuvre> ouevs = new ArrayList<>();
    @FXML
    private Label alertcard;

    @FXML
    private ImageView alertimgcard;

    @FXML
    private VBox vbPrice;

    @FXML
    private GridPane grid;
    @FXML
    private Label TotalArticle;

    @FXML
    private Label priceVeri;

    @FXML
    private TextField stringpayment;



    private List<oeuvre> getData() {

        servicePanier sp = new servicePanier();
        List<oeuvre> ouevs = new ArrayList<>();
        oeuvre ouev;

        try {
            for (oeuvre item : sp.selectAlloeuvre()) {

                ouev = new oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
float somme = 0.f;
        ouevs.addAll(getData());

        int column = 0;
        int row = 1;
        for (oeuvre item : ouevs) {

            // Accumulate the price
            somme += item.getPrice();
        }
     

      //  for (int i = 0; i < ouevs.size(); i++) {
              /*
                Label label = new Label("Price: " + item.getPrice());
                vbPrice.getChildren().add(label);*/
        try {
            for (int i = 0; i < ouevs.size(); i++) {

                System.out.println("855");
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/commandeprix.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                commandesPrix commandesPrixX = fxmlLoader.getController();
                commandesPrixX.setDataa(ouevs.get(i));
                grid.add(anchorPane, column,row++);
                GridPane.setMargin(anchorPane, new Insets(3));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TotalArticle.setText(fxMain.CURRENCY +String.valueOf(somme));





            }

    @FXML
    void Nextstep(ActionEvent event) throws IOException {
        if (!stringpayment.getText().matches("\\d{0,8}") || stringpayment.getText().length() != 8) {
            Image image = new Image(getClass().getResourceAsStream("/img/card.png"));
            alertimgcard.setImage(image);
            alertcard.setText("Please Check Your Card !");

        } else {

            Parent root = FXMLLoader.load(getClass().getResource("/views/ajouterroutlescommandes2.fxml"));
            Stage window = (Stage) addCommande.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticCardVeri.setText(stringpayment.getText());
            System.out.println("hi");
            System.out.println(StaticCardVeri.getText());
        }
    }
    @FXML
    private Button backo;
    @FXML
    void backpanier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));
    }

    @FXML
    void cancel(ActionEvent event) {
        stringpayment.setText("");
    }

    }

