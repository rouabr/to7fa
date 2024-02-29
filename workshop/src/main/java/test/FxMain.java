package test;

import controllers.ReservationFXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FxMain extends Application {
    public static void main(String[] args) {
        launch();
    }
/*
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonneFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Ajouter un musee");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
        stage.show();

    }*/

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menuFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
        stage.show();

    }

  /*  @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Musées");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBounds.getWidth() * 0.9);
        stage.setHeight(screenBounds.getHeight() * 0.9);
        stage.show();
        stage.centerOnScreen();

    }*/
/*
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationBackFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Réservation de musées");
        stage.setScene(scene);
        stage.show();
    }*/
}
