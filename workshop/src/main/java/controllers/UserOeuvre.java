package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Oeuvre;

import services.ServiceOeuvre;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserOeuvre implements Initializable {
    private Connection cnx;
    private List<Oeuvre> ouevs = new ArrayList<>();

    @FXML
    private Button AjouterOeuvre;


    @FXML
    private Button Whoarewe;
    @FXML
    private Button BtnPromotion;

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


    // Method to establish the database connection
    private void initializeDatabaseConnection() {
        // Initialize your database connection here
        // For example:
        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to establish database connection.");
        }
    }

    @FXML
    public void applyPromotions() {
        if (cnx == null) {
            System.err.println("Database connection is not initialized.");
            return;
        }

        try {
            // Get the current date
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Calculate the end date of the promotion period (5 seconds from the current date)
            LocalDateTime endDateTime = LocalDateTime.now().plusSeconds(30);

            // SQL query to update the price of Oeuvres within the promotion period
            String sql = "UPDATE oeuvre SET prix = prix * 0.5 WHERE date >= ? AND date <= ?";

            try (PreparedStatement statement = cnx.prepareStatement(sql)) {
                statement.setTimestamp(1, Timestamp.valueOf(currentDateTime));
                statement.setTimestamp(2, Timestamp.valueOf(endDateTime));

                // Execute the update query
                int rowsAffected = statement.executeUpdate();
                System.out.println(rowsAffected + " Oeuvres were updated with promotions.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to apply promotions to Oeuvres.");
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to apply promotions to Oeuvres.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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


    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ouevs.addAll(getData());
        initializeDatabaseConnection();
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


