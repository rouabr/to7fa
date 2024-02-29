package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Musee;
import models.User;
import services.ServiceMusee;
import services.ServiceUser;
import utils.DBConnection;
import javafx.scene.control.cell.PropertyValueFactory;

public class AjouterPersonneFXML {

FileChooser fileChooser = new FileChooser();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnajouter;

    @FXML
    private Button btnmodfier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private Button selectImageButton;
    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<Musee,String> coladresse;

    @FXML
    private TableColumn<Musee,String> colusername;

    @FXML
    private TableColumn<Musee,String> coldescription;

    @FXML
    private TableColumn<Musee,String> colnom;

    @FXML
    private TableColumn<Musee,Integer> coltickets;

    @FXML
    private TableColumn<Musee,String> colville;

    @FXML
    private TextField tfadresse;

    @FXML
    private TextField tfusername;

    @FXML
    private TextField tfdescription;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tftickets;

    @FXML
    private ChoiceBox<String> tfville;

    @FXML
    private TableView<Musee> tvmusee;
    private String selectedImagePath;

    @FXML
    void ajouterMusee(ActionEvent event) {
        try {
            // Créer un nouveau musée en incluant le chemin de l'image
            Musee m = new Musee(tfnom.getText(), tfadresse.getText(), tfville.getValue(), Integer.parseInt(tftickets.getText()), tfdescription.getText(), selectedImagePath);
            if (tfnom.getText().isEmpty() || tfadresse.getText().isEmpty() || tfville.getValue().isEmpty() || tftickets.getText().isEmpty() || tfdescription.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("Veuillez remplir tous les champs");
                alert.show();
                return;
            }
            // Insérer le nouveau musée
            ServiceMusee sm = new ServiceMusee();
            boolean success = sm.insertOne(m);
            if(success) {
                tvmusee.getSelectionModel().clearSelection();
                showMusees();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added successfully");
                alert.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fail");
                alert.setContentText("Museum already exists");
                alert.show();
            }
        } catch (SQLException e) {
            // Gérer les exceptions SQL
            e.printStackTrace();


        } catch (NumberFormatException e) {
            // Gérer les exceptions de format de nombre
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données");
            alert.show();
        }
    }
    @FXML
     void supprimerMusee(ActionEvent event) {
         // Obtenez le musée sélectionné dans la TableView
         Musee museeSelectionne = tvmusee.getSelectionModel().getSelectedItem();

         // Vérifiez si un musée est sélectionné
         if (museeSelectionne != null) {
             try {
                 // Supprimez le musée en utilisant l'ID du musée sélectionné
                 ServiceMusee sm = new ServiceMusee();
                 sm.deleteOne(museeSelectionne.getId_musee());

                 tvmusee.getSelectionModel().clearSelection();
                 // Affichez un message de réussite ou faites d'autres actions nécessaires
                 System.out.println("Museum deleted successfully.");

                 // Mettez à jour votre TableView pour refléter les changements
                 showMusees();
                 Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 alert.setTitle("Success");
                 alert.setContentText("Deleted successfully");
                 alert.show();
                 clearFields();

             } catch (SQLException e) {
                 // Gérez les exceptions SQL
                 e.printStackTrace();
                 // Affichez un message d'erreur à l'utilisateur si nécessaire
             }
         } else {
             // Si aucun musée n'est sélectionné, affichez un message d'erreur ou effectuez d'autres actions nécessaires
             System.out.println("No museum selected.");
         }
     }

    @FXML
    private void modifierMusee(ActionEvent event) {
        try {
            // Obtenez le musée sélectionné dans le TableView
            Musee museeSelectionne = tvmusee.getSelectionModel().getSelectedItem();

            // Vérifiez si un musée est sélectionné
            if (museeSelectionne != null) {
                // Créez un nouvel objet Musee avec les données du formulaire

                Musee m = new Musee(tfnom.getText(), tfadresse.getText(), tfville.getValue(), 0, tfdescription.getText(), selectedImagePath);
                if (tfnom.getText().isEmpty() || tfadresse.getText().isEmpty() || tfville.getValue().isEmpty() || tftickets.getText().isEmpty() || tfdescription.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de saisie");
                    alert.setContentText("Veuillez remplir tous les champs");
                    alert.show();
                    return;
                }
                // Vérifiez si le champ tftickets n'est pas vide avant de le convertir en entier
                int tickets = 0;
                if (!tftickets.getText().isEmpty()) {
                    tickets = Integer.parseInt(tftickets.getText());
                }
                m.setNbr_tickets_disponibles(tickets);

                // Mettez à jour le musée en incluant le chemin de l'image
                ServiceMusee sm = new ServiceMusee();
                sm.updateOne(museeSelectionne.getId_musee(), m, selectedImagePath);
                tvmusee.getSelectionModel().clearSelection();

                // Affichez à nouveau la liste des musées après la mise à jour
                showMusees();
                System.out.println("Museum updated successfully.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Modified successfully");
                alert.show();
                clearFields();
            } else {
                System.out.println("No museum selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the museum. Please try again later.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number for tickets.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the museum.");
        }
    }
     @FXML
     void getData(MouseEvent event) {
         Musee musee = tvmusee.getSelectionModel().getSelectedItem();
         if (musee != null) {
             tfnom.setText(musee.getNom_musee());
             tfadresse.setText(musee.getAdresse());
             tfville.setValue(musee.getVille());
             tftickets.setText(Integer.toString(musee.getNbr_tickets_disponibles()));
             tfdescription.setText(musee.getDescription());

             btnajouter.setDisable(true);
         }
     }
    private void clearFields() {
        tfnom.clear();
        tfadresse.clear();
        tfville.setValue(null);
        tftickets.clear();
        tfdescription.clear();
        selectedImagePath = null;
    }
    public ObservableList<Musee> getMusees() {
        ObservableList<Musee> musees = FXCollections.observableArrayList();
        String query = "SELECT * FROM musee";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Musee musee = new Musee();
                musee.setId_musee(rs.getInt("id_musee"));
                musee.setNom_musee(rs.getString("nom_musee"));
                musee.setAdresse(rs.getString("adresse"));
                musee.setVille(rs.getString("ville"));
                musee.setNbr_tickets_disponibles(rs.getInt("nbr_tickets_disponibles"));
                musee.setDescription(rs.getString("description"));
                musees.add(musee);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des musées.", e);
        }
        return musees;
    }
    @FXML
    private void choisirImage(MouseEvent event) {
    File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedImagePath = file.getAbsolutePath();
            System.out.println("Chemin de l'image : " + selectedImagePath);
        }
    }

    @FXML
    public void showMusees(){
        ObservableList<Musee> list = getMusees();
        tvmusee.setItems(list);
        colnom.setCellValueFactory(new PropertyValueFactory<Musee,String>("nom_musee"));
        coladresse.setCellValueFactory(new PropertyValueFactory<Musee,String>("adresse"));
        colville.setCellValueFactory(new PropertyValueFactory<Musee,String>("ville"));
        coldescription.setCellValueFactory(new PropertyValueFactory<Musee,String>("description"));
        coltickets.setCellValueFactory(new PropertyValueFactory<Musee,Integer>("nbr_tickets_disponibles"));
    }

    @FXML
    void initialize() {
        fileChooser = new FileChooser();
        tfville.setItems(FXCollections.observableArrayList(
                "Jendouba", "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Kairouan",
                "Kasserine", "Kebili", "La Manouba", "Le Kef", "Mahdia", "Médenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur",
                "Tunis", "Zaghouan"
        ));
        assert btnajouter != null : "fx:id=\"btnajouter\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert btnmodfier != null : "fx:id=\"btnmodfier\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert btnsupprimer != null : "fx:id=\"btnsupprimer\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert coladresse != null : "fx:id=\"coladresse\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert coldescription != null : "fx:id=\"coldescription\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert colnom != null : "fx:id=\"colnom\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert coltickets != null : "fx:id=\"coltickets\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert colville != null : "fx:id=\"colville\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert selectImageButton != null : "fx:id=\"selectImageButton\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tfadresse != null : "fx:id=\"tfadresse\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tfdescription != null : "fx:id=\"tfdescription\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tftickets != null : "fx:id=\"tftickets\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tfville != null : "fx:id=\"tfville\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        assert tvmusee != null : "fx:id=\"tvmusee\" was not injected: check your FXML file 'AjouterPersonneFXML.fxml'.";
        fileChooser.setInitialDirectory(new File("C:\\Users\\Admin\\IdeaProjects\\workshop\\src\\main\\resources"));
        showMusees();
        tvmusee.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                btnajouter.setDisable(false);
                btnmodfier.setDisable(true); // disable the modify button when no museum is selected
                btnsupprimer.setDisable(true); // disable the delete button when no museum is selected
            } else {
                btnajouter.setDisable(true);
                btnmodfier.setDisable(false); // enable the modify button when a museum is selected
                btnsupprimer.setDisable(false); // enable the delete button when a museum is selected
            }
        });

        tvmusee.getSelectionModel().clearSelection();
        btnmodfier.setDisable(true); // disable the modify button initially
        btnsupprimer.setDisable(true);
        root.setOnMouseClicked(event -> {
            System.out.println("Clicked outside TableView");
            if (!tvmusee.localToScene(tvmusee.getBoundsInLocal()).contains(event.getSceneX(), event.getSceneY())) {
                System.out.println("Clearing selection and fields");
                tvmusee.getSelectionModel().clearSelection();
                clearFields();
                btnajouter.setDisable(false);
                btnmodfier.setDisable(true);
                btnsupprimer.setDisable(true);
            }
        });
        tfnom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\D*") || newValue.length() > 7) {
                tfnom.setText(oldValue);
            }
        });
        tfadresse.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\D*") || newValue.length() > 60) {
                tfadresse.setText(oldValue);
            }
        });
        tfdescription.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\D*") || newValue.length() > 100) {
                tfdescription.setText(oldValue);
            }
        });
        tftickets.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (!newValue.matches("\\d*") || Integer.parseInt(newValue) > 999) {
                    tftickets.setText(oldValue);
                }
            }
        });
    }

}


