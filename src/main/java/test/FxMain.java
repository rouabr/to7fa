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
import services.ServiceWeather;

import java.io.IOException;

public class FxMain extends Application {
    public static void main(String[] args) {
        launch();
    }
    public static Stage stage;
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
/*

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menuFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Menu");
        stage.setScene(scene);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
        stage.show();

    }*/
/*

@Override
public void start(Stage primaryStage) throws Exception{
    Parent root = FXMLLoader.load(getClass().getResource("/MapFXML.fxml"));
    primaryStage.setTitle("Weather 0.2");
    primaryStage.setScene(new Scene(root));
    //primaryStage.setResizable(false);
    primaryStage.show();
}*/

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationFXML.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setTitle("Musées");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setWidth(screenBounds.getWidth() * 0.85);
        stage.setHeight(screenBounds.getHeight() * 0.95);
        stage.show();
        stage.centerOnScreen();

    }
    /*
@Override
public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/EmailSendFXML.fxml"));
    FxMain.stage = stage;
    stage.setScene(new Scene(root));
    stage.setTitle("Mail Sender");
    stage.show();
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
/*
    @Override
    public void start(Stage stage) throws Exception {
FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CaptchaFXML.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setResizable(false);
        stage.setTitle("Captcha Generator");
        stage.setScene(scene);
        stage.show();
}*/
}
