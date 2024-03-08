package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.livraison;
import services.servicePerson;
import utils.DBConnection;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ajouter {
    @FXML
    private TableView<livraison> tvlivraison;

    @FXML
    private Button btnajouter;

    @FXML
    private Button btnmodifier;
    @FXML
    private Button backBtn;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TableColumn<livraison, String> ciladresse;

    @FXML
    private TableColumn<livraison, String> colcommande;

    @FXML
    private TableColumn<livraison, String> coldate;

    @FXML
    private TableColumn<livraison, Float> colfrais;

    @FXML
    private TableColumn<livraison, Integer> colid;

    @FXML
    private TableColumn<livraison, String> colnom;

    @FXML
    private TableColumn<livraison, String> colstatus;

    @FXML
    private TextField tfadresse;

    @FXML
    private TextField tfcommande;

    @FXML
    private DatePicker tfdate;

    @FXML
    private TextField tffrais;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfstatus;
    @FXML
    private Button btnCount;

    @FXML
    void ajouterLivraison(ActionEvent event) {
        String frais=tffrais.getText();

        if (tfid.getText().isEmpty() || tfadresse.getText().isEmpty()  || tfstatus.getText().isEmpty() || tfnom.getText().isEmpty()||tfcommande.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
            return;}
        else {
        try {
            Image originalImage = new Image(String.valueOf(getClass().getResource("/img/verif.png")));
            double targetWidth = 50; // Set the desired width
            double targetHeight = 50; // Set the desired height
            Image resizedImage = new Image(originalImage.getUrl(), targetWidth, targetHeight, true, true);
            Notifications notification = Notifications.create();
            notification.graphic(new ImageView(resizedImage));
            notification.text("Votre Livraison est en cours de traitement!");
            notification.title("Ajout Effectué");
            notification.hideAfter(Duration.seconds(4));
            notification.position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();

            float newfrais = Float.parseFloat(frais);
             ;
            livraison p = new livraison(String.valueOf(tfdate.getValue()),tfadresse.getText(), tfstatus.getText(),newfrais,tfnom.getText(),tfcommande.getText());
            servicePerson sp = new servicePerson();
            sp.insertOne(p);
            Parent root = FXMLLoader.load(getClass().getResource("/views/livraison.fxml"));
            Stage window =(Stage) btnajouter.getScene().getWindow();
            window.setScene(new Scene(root,1315,800));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }}
    public ObservableList<livraison> getLivraison() {
        ObservableList<livraison> livraisons = FXCollections.observableArrayList();
        String query = "SELECT * FROM livraison";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                livraison liv = new livraison();
                liv.setID(rs.getInt("id_livraison"));
                liv.setAdresse(rs.getString("adresse_livraison"));
                liv.setDate(rs.getString("date_livraison"));
                liv.setFrais(rs.getFloat("frais_livraison"));
                liv.setId_livreur(rs.getInt("id_livreur"));
                liv.setStatus(rs.getString("status_livraison"));


                livraisons.add(liv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des musées.", e);
        }
        return livraisons;
    }

  /*  @FXML
    public void showLivraisons(){

        ObservableList<livraison> list = getLivraison();
        tvlivraison.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<livraison,Integer>("id_livraison"));
        ciladresse.setCellValueFactory(new PropertyValueFactory<livraison,String>("adresse_livraison"));
        coldate.setCellValueFactory(new PropertyValueFactory<livraison,String>("date_livraison"));
        colstatus.setCellValueFactory(new PropertyValueFactory<livraison,String>("status_livraison"));
        colfrais.setCellValueFactory(new PropertyValueFactory<livraison,Float>("frais_livraison"));
        colnom.setCellValueFactory(new PropertyValueFactory<livraison,Integer>("name_transporteur"));
    }*/

    @FXML
    void Countdown(ActionEvent event) {
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        javafx.util.Duration.seconds(0),
                        actionEvent -> {
                            LocalDate selectedDate = tfdate.getValue();
                            LocalDateTime eventDateTime = selectedDate.atStartOfDay();

                            java.time.Duration duration = java.time.Duration.between(LocalDateTime.now(), eventDateTime);

                            if (!duration.isNegative()) {
                                long totalSeconds = duration.getSeconds();
                                long days = totalSeconds / 86400; // Number of days
                                System.out.println("Days: " + totalSeconds);
                                long hours = (totalSeconds % 86400) / 3600; // Number of hours
                                long minutes = ((totalSeconds % 86400) % 3600) / 60; // Number of minutes
                                long seconds = ((totalSeconds % 86400) % 3600) % 60; // Number of seconds

                                String time = String.format(" %d days  %d hours %d minutes %d seconds left" ,days, hours, minutes, seconds);

                                // Update the countdown label with the remaining time
                                btnCount.setText(time);
                            } else {
                                // Update the countdown label when the event is happening
                                btnCount.setText("Event is happening now!");
                            }
                        }
                ),
                new KeyFrame(javafx.util.Duration.seconds(1))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    void modifierLivraison(ActionEvent event) {
        String frais=tffrais.getText();
        try {
            float newfrais = Float.parseFloat(frais);

            livraison p = new livraison(Integer.parseInt(tfid.getText()) , String.valueOf(tfdate.getValue()), tfadresse.getText(), tfstatus.getText(),newfrais,tfnom.getText() ,tfcommande.getText()) ;
            servicePerson sp = new servicePerson();
            sp.updateOne(p);
            Parent root = FXMLLoader.load(getClass().getResource("/views/livraison.fxml"));
            Stage window =(Stage) btnajouter.getScene().getWindow();
            window.setScene(new Scene(root,1315,800));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void supprimerLivraison(ActionEvent event) {

    }
    @FXML
    void retour(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/livraison.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML file
            Scene scene = new Scene(root);

            // Get the current stage (window) from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    @FXML
    void initialize() {
        assert btnajouter != null : "fx:id=\"btnajouter\" was not injected: check your FXML file 'aboud.fxml'.";
        assert btnmodifier != null : "fx:id=\"btnmodifier\" was not injected: check your FXML file 'aboud.fxml'.";
        assert btnsupprimer != null : "fx:id=\"btnsupprimer\" was not injected: check your FXML file 'aboud.fxml'.";
        assert ciladresse != null : "fx:id=\"ciladresse\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colcommande != null : "fx:id=\"colcommande\" was not injected: check your FXML file 'aboud.fxml'.";
        assert coldate != null : "fx:id=\"coldate\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colfrais != null : "fx:id=\"colfrais\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colid != null : "fx:id=\"colid\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colnom != null : "fx:id=\"colnom\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colstatus != null : "fx:id=\"colstatus\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfadresse != null : "fx:id=\"tfadresse\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfcommande != null : "fx:id=\"tfcommande\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfdate != null : "fx:id=\"tfdate\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tffrais != null : "fx:id=\"tffrais\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfid != null : "fx:id=\"tfid\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfstatus != null : "fx:id=\"tfstatus\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tvlivraison != null : "fx:id=\"tvlivraison\" was not injected: check your FXML file 'aboud.fxml'.";

    }



}