package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.livraison;
import services.servicePerson;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class affichelivraison implements Initializable {
    private List<livraison> livrs = new ArrayList<>();
    @FXML
    private GridPane grid;
    @FXML
    private Button accueilbtn;

    @FXML
    void acceuil(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/aboud.fxml"));
        Stage window =(Stage) accueilbtn.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));


    }

    private List<livraison> getData() {

        servicePerson sp = new servicePerson();
        List<livraison> livres = new ArrayList<>();
        livraison liv;

        try {
            for (livraison item : sp.selectAll()) {

                liv = new livraison();
                liv.setID(item.getID());
                liv.setId_livreur(item.getId_livreur());
                liv.setStatus(item.getStatus());
                liv.setFrais(item.getFrais());
                liv.setAdresse(item.getAdresse());
                liv.setId_commande(item.getId_commande());
                livres.add(liv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        reload();
    }
    @FXML
    public void reload()
    {
        grid.getChildren().clear();
        System.out.println("12365");
        livrs.clear();
        livrs.addAll(getData());


        // TextFields.bindAutoCompletion(searchbtn,words);


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < livrs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/onelivraison.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                item itemController = fxmlLoader.getController();
                itemController.setData(livrs.get(i));



                grid.add(anchorPane, column, row++); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    }


