package controller;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Livreur;
import services.serviceLivreur;

public class LivreurFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<Livreur,String> coladresse;

    @FXML
    private TableColumn<Livreur, String> colmatricule;

    @FXML
    private TableColumn<Livreur, String> colnom;

    @FXML
    private TableColumn<Livreur, String> colprenom;

    @FXML
    private TableColumn<Livreur, String> coltelephone;

    @FXML
    private TableView<Livreur> tvlivreurs;
    @FXML
    private Button ajouterbtn;
    @FXML
    private Button modifeirbtn;

    @FXML
    private Button supprimerbtn;
    @FXML
    private TextField tfadresse;

    @FXML
    private TextField tfmatricule;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tftelephone;

    @FXML
    void ajouterLivreur(ActionEvent event) {
        try {
            // Créer un nouveau musée en incluant le chemin de l'image
            Livreur m = new Livreur(tfnom.getText(), tfadresse.getText(), tfprenom.getText(), tfmatricule.getText(),tftelephone.getText());

            // Insérer le nouveau musée
             serviceLivreur sm = new serviceLivreur();
             sm.insertOne(m);
             tvlivreurs.getSelectionModel().clearSelection();
            showLivreurs();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Added successfully");
                alert.show();

    } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    @FXML
    public void showLivreurs(){
        serviceLivreur sr = new serviceLivreur();
        ObservableList<Livreur> list = sr.getLivreurs();
        System.out.println(list);
        tvlivreurs.setItems(list);
        colnom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("nom_livreur"));
        colprenom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("prenom_livreur"));
        coladresse.setCellValueFactory(new PropertyValueFactory<Livreur,String>("adresse"));
        colmatricule.setCellValueFactory(new PropertyValueFactory<Livreur,String>("matricule"));
        coltelephone.setCellValueFactory(new PropertyValueFactory<Livreur,String>("telephone"));

    }

    @FXML
    void supprimerLivreur(ActionEvent event) {
        Livreur livreurSelectionne = tvlivreurs.getSelectionModel().getSelectedItem();

        // Vérifiez si un musée est sélectionné
        if (livreurSelectionne != null) {
            try {
                // Supprimez le musée en utilisant l'ID du musée sélectionné
                serviceLivreur sl = new serviceLivreur();
                sl.deleteOne(livreurSelectionne.getId_livreur());

                // Affichez un message de réussite ou faites d'autres actions nécessaires
                System.out.println("livreur deleted successfully.");

                // Mettez à jour votre TableView pour refléter les changements
                showLivreurs();
            } catch (SQLException e) {
                // Gérez les exceptions SQL
                e.printStackTrace();
                // Affichez un message d'erreur à l'utilisateur si nécessaire
            }
        } else {
            // Si aucun musée n'est sélectionné, affichez un message d'erreur ou effectuez d'autres actions nécessaires
            System.out.println("No livreur selected.");
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Livreur livreur = tvlivreurs.getSelectionModel().getSelectedItem();
        if (livreur != null) {
            tfnom.setText(livreur.getNom_livreur());
            tfadresse.setText(livreur.getAdresse());
            tfmatricule.setText(livreur.getMatricule());
            tfprenom.setText(livreur.getPrenom_livreur());
            tftelephone.setText(livreur.getTelephone());

        }
    }
    @FXML
    void modifierLivreur(ActionEvent event) {
        try {
            Livreur livreurSelectionne = tvlivreurs.getSelectionModel().getSelectedItem();

            if (livreurSelectionne != null) {

                Livreur m = new Livreur(tfnom.getText(), tfprenom.getText(), tfadresse.getText(),tftelephone.getText(),tfmatricule.getText());
                m.setId_livreur(livreurSelectionne.getId_livreur());
                serviceLivreur sm = new serviceLivreur();
                sm.updateOne(m);
                tvlivreurs.getSelectionModel().clearSelection();

                showLivreurs();
                System.out.println("Livreur updated successfully.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Modified successfully");
                alert.show();

            } else {
                System.out.println("No livreur selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the museum. Please try again later.");
        }
    }


    @FXML
    void initialize() {
        assert ajouterbtn != null : "fx:id=\"ajouterbtn\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert coladresse != null : "fx:id=\"coladresse\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert colmatricule != null : "fx:id=\"colmatricule\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert colnom != null : "fx:id=\"colnom\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert colprenom != null : "fx:id=\"colprenom\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert coltelephone != null : "fx:id=\"coltelephone\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tfadresse != null : "fx:id=\"tfadresse\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tfmatricule != null : "fx:id=\"tfmatricule\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tfprenom != null : "fx:id=\"tfprenom\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tftelephone != null : "fx:id=\"tftelephone\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        assert tvlivreurs != null : "fx:id=\"tvlivreurs\" was not injected: check your FXML file 'livreurFXML.fxml'.";
        showLivreurs();
    }
}


