/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package to7fa.controllers;

import to7fa.entities.evenement;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;

import org.controlsfx.control.Notifications;
import to7fa.services.ServiceEvenement;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class GererEvenementController implements Initializable {


    ObservableList<String>types = FXCollections.observableArrayList("art","culture","fêtes","nature");

    @FXML
    private TextField TF_nomM;
    @FXML
    private TextField TF_lieuM;
    @FXML
    private TextField TF_capaciteM;
    @FXML
    private TextField TF_prixM;
    @FXML
    private TextField TF_descriptionM;
    @FXML
    private TextField TF_imageM;
    @FXML
    private DatePicker dateDebutM;
    @FXML
    private DatePicker dateFinM;

    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_modifier;
    @FXML
    private Button btn_supprimer;
    @FXML
    private TextField TF_id;

    String xamppFolderPath = "C:/xampp/htdocs/img/";

    @FXML
    private Button btn_importer;
    @FXML
    private ImageView imageevenement;
    @FXML
    private ImageView iconeNom;
    @FXML
    private ImageView iconeLieu;
    @FXML
    private ImageView iconeCapacite;
    @FXML
    private ImageView iconecPrix;
    @FXML
    private ImageView iconeDescription;
    @FXML
    private ImageView iconeImage;
    @FXML
    private ImageView iconeType;
    @FXML
    private ImageView iconeDateDebut;
    @FXML
    private ImageView iconeDateFin;
    @FXML
    private ImageView iconeNombreActuel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn_retour.setOnAction(event -> {

            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/AfficherEvenements.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AfficherEvenementsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


    }

    @FXML
    private void modifierEvenement(ActionEvent event) {
        System.out.println("nn");

        ServiceEvenement SE = new ServiceEvenement();
        if(testSaisie()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de modification");
            alert.setContentText("Etes vous sur de vouloir modifier cet évènement ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK){


                evenement ev = new evenement();
                ev.setID_event(SE.getId(TF_id.getText()));
                ev.setNom_event(TF_nomM.getText());
                ev.setDescription_event(TF_descriptionM.getText());
                ev.setLieu_event(TF_lieuM.getText());
                ev.setCapacite_event(Integer.parseInt(TF_capaciteM.getText()));
                ev.setImage_event(TF_imageM.getText());
                ev.setPrix_event(Double.parseDouble(TF_prixM.getText()));
                LocalDate dateDebut_local = dateDebutM.getValue();
                LocalDate dateFin_local = dateFinM.getValue();
                ev.setDateDebut_event(java.sql.Date.valueOf(dateDebut_local));
                ev.setDateFin_event(java.sql.Date.valueOf(dateFin_local));


                // Stocker l'URL de l'image dans la base de données ou l'utiliser pour afficher l'image.


                //*****************




                SE.modifierEvenement(ev);
                if (testSaisie()){
                    Notifications notificationBuilder = Notifications.create()
                            .title("Modification EVENEMENT")
                            .text("votre évènement a bien été modifié.")
                            .graphic(null)
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.BOTTOM_RIGHT);
                    notificationBuilder.showInformation();
                }else {
                    System.out.println("Cancel");
                }
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("/AfficherEvenements.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AfficherEvenementsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void supprimerEvenement(ActionEvent event) {
        System.out.println("kk");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setContentText("Etes vous sur de supprimer cet évènement ?");
        System.out.println("kk");


        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            ServiceEvenement SE = new ServiceEvenement();

            SE.supprimerEvenement(SE.getId(TF_id.getText()));

            Notifications notificationBuilder = Notifications.create()
                    .title("Suppression EVENEMENT")
                    .text("votre évènement a bien été supprimé.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.showInformation();

        } else {
            System.out.println("Cancel");
        }
        try {
            Parent page1 = FXMLLoader.load(getClass().getResource("/AfficherEvenements.fxml"));
            Scene scene = new Scene(page1);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AfficherEvenementsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    void selected_item(int ID_event, String nom_event, String description_event, String lieu_event, String type_event, Date dateDebut_event, Date dateFin_event, int capacite_event, String image_event, double prix_event) {
//       ServicePromotion P = new ServicePromotion();
System.out.println(image_event);

        LocalDate dateDebut_local = dateDebut_event.toLocalDate();
        LocalDate dateFin_local = dateFin_event.toLocalDate();

        TF_id.setText(String.valueOf(ID_event));
        TF_descriptionM.setText(description_event);
        TF_nomM.setText(nom_event);
        TF_lieuM.setText(lieu_event);
        TF_imageM.setText(image_event);
        TF_capaciteM.setText(String.valueOf(capacite_event));
        TF_prixM.setText(String.valueOf(prix_event));
        dateDebutM.setValue(dateDebut_local);
        dateFinM.setValue(dateFin_local);

        String imagePath = xamppFolderPath + image_event; // Constructing the image path
        Image image = new Image(imagePath);

// Set the image to the ImageView
        imageevenement.setImage(image);
        imageevenement.setPreserveRatio(true);

        System.out.println(imagePath); // Verify the constructed path


    }

    @FXML
    private void importerImage(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a banner file !");
        // fileChooser.setInitialDirectory(new File("\\Mehdi\\ESPRIT\\pi java\\zero\\src\\Images"));
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);

// Copier le fichier dans le dossier XAMPP
        Path source = file.toPath();
        String fileName = file.getName();
        Path destination = Paths.get(xamppFolderPath + fileName);



        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            BufferedImage bufferedImage = ImageIO.read(file);
            TF_imageM.setText(fileName);
            System.err.println("1");
            System.err.println("2");
        } catch (IOException ex) {
            System.out.println("could not get the image");
        }
        String imagePath = "images/" + fileName;
    }



    private Boolean testSaisie() {
        String erreur = "";



        if (!testDateDebut()) {
            erreur = erreur + ("Veuillez saisir une date valide \n");
        }
        if (!testDateFin()) {
            erreur = erreur + ("Veuillez saisir une date valide \n");
        }
        if (testLieuEvent()) {
            erreur = erreur + ("Veuillez verifier votre Lieu: seulement des caractères et de nombre >= 3 \n");
        }
        if (!testCapaciteEvent()) {
            erreur = erreur + ("Veuillez verifier votre Capacité: seulement des nombres >= 10 \n");
        }
        if (!testPrixEvent()) {
            erreur = erreur + ("Veuillez verifier votre Prix: seulement des nombres >= 10 \n");
        }
        /*  if (!testImageEvent()) {
            erreur = erreur + ("Veuillez insérer votre Image \n");
        }*/
        System.out.println(erreur);
        return   testDateDebut() && testLieuEvent() && testDateFin() && testCapaciteEvent() && testPrixEvent()  ;
    }


    /* private Boolean testImageEvent() {
         int nbNonChar = 0;
        for (int i = 0; i < TF_imageM.getText().trim().length(); i++) {
            char ch = TF_imageM.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }
        if (nbNonChar == 0) {
            iconeImage.setImage(new Image("images/yes.png"));
            return true;
        } else {
            iconeImage.setImage(new Image("images/no.png"));
            return false;

        }
    }*/

    private Boolean testPrixEvent() {
        if (Double.parseDouble(TF_prixM.getText()) >= 10.0) {
            return true;
        } else {
            return false;

        }
    }

    private Boolean testCapaciteEvent() {

        if (Integer.parseInt(TF_capaciteM.getText()) >= 10) {
            return true;
        } else {
            return false;

        }
    }
    private Boolean testLieuEvent() {
        int nbNonChar = 0;
        for (int i = 1; i < TF_lieuM.getText().trim().length(); i++) {
            char ch = TF_lieuM.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && TF_lieuM.getText().trim().length() >= 3) {
            return true;
        } else {
            return false;

        }
    }

    private Boolean testDateDebut() {
        LocalDate now = LocalDate.now();
        if ( dateDebutM.getValue().compareTo(now) > 0) {
            return true;
        } else {
        }
        return false;
    }

    private Boolean testDateFin() {
        LocalDate now = LocalDate.now();
        if ((dateFinM.getValue().compareTo(now) > 0) && ((dateFinM.getValue().isAfter(dateDebutM.getValue())) || (dateFinM.getValue().isEqual(dateDebutM.getValue())))) {

            return true;
        } else {
        }
        return false;
    }
}
