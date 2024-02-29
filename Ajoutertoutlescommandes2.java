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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.commande;
import models.oeuvre;
import services.serviceCommande;
import services.servicePanier;
import tn.esprit.fxMain;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Ajoutertoutlescommandes2 implements Initializable {
    @FXML
    private VBox vboxImg;



    @FXML
    private Button backo;
    @FXML
    private Button backp;

    @FXML
    private Label cardVeri;

    @FXML
    private Button orderter;

    @FXML
    private Label priceVeri;

    @FXML
    private AnchorPane scene2;
    @FXML
    private Label nbr;
    @FXML
    private GridPane grid;

    public static Label StaticCardVeri;



    private List<oeuvre> ouevs = new ArrayList<>();


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
        int somme = 0;
        ouevs.addAll(getData());


        for (oeuvre item : ouevs) {
            somme += item.getPrice();
            System.out.println("haw jee");
        }
        priceVeri.setText(fxMain.CURRENCY +String.valueOf(somme));
        nbr.setText(String.valueOf(ouevs.size()));

        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/imgCommande.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                imgCommande ImgCommande = fxmlLoader.getController();
                ImgCommande.setData(ouevs.get(i));



                grid.add(anchorPane, column, row++); //(child,column,row)
                //set grid width

                GridPane.setMargin(anchorPane, new Insets(3));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StaticCardVeri=cardVeri;


    }
    @FXML
    void buy(ActionEvent event) {
        ouevs.clear();
        ouevs.addAll(getData());


        try {

            for (oeuvre item : ouevs) {

                float floatValue = (float) item.getPrice();
                commande p = new commande(floatValue, item.getName(), cardVeri.getText(), item.getId_oeuvre());
                serviceCommande sp = new serviceCommande();
                sp.insertOne(p);
                System.out.println(item.getName());
                servicePanier dbHandler = new servicePanier();
                dbHandler.deleteOne(4, item.getId_oeuvre());
            }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Data inserted successfully!");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
                Stage window = (Stage) orderter.getScene().getWindow();
                window.setScene(new Scene(root, 1315, 800));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }







    @FXML
    void backpanier(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));

    }



    }


