/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to7fa.controllers;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        try {
        // Parent root = FXMLLoader.load(getClass().getResource("/evenementClient.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvenements.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/codingbeasts/doulicha/views/AfficherParticipations.fxml"));

           // Parent root = FXMLLoader.load(getClass().getResource("/homeAdmin.fxml"));
            // Parent root = FXMLLoader.load(getClass().getResource("/org/to7fa/views/passEvent.fxml"));


            //Scene scene = new Scene(root, 700, 400);
            Scene scene = new Scene(root);

            primaryStage.setTitle("to7fa");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());;
        }
    }

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        launch();
    }

}
