package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Categorie;
import models.Oeuvre;
import services.ServiceCategorie;
import services.ServiceOeuvre;
import utils.DBConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OeuvreBackFXML {

    @FXML
    private TableView<Oeuvre> tvOeuvre;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableColumn<String, Oeuvre> colLienImg;

    @FXML
    private TableColumn<Integer, Oeuvre> colNomCat;

    @FXML
    private TableColumn<String, Oeuvre> colNomUser;

    @FXML
    private TableColumn<String, Oeuvre> coldate;

    @FXML
    private TableColumn<String, Oeuvre> coldescription;

    @FXML
    private TableColumn<Float, Oeuvre> colprix;

    @FXML
    private TableColumn<String, Oeuvre> colstatus;

    @FXML
    private TableColumn<String, Oeuvre> coltitre;




    public ObservableList<Oeuvre> getOeuvre() {
        ObservableList<Oeuvre> oeuvreList = FXCollections.observableArrayList();
        String query = "SELECT oeuvre.*, categorie.nom_cat FROM oeuvre JOIN categorie ON oeuvre.id_cat = categorie.id_cat";
        ServiceOeuvre so = new ServiceOeuvre();
        ServiceCategorie sc = new ServiceCategorie();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {

                Oeuvre oeuvre = new Oeuvre();
                oeuvre.setId_oeuvre(rs.getInt("id_oeuvre"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setDescription(rs.getString("description"));
                oeuvre.setPrix(rs.getFloat("prix"));
                oeuvre.setDate(rs.getString("date"));
                oeuvre.setStatus(rs.getString("status"));
                oeuvre.setId_cat(rs.getInt("id_cat"));
                oeuvre.setNom_cat(rs.getString("nom_cat"));
                // Obtenez la catégorie à partir de son ID
                System.out.println("ss");

                //Categorie categorie = sc.getCategorieById(categorieId);

                // Assurez-vous que la catégorie n'est pas null avant de définir son nom
                //oeuvre.setNomCategorie(categorie.getNom_cat());

                oeuvreList.add(oeuvre);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des oeuvres.", ex);
        } finally {
            // Fermeture des ressources
            try {


                if (rs != null) rs.close();
                System.out.println("88");
                if (st != null) st.close();
                System.out.println("99");
                if (con != null) con.close();
                System.out.println("00");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("vv");

        return oeuvreList;
    }
    @FXML
    void supprimerReservation(ActionEvent event) {
        Oeuvre oeuvreSelectionne = tvOeuvre.getSelectionModel().getSelectedItem();
        System.out.println("id"+oeuvreSelectionne.getStatus());

        // Vérifiez si un musée est sélectionné
        if (oeuvreSelectionne != null) {
            try {
                // Supprimez le musée en utilisant l'ID du musée sélectionné
                ServiceOeuvre sr = new ServiceOeuvre();

                sr.deleteOne(oeuvreSelectionne.getId_oeuvre());

                // Affichez un message de réussite ou faites d'autres actions nécessaires
                System.out.println("Oeuvre deleted successfully.");

                // Mettez à jour votre TableView pour refléter les changements
                showOeuvres();
            } catch (SQLException e) {
                // Gérez les exceptions SQL
                e.printStackTrace();
                // Affichez un message d'erreur à l'utilisateur si nécessaire
            }
        } else {
            // Si aucun musée n'est sélectionné, affichez un message d'erreur ou effectuez d'autres actions nécessaires
            System.out.println("No oeuvres selected.");
        }
    }
    @FXML
    public void showOeuvres(){
        ObservableList<Oeuvre> list = getOeuvre();
        tvOeuvre.setItems(list);
        coltitre.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("titre"));
        coldescription.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Float,Oeuvre>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("date"));
        colstatus.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("Status"));
        colNomCat.setCellValueFactory(new PropertyValueFactory<Integer,Oeuvre>("id_cat"));
    }


    /*@FXML
    void ModifierOeuvre(ActionEvent event) {
        try {
            Oeuvre oeuvreSelectionne = tvOeuvre.getSelectionModel().getSelectedItem();
            if (oeuvreSelectionne != null) {
                // Assuming tfDateModification, tfNouveauPrix, and tfNouveauStatut are fields representing data inputs


                // Assuming musee is a variable containing information about a museum
                // Assuming musee.getId_musee() returns the id of the museum
                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();

                // Modify the selected Oeuvre object with the updated information
                oeuvreSelectionne.setDate(String.(coldate.toString()); // Assuming date is a string in the format "YYYY-MM-DD"
                oeuvreSelectionne.setPrix(Float.(colprix.)); // Assuming tfNouveauPrix represents the new price
                oeuvreSelectionne.setDescription(coldescription.getText());
                oeuvreSelectionne.setTitre(coltitre.getText());
                oeuvreSelectionne.setNom_cat(colNomCat.getText());
                oeuvreSelectionne.setLienImg(colLienImg.getText());
                // Assuming tfNouveauStatut represents the new status

                // Assuming ServiceOeuvre.modifyOne() method signature takes arguments like (int id_oeuvre, String date, float prix, String status)
                serviceOeuvre.updateOne(oeuvreSelectionne);

                // Refresh the UI or update the table view if necessary
                // showOeuvres();

                System.out.println("Oeuvre updated successfully.");
            } else {
                System.out.println("No oeuvre selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the oeuvre. Please try again later.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid number for the price.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the oeuvre.");
        }
    }*/
    @FXML
    void ModifierOeuvre(ActionEvent event) {
        try {
            // Get the selected Oeuvre object from the table view
            Oeuvre oeuvreSelectionne = tvOeuvre.getSelectionModel().getSelectedItem();
            if (oeuvreSelectionne != null) {
                // Modify the selected Oeuvre object with the updated information
                oeuvreSelectionne.setDate(coldate.getText()); // Assuming coldate represents the date field
                oeuvreSelectionne.setDescription(coldescription.getText()); // Assuming coldescription represents the description field
                oeuvreSelectionne.setTitre(coltitre.getText()); // Assuming coltitre represents the titre field
                oeuvreSelectionne.setNom_cat(colNomCat.getText()); // Assuming colNomCat represents the nom_cat field
                oeuvreSelectionne.setLienImg(colLienImg.getText()); // Assuming colLienImg represents the lienImg field
                oeuvreSelectionne.setStatus(colstatus.getText()); // Assuming colstatus represents the status field

                // Validate and set the price
                String prixText = colprix.getText(); // Assuming colprix represents the price field
                System.out.println("Input price text: " + prixText); // Debugging statement
                if (isValidFloat(prixText)) {
                    oeuvreSelectionne.setPrix(Float.parseFloat(prixText));
                } else {
                    System.out.println("Invalid price format. Please enter a valid price.");
                    return; // Stop execution if price is invalid
                }

                // Instantiate the service class for Oeuvre
                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();

                // Update the selected Oeuvre object in the database
                serviceOeuvre.updateOne(oeuvreSelectionne);

                // Display a success message
                System.out.println("Oeuvre updated successfully.");
            } else {
                System.out.println("No oeuvre selected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the oeuvre. Please try again later.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter valid values for the fields.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while updating the oeuvre.");
        }
    }

    // Method to validate if the entered string represents a valid float number
    private boolean isValidFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
//            if (oeuvreSelectionne != null) {
//                // Assuming colDate, colPrix, colDescription, colTitre, colNomCat, and colLienImg are TableColumn objects representing data fields
//                // Modify the selected Oeuvre object with the updated information
//                oeuvreSelectionne.setDate(coldate.getText());
//                oeuvreSelectionne.setDescription(coldescription.getText());
//                oeuvreSelectionne.setTitre(coltitre.getText());
//                oeuvreSelectionne.setNom_cat(colNomCat.getText());
//                oeuvreSelectionne.setLienImg(colLienImg.getText());
//                // Assuming colNouveauStatut represents the new status field
//                oeuvreSelectionne.setStatus(colstatus.getText());
//                System.out.println("aa");
//                // Validate the entered price
//                String prixText = colprix.getText();
//                if (isValidInteger(prixText)) {
//                    oeuvreSelectionne.setPrix(Integer.parseInt(prixText));
//
//                    // Instantiate the service class for Oeuvre
//                    ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
//
//                    // Update the selected Oeuvre object in the database
//                    serviceOeuvre.updateOne(oeuvreSelectionne);
//
//                    // Display a success message
//                    System.out.println("Oeuvre updated successfully.");}
//                 else {
//                    System.out.println("Invalid price format. Please enter a valid price.");
//                }
//            } else {
//                System.out.println("No oeuvre selected.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("An error occurred while updating the oeuvre. Please try again later.");
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid number format. Please enter a valid number for the price.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("An error occurred while updating the oeuvre.");
//        }
//    }
//
//    // Method to validate if the entered string represents a valid integer number
//    private boolean isValidInteger(String str) {
//        try {
//            Integer.parseInt(str);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }

    // Method to validate if a string represents a valid float

    @FXML
    void initialize() {
        assert btnSupprimer != null : "fx:id=\"btnSupprimer\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert colLienImg != null : "fx:id=\"colLienImg\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert colNomCat != null : "fx:id=\"colNomCat\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert colNomUser != null : "fx:id=\"colNomUser\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert coldate != null : "fx:id=\"coldate\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert coldescription != null : "fx:id=\"coldescription\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert colprix != null : "fx:id=\"colprix\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert colstatus != null : "fx:id=\"colstatus\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert coltitre != null : "fx:id=\"coltitre\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";
        assert tvOeuvre != null : "fx:id=\"tvOeuvre\" was not injected: check your FXML file 'OeuvreBackFXML.fxml'.";

        showOeuvres();
    }



}
