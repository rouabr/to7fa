package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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

    private List<livraison> getData() {

        servicePerson sp = new servicePerson();
        List<livraison> livres = new ArrayList<>();
        livraison liv;

        try {
            for (livraison item : sp.selectAll()) {

                liv = new livraison();
                liv.setID(item.getID());
                liv.setName(item.getName());
                liv.setStatus(item.getStatus());
                liv.setFrais(item.getFrais());
                liv.setAdresse(item.getAdresse());
                liv.setNom_commande(item.getNom_commande());
                livres.add(liv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livres;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("hi");
        livrs.addAll(getData());


        // TextFields.bindAutoCompletion(searchbtn,words);


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < livrs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/onelivraison.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                item itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(livrs.get(i));
                System.out.println("lala");


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
    public void reload()
    {
        System.out.println("hi");
        livrs.addAll(getData());


        // TextFields.bindAutoCompletion(searchbtn,words);


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < livrs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/onelivraison.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                item itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(livrs.get(i));
                System.out.println("lala");


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


