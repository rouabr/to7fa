package controllers.backCommande;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class back implements Initializable {


    @FXML
    private ImageView Exit;

    @FXML
    private Label MeniBack;

    @FXML
    private Label Menu;
    @FXML
    private StackPane stackpane;

    @FXML
    private AnchorPane slider;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MeniBack.setVisible(true);
            });
        });

        MeniBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MeniBack.setVisible(false);
            });
        });



    }
    @FXML
    void Dashboardpanier(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/dashboardPanier.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void Add(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/add.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Dashboard(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/dashboard.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Delete(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/delete.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Update(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/update.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void home(MouseEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/back.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @FXML
    void UpdatePanier(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/updatePanier.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void DeletePanier(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/deletePanier.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AddPanier(ActionEvent event) {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/backviews/addPanier.fxml"));
            stackpane.getChildren().removeAll();
            stackpane.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }

