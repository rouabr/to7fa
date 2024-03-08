    package controllers;
    import java.sql.Timestamp;
    import java.time.LocalDateTime;
    import java.time.ZoneOffset;
    import java.util.List;

    import javafx.collections.ObservableList;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.geometry.Pos;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Button;
    import javafx.scene.control.ChoiceBox;
    import javafx.scene.control.TextField;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.FileChooser;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import models.Categorie;
    import models.Oeuvre;
    import org.controlsfx.control.Notifications;
    import services.ServiceCategorie;
    import services.ServiceOeuvre;

    import javax.swing.*;
    import java.io.File;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardCopyOption;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.SQLException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.List;
    import javafx.stage.FileChooser;

    public class AjouterOeuvre {
        private Connection cnx;

        @FXML
        private ChoiceBox<String> categorieChoiceBox;

        @FXML
        private Button BtnPromotion;

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
        private Button Parcourir;

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
    //    @FXML
    //    public void ajouterOeuvre(ActionEvent event) {
    //        if (TitreOeuvre.getText().isEmpty() || Description.getText().isEmpty()||    Prix.getText().isEmpty() || Date.getText().isEmpty() || Status.getText().isEmpty()||lienImage.getText().isEmpty())
    //        {
    //            Alert alert = new Alert(Alert.AlertType.ERROR);
    //            alert.setTitle("Erreur de saisie");
    //            alert.setContentText("Veuillez remplir tous les champs");
    //            alert.show();
    //            return;}
    //        else {
    //        try {
    //            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),
    //                    Date.getText(), Status.getText(), lienImage.getText());
    //
    //
    //
    //
    //
    //            ServiceOeuvre so = new ServiceOeuvre();
    //            so.insertOne(o);
    //            clearFields(); // Clear the fields after adding
    //            System.out.println("Oeuvre added successfully!");
    //        } catch (SQLException e) {
    //            System.out.println("Error while adding the oeuvre to the database: " + e.getMessage());
    //        } catch (NumberFormatException e) {
    //            System.out.println("Invalid Price: The price must be a valid number!");
    //        }}
    //    }
    //    @FXML
    //    public void ajouterOeuvre(ActionEvent event) {
    //        try {
    //            Oeuvre o = new Oeuvre(TitreOeuvre.getText(), Description.getText(), Float.parseFloat(Prix.getText()),
    //                    Date.getText(), Status.getText(), lienImage.getText());
    //            ServiceOeuvre so = new ServiceOeuvre();
    //            so.insertOne(o);
    //            clearFields(); // Clear the fields after adding
    //            showAlert(Alert.AlertType.INFORMATION, "Success", "The oeuvre has been successfully added!");
    //        } catch (SQLException e) {
    //            showAlert(Alert.AlertType.ERROR, "Database Error", "Error while adding the oeuvre to the database: " + e.getMessage());
    //        } catch (NumberFormatException e) {
    //            showAlert(Alert.AlertType.ERROR, "Invalid Price", "The price must be a valid number!");
    //        }
    //    }
    //
    //    private void showAlert(Alert.AlertType alertType, String title, String content) {
    //        Alert alert = new Alert(alertType);
    //        alert.setTitle(title);
    //        alert.setContentText(content);
    //        alert.showAndWait();
    //    }
    //@FXML
    //public void ajouterOeuvre(ActionEvent event) {
    //    try {
    //        Oeuvre o = new Oeuvre(
    //                TitreOeuvre.getText(),
    //                Description.getText(),
    //                Float.parseFloat(Prix.getText()),
    //                Date.getText(),
    //                Status.getText(),
    //                lienImage.getText()
    //        );
    //        ServiceOeuvre so = new ServiceOeuvre();
    //        so.insertOne(o);
    //    } catch (SQLException e) {
    //        Alert alert = new Alert(Alert.AlertType.ERROR);
    //        alert.setTitle("Erreur de saisie");
    //        alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
    //        alert.show();
    //    } catch (NumberFormatException e) {
    //        Alert alert = new Alert(Alert.AlertType.ERROR);
    //        alert.setTitle("Erreur de saisie");
    //        alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
    //        alert.show();
    //    }
    //
    //    clearFields();
    //}
    @FXML
    void ajouterOeuvre(ActionEvent event) {
        if (!validateInput()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
            return;
        }
        String catnom =categorieChoiceBox.getValue();
        ServiceCategorie sc = new ServiceCategorie();
        Categorie cat= sc.getCategorieByNom(catnom);
        int idUser = 1;
        int idcat = cat.getId_cat();
        try {
            Oeuvre o = new Oeuvre(
                    TitreOeuvre.getText(),
                    Description.getText(),
                    Float.parseFloat(Prix.getText()),
                    Date.getText(),
                    Status.getText(),
                    lienImage.getText(),
                    idcat,
                    idUser

            );
            ServiceOeuvre so = new ServiceOeuvre();
            so.insertOne(o);
            Image originalImage = new Image(String.valueOf(getClass().getResource("/img/cloche.png")));
            double targetWidth = 50; // Set the desired width
            double targetHeight = 50; // Set the desired height
            Image resizedImage = new Image(originalImage.getUrl(), targetWidth, targetHeight, true, true);
            Notifications notification = Notifications.create();
            notification.graphic(new ImageView(resizedImage));
            notification.text("Conseil est ajouté avec Succés");
            notification.title("Ajout Effectué");
            notification.hideAfter(Duration.seconds(4));
            notification.position(Pos.BOTTOM_RIGHT);
            notification.darkStyle();
            notification.show();
            showAlert(Alert.AlertType.INFORMATION, "Success", "The oeuvre has been added successfully!");
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the oeuvre. Please try again later.");
            e.printStackTrace();
        }
    }

        private boolean validateInput() {
            return !TitreOeuvre.getText().isEmpty() &&
                    !Description.getText().isEmpty() &&
                    !Prix.getText().isEmpty() &&
                    !Date.getText().isEmpty() &&
                    !Status.getText().isEmpty() &&
                    !lienImage.getText().isEmpty();
        }

        private void showAlert(Alert.AlertType alertType, String title, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }

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

        @FXML
        void generatePDF(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(null); // Pass null for the stage to use the default stage

            if (file != null) {
                // Generate the PDF file
                // You can implement this part based on your requirements
                System.out.println("PDF file saved to: " + file.getAbsolutePath());
            }
        }


        // Method to apply promotions on Oeuvre prices for a period of 15 days from the date of disposition

        @FXML
        void initialize() {
            assert Ajouter != null : "fx:id=\"Ajouter\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert Date != null : "fx:id=\"Date\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert Description != null : "fx:id=\"Description\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert Parcourir != null : "fx:id=\"Parcourir\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert Prix != null : "fx:id=\"Prix\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert Status != null : "fx:id=\"Status\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert TitreOeuvre != null : "fx:id=\"TitreOeuvre\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert categorieChoiceBox != null : "fx:id=\"categorieChoiceBox\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            assert lienImage != null : "fx:id=\"lienImage\" was not injected: check your FXML file 'AjouterOeuvre.fxml'.";
            ServiceCategorie sm = new ServiceCategorie();
            List<String> oeuvresNames = sm.getAllCategoriesNames();
            categorieChoiceBox.getItems().addAll(oeuvresNames);
        }
    }
