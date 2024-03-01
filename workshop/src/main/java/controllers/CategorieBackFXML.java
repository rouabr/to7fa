package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.Categorie;
import services.ServiceCategorie;

public class CategorieBackFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ajouterbtn;

    @FXML
    private TableColumn<Categorie, Integer> colid;

    @FXML
    private TableColumn<Categorie, String> colnom;

    @FXML
    private Button modifierbtn;

    @FXML
    private Button sumpprimerbtn;

    @FXML
    private TextField tfnom;

    @FXML
    private TableView<Categorie> tvcategorie;

    @FXML
    void ajouterCategorie(ActionEvent event) {

        try {
            // Créer un nouveau musée en incluant le chemin de l'image
            Categorie m = new Categorie(tfnom.getText());

            // Insérer le nouveau musée
            ServiceCategorie sc = new ServiceCategorie();
            sc.insertOne(m);
            showCategories();
            tvcategorie.getSelectionModel().clearSelection();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Added successfully");
            alert.show();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @FXML
    void getData(MouseEvent event) {
        Categorie livreur = tvcategorie.getSelectionModel().getSelectedItem();
        if (livreur != null) {
            tfnom.setText(livreur.getNom_cat());


        }
    }

    @FXML
    void modifierCategorie(ActionEvent event) {
        try {
            Categorie categorieSelectionne = tvcategorie.getSelectionModel().getSelectedItem();

            if (categorieSelectionne != null) {

                Categorie m = new Categorie(tfnom.getText());
                m.setId_cat(categorieSelectionne.getId_cat());
                ServiceCategorie sm = new ServiceCategorie();
                sm.updateOne(m);
                tvcategorie.getSelectionModel().clearSelection();

                showCategories();
                System.out.println("categorie updated successfully.");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Modified successfully");
                alert.show();

            } else {
                System.out.println("No categorie selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the categorie. Please try again later.");
        }
    }

    @FXML
    void supprimerCategorie(ActionEvent event) {
        Categorie categorieSelectionne = tvcategorie.getSelectionModel().getSelectedItem();

        if (categorieSelectionne != null) {
            try {
                ServiceCategorie sl = new ServiceCategorie();
                sl.deleteOne(categorieSelectionne.getId_cat());

                System.out.println("categorie deleted successfully.");

                showCategories();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No categorie selected.");
        }
    }
    @FXML
    public void showCategories(){
        ServiceCategorie sr = new ServiceCategorie();
        ObservableList<Categorie> list = sr.getCategories();
        System.out.println(list);
        tvcategorie.setItems(list);
        colnom.setCellValueFactory(new PropertyValueFactory<Categorie,String>("nom_cat"));
        colid.setCellValueFactory(new PropertyValueFactory<Categorie,Integer>("id_cat"));


    }

    @FXML
    void initialize() {
        assert ajouterbtn != null : "fx:id=\"ajouterbtn\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert colid != null : "fx:id=\"colid\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert colnom != null : "fx:id=\"colnom\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert modifierbtn != null : "fx:id=\"modifierbtn\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert sumpprimerbtn != null : "fx:id=\"sumpprimerbtn\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        assert tvcategorie != null : "fx:id=\"tvcategorie\" was not injected: check your FXML file 'CategorieBackFXML.fxml'.";
        showCategories();
    }

}
