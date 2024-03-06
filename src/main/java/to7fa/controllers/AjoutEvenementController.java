package to7fa.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import to7fa.entities.evenement;
//import com.twilio.http.HttpClient;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import to7fa.services.ServiceEvenement;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class AjoutEvenementController implements Initializable {

    ObservableList<String>types = FXCollections.observableArrayList("art","culture","fêtes","nature");

    @FXML
    private TextField TF_description;
    @FXML
    private TextField TF_nom;
    @FXML
    private TextField TF_lieu;
    @FXML
    private TextField TF_capacite;
    @FXML
    private TextField TF_prix;
    @FXML
    private DatePicker dateDebut;
    @FXML
    private TextField TF_image;
    @FXML
    private DatePicker dateFin;
    @FXML
    private ComboBox<String> comboType;
    @FXML
    private TextField typee;
    @FXML
    private Button btnAjouter;
    @FXML
    private ImageView iconeDateFin;
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
    private Button btn_importer;

    public String imagePath = "Image non sélectionnée";
    @FXML
    private ImageView imageevenement;
    @FXML
    private Button btn_retour;



    private String path;
    File selectedFile;
    private String test;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // comboType.setItems(types);

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
    private void ajouterEvenement(ActionEvent event) {


        String nom_event = TF_nom.getText();
        String description_event = TF_description.getText();
        String lieu_event = TF_lieu.getText();

        // String type_event = comboTy   pe.getValue();
        LocalDate dateDebut_local = dateDebut.getValue();
        LocalDate dateFin_local = dateFin.getValue();

        Date dateDebut_event = java.sql.Date.valueOf(dateDebut_local);
        Date dateFin_event = java.sql.Date.valueOf(dateFin_local);
        int capacite_event = Integer.parseInt(TF_capacite.getText());

        String image_event = TF_image.getText();
        System.out.println(TF_image.getText());
        double prix_event = Double.parseDouble(TF_prix.getText());


        evenement ev = new evenement(nom_event, description_event, lieu_event, typee.getText(), dateDebut_event, dateFin_event, capacite_event, image_event, prix_event);
        System.out.println("gg");


        //*****************

    /*HttpPost post = new HttpPost("http://localhost/img/upload.php");
 File imageFile = new File("C:\\xampp\\htdocs\\img\\"+TF_image.getText());
    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
    builder.addBinaryBody("fileToUpload", imageFile, ContentType.DEFAULT_BINARY, imageFile.getName());

    builder.addTextBody("nom", TF_nom.getText());
    HttpEntity entity = builder.build();

    post.setEntity(entity);

    HttpClient client = HttpClientBuilder.create().build();
    try {
        HttpResponse response = client.execute(post);
        HttpEntity responseEntity = response.getEntity();
        String imageUrl = EntityUtils.toString(responseEntity);
        System.out.println(imageUrl);
        // Stocker l'URL de l'image dans la base de données ou l'utiliser pour afficher l'image.
       ev.setImage_event(imageUrl);
    } catch (IOException e) {
        e.printStackTrace();
    }*/
        //*****************


        System.out.println("ll");

            ServiceEvenement sevent = new ServiceEvenement();
            System.out.println("ff");
            sevent.ajouterEvenement2(ev);
            System.out.println("oo");
            Notifications notificationBuilder = Notifications.create()
                    .title("Ajout EVENEMENT")
                    .text("votre évènement a bien été ajouté.")
                    .graphic(null)
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);
            notificationBuilder.showInformation();

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
    @FXML
    private Button btnPDF;

    void genererPDF(ActionEvent event) {
      /*  try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("liste_evenements.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = fileChooser.showSaveDialog(btnPDF.getScene().getWindow());

            if (file != null) {
                ObservableList<evenement> evenements = /* Get your list of events here ;
                PDF.generatePDF(evenements, file);
                System.out.println("PDF generated successfully.");
            } else {
                System.out.println("No file selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while generating PDF.");
        }*/
    }



    private Boolean testSaisie() {
        String erreur = "";


        if (!testNomEvent()) {
            System.out.println("p");
            erreur = erreur + ("Veuillez saisir un nom valide [seulement des caractères et de taille >=3] \n");
        }

        if (!testDateDebut()) {
            System.out.println("v");
            erreur = erreur + ("Veuillez saisir une date valide \n");
        }
        if (!testDateFin()) {
            System.out.println("k");
            erreur = erreur + ("Veuillez saisir une date valide \n");
        }
        if (!testLieuEvent()) {
            System.out.println("m");
            erreur = erreur + ("Veuillez saisir un Lieu valide [seulement des caractères et de taille >=3]\n");
        }
        if (!testCapaciteEvent()) {
            System.out.println("z");
            erreur = erreur + ("Veuillez saisir une capacité valide [seulement une capacité >= 10] \n");
        }
        if (!testPrixEvent()) {
            System.out.println("q");
            erreur = erreur + ("Veuillez saisir un prix valide [seulement un prix >= 10] \n");
        }
        if (!testImageEvent()) {
            System.out.println("7");
            erreur = erreur + ("Veuillez insérer votre Image \n");
        }

        return  testNomEvent() && testDateDebut() && testLieuEvent() && testDateFin() && testCapaciteEvent() && testPrixEvent() && testImageEvent() ;
    }

    private Boolean testNomEvent() {
        int nbNonChar = 0;
        String nomEvent = TF_nom.getText().trim();
        for (int i = 0; i < nomEvent.length(); i++) {
            char ch = nomEvent.charAt(i);
            if (!Character.isLetter(ch) && !Character.isWhitespace(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && nomEvent.length() >= 3) {
          //  iconeNom.setImage(new Image("images/yes.png"));
            return true;
        } else {
          //  iconeNom.setImage(new Image("images/no.png"));

            return false;
        }
    }


    private Boolean testImageEvent() {
        int nbNonChar = 0;
        for (int i = 1; i < TF_image .getText().trim().length(); i++) {
            char ch = TF_image.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }
        if (nbNonChar != 0) {
         //   iconeImage.setImage(new Image("images/yes.png"));

            return true;
        } else {
           // iconeImage.setImage(new Image("images/no.png"));


            return false;

        }
    }

    private Boolean testPrixEvent() {
        if (Double.parseDouble(TF_prix.getText()) >= 10) {
          //  iconecPrix.setImage(new Image("images/yes.png"));


            return true;
        } else {
           // iconecPrix.setImage(new Image("images/no.png"));


            return false;

        }
    }

    private Boolean testCapaciteEvent() {

        if (Integer.parseInt(TF_capacite.getText()) >= 10) {
          //  iconeCapacite.setImage(new Image("images/yes.png"));


            return true;
        } else {
          //  iconeCapacite.setImage(new Image("images/no.png"));

            return false;

        }
    }



   /* public void capacite_event_Input_Validator(TextField TF_capacite) {
    try {
        int capacite = Integer.parseInt(TF_capacite.getText());
        // La saisie est valide, la bordure reste inchangée.
        TF_capacite.setStyle("-fx-border-color: null;");
    } catch (NumberFormatException e) {
        // La saisie n'est pas valide, la bordure devient rouge.
        TF_capacite.setStyle("-fx-border-color: red;");
        throw new IllegalArgumentException("La capacité de l'événement doit être un entier valide.");
    }
} */


    private Boolean testLieuEvent() {
        int nbNonChar = 0;
        for (int i = 1; i < TF_lieu .getText().trim().length(); i++) {
            char ch = TF_lieu.getText().charAt(i);
            if (!Character.isLetter(ch)) {
                nbNonChar++;
            }
        }

        if (nbNonChar == 0 && TF_lieu.getText().trim().length() >= 3) {
          //  iconeLieu.setImage(new Image("images/yes.png"));


            return true;
        } else {
        //    iconeLieu.setImage(new Image("images/no.png"));


            return false;

        }
    }

    private Boolean testDateDebut() {
        LocalDate now = LocalDate.now();
        if ( dateDebut.getValue().compareTo(now) > 0) {
           // iconeDateDebut.setImage(new Image("images/yes.png"));


            return true;
        } else {
           // iconeDateDebut.setImage(new Image("images/no.png"));


        }
        return false;
    }

    private Boolean testDateFin() {
        LocalDate now = LocalDate.now();
        if ((dateFin.getValue().compareTo(now) > 0) && ((dateFin.getValue().isAfter(dateDebut.getValue())) || (dateFin.getValue().isEqual(dateDebut.getValue())))) {
         //   iconeDateFin.setImage(new Image("images/yes.png"));


            return true;
        } else {
          //  iconeDateFin.setImage(new Image("images/no.png"));


        }
        return false;
    }


    // Assuming xamppFolderPath is correctly initialized somewhere in your class
    private String xamppFolderPath = "C:/xampp/htdocs/img/";

    @FXML
    private void importerImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick a banner file!");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png")
        );

        File file = fileChooser.showOpenDialog(null); // Use the primary stage if available instead of null
        if (file != null) {
            try {
                Path source = file.toPath();
                String fileName = file.getName();
                Path destination = Paths.get(xamppFolderPath + fileName);

                // Copy the file to the destination directory
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

                // Update TextField with the file name
                TF_image.setText(fileName);
                System.out.println("Image imported successfully: " + fileName);
            } catch (IOException e) {
                System.err.println("Error importing the image: " + e.getMessage());
                // Optionally, update the UI or show an alert dialog to inform the user of the error
            }
        } else {
            System.out.println("Image import cancelled or failed.");
        }
    }

}






