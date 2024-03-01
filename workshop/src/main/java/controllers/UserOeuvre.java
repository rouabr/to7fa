package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.Oeuvre;
import services.ServiceOeuvre;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserOeuvre {
    private List<Oeuvre> ouevs = new ArrayList<>();

    @FXML
    private Button AjouterOeuvre;

    @FXML
    private Button Whoarewe;

    @FXML
    private Button backo;

    @FXML
    private GridPane grid;




    @FXML
    void Ajout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AjouterOeuvre.fxml"));
        Stage window =(Stage) AjouterOeuvre.getScene().getWindow();
        window.setScene(new Scene(root)); // No need to specify width and height here

    }
    @FXML
    void backTopan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Acceuil.fxml"));
        Stage window =(Stage) backo.getScene().getWindow();
        window.setScene(new Scene(root,1315,800));

    }


    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/UserOeuvre.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private List<Oeuvre> getData() {
        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

                ouev = new Oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setDate(item.getDate());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        ouevs.addAll(getData());
        System.out.println("aa");
        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();


                fxmlLoader.setLocation(getClass().getResource("/Item.fxml"));
                System.out.println("dqfsgqf");

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                Item itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(ouevs.get(i));
                System.out.println("lala");

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


