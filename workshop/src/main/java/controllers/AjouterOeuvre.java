package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Categorie;
import models.Oeuvre;
import services.ServiceOeuvre;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjouterOeuvre {

    @FXML
    private Button Ajouter;

    @FXML
    private TextField Date;

    @FXML
    private TextField Description;

    @FXML
    private TextField Prix;

    @FXML
    private TextField Status;

    @FXML
    private TextField TitreOeuvre;

    @FXML
    private ImageView image;
    @FXML
    private TextField lienImage;
    private String xamppFolderPath="c:/xampp/htdocs/img/";

    @FXML
    private void clearFields() {
        TitreOeuvre.clear();
        Description.clear();
        Prix.clear();
        Date.clear();
        Status.clear();
        lienImage.clear();
    }


    private List<Oeuvre> ouevs = new ArrayList<>();
    private List<Oeuvre> getData() {
        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

                ouev = new Oeuvre();
                ouev.setTitre(item.getTitre());
                ouev.setDescription(item.getDescription());
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
    void parcourirImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choisi une image");
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("JPEG","*.jpeg"),
                new FileChooser.ExtensionFilter("PNG","*.png")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Path source = file.toPath();
            String fileName = file.getName();
            Path destination = Paths.get(xamppFolderPath + fileName);
            String imgURL=xamppFolderPath+fileName;
            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                lienImage.setText(imgURL);
                Image image1= new Image("file:" +imgURL);
                image.setImage(image1);


            } catch (IOException ex) {
                System.out.println("Could not get the image");
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }


    }
    @FXML
    public void ajouterOeuvre(ActionEvent event) {
        try {
            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),
                    Date.getText(), Status.getText(), lienImage.getText());
            ServiceOeuvre so = new ServiceOeuvre();
            so.insertOne(o);
            clearFields(); // Clear the fields after adding
            System.out.println("Oeuvre added successfully!");
        } catch (SQLException e) {
            System.out.println("Error while adding the oeuvre to the database: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Price: The price must be a valid number!");
        }
    }
    /*@FXML
    public void ajouterOeuvre(ActionEvent event) {
        try {
            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),
                    Date.getText(), Status.getText(), lienImage.getText());
            ServiceOeuvre so = new ServiceOeuvre();
            so.insertOne(o);
            clearFields(); // Clear the fields after adding
            showAlert(Alert.AlertType.INFORMATION, "Success", "The oeuvre has been successfully added!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while adding the oeuvre to the database: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Price", "The price must be a valid number!");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }*/
   /* @FXML
    public void ajouterOeuvre(ActionEvent event) {
        try {
            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),Date.getText(),Status.getText(),lienImage.getText());
            ServiceOeuvre so = new ServiceOeuvre();
            so.insertOne(o);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }

        clearFields();

    }*/
//    @FXML
//    void ajouterOeuvre(ActionEvent event) {
//        try {
//            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),
//                    Date.getText(), Status.getText(), lienImage.getText());
//            ServiceOeuvre so = new ServiceOeuvre();
//            so.insertOne(o);
//            clearFields(); // Effacez les champs après l'ajout
//            showAlert(Alert.AlertType.INFORMATION, "Succès", "L'œuvre a été ajoutée avec succès!");
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Erreur lors de l'ajout de l'œuvre: " + e.getMessage());
//        } catch (NumberFormatException e) {
//            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix doit être un nombre valide!");
//        }
//    }
//    private void showAlert(Alert.AlertType alertType, String title, String content) {
//        Alert alert = new Alert(alertType);
//        alert.setTitle(title);
//        alert.setContentText(content);
//        alert.showAndWait();
//    }
//


}
