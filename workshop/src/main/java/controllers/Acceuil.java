package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Oeuvre;
import services.ServiceOeuvre;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Acceuil extends Application implements Initializable {
    private List<Oeuvre> ouevs = new ArrayList<>();
    @FXML
    private GridPane grid;
    @FXML
    private VBox AjouterOeuvre;

    @FXML
    private Button myprofile;


    @FXML
    void myprofile(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UserOeuvre.fxml"));
        Stage window =(Stage) myprofile.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    @FXML
    void userOeuvre(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserOeuvre.fxml"));
        Parent root = loader.load();

        UserOeuvre controller = loader.getController();

    }
    @Override
    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
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

    @Override
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
