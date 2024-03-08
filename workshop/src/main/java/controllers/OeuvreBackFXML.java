package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.GeneratePdf;
import models.Oeuvre;
import services.ServiceCategorie;
import services.ServiceOeuvre;
import utils.DBConnection;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OeuvreBackFXML {
    FileChooser fileChooser= new FileChooser();


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
    @FXML
    private TextField searchField;
    @FXML
    private Button Btnimage;
    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfNomCat;

    @FXML
    private TextField tfNomUser;
    @FXML
    private Button btnPDF;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfStatus;

    @FXML
    private TextField tfTitre;

    String selectedImagePath;

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
    private void choisirImage(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedImagePath = file.getAbsolutePath();
            System.out.println("Chemin de l'image : " + selectedImagePath);
        }
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
                showOeuvres1();
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
            System.out.println("salemou yg");
            if (oeuvreSelectionne != null) {
                System.out.println("salemou 3alkom");

                // Modify the selected Oeuvre object with the updated information
                oeuvreSelectionne.setDate(coldate.getText()); // Assuming coldate represents the date field
                oeuvreSelectionne.setDescription(coldescription.getText()); // Assuming coldescription represents the description field
                oeuvreSelectionne.setTitre(coltitre.getText()); // Assuming coltitre represents the titre field
                oeuvreSelectionne.setNom_cat(colNomCat.getText()); // Assuming colNomCat represents the nom_cat field
                oeuvreSelectionne.setLienImg(colLienImg.getText()); // Assuming colLienImg represents the lienImg field
                oeuvreSelectionne.setStatus(colstatus.getText()); // Assuming colstatus represents the status field
                String catIdAsString = colNomCat.getText();
                int catId = Integer.parseInt(catIdAsString);
                oeuvreSelectionne.setId_cat(catId);
                String userIdAsString = colNomUser.getText();
                int userId = Integer.parseInt(userIdAsString);
                oeuvreSelectionne.setUser(userId);
                System.out.println("salemou uig");
                // Validate and set the price
                String prixString = colprix.getText(); // Assuming prixTextField.getText() returns the string representation of the prix

                try {
                    // Parse the string to obtain a Float object
                    float prix = Float.parseFloat(prixString);

                    // Use the Float object to set the prix in your oeuvreSelectionne object
                    oeuvreSelectionne.setPrix(prix);
                } catch (NumberFormatException e) {
                    // Handle parsing errors if the string does not represent a valid float
                    e.printStackTrace(); // Or display an error message to the user
                }

                // Instantiate the service class for Oeuvre
                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();

                // Update the selected Oeuvre object in the database
                serviceOeuvre.updateOne1(oeuvreSelectionne.getId_oeuvre(),oeuvreSelectionne,selectedImagePath);
                showOeuvres1();

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
    public void showOeuvres1(){
        ObservableList<Oeuvre> list = getOeuvre();
        tvOeuvre.setItems(list);
        tvOeuvre.setItems(list);
        coltitre.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("titre"));
        coldescription.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Float,Oeuvre>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("date"));
        colstatus.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("Status"));
        ServiceCategorie sc = new ServiceCategorie();
        colNomCat.setCellValueFactory(new PropertyValueFactory<Integer,Oeuvre>("id_cat"));
        FilteredList<Oeuvre> filteredData = new FilteredList<>(list, b-> true);
        searchField.textProperty().addListener((observable , oldValue, newValue) -> {
            filteredData.setPredicate(Oeuvre -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(Oeuvre.getTitre().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }
                else if(Oeuvre.getStatus().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }
                else if (String.valueOf(Oeuvre.getId_oeuvre()).toLowerCase().contains(searchKeyword.toLowerCase())) {                    return true;


                }else
                    return false;
            });
        });
        SortedList<Oeuvre> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvOeuvre.comparatorProperty());
        tvOeuvre.setItems(sortedData);
    }
    @FXML
    void getData(MouseEvent event) {
        Oeuvre oeuvre = tvOeuvre.getSelectionModel().getSelectedItem();
        if (oeuvre != null) {
            ServiceCategorie sc = new ServiceCategorie();
            tfTitre.setText(oeuvre.getTitre());
            tfDescription.setText(oeuvre.getDescription());
            float prix = oeuvre.getPrix();
            String prixAsString = String.valueOf(prix);
            tfPrix.setText(prixAsString);
            tfDate.setText(oeuvre.getDate());
            tfStatus.setText(oeuvre.getStatus());
            int idCat = oeuvre.getId_cat();
            String idCatAsString = String.valueOf(idCat);
            tfNomCat.setText(idCatAsString);
            int userId = oeuvre.getUser();
            String userIdAsString = String.valueOf(userId);
            tfNomUser.setText(userIdAsString);            // Mettre à jour le chemin de l'image
            selectedImagePath = oeuvre.getLienImg();

        }
    }
    @FXML
    void generatePDF(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF File");
            fileChooser.setInitialFileName("liste_oeuvres.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            File file = fileChooser.showSaveDialog(btnPDF.getScene().getWindow());

            if (file != null) {
                ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
                List<Oeuvre> oeuvresList = serviceOeuvre.getAllOeuvres();
                ObservableList<Oeuvre> oeuvres = FXCollections.observableArrayList(oeuvresList);

                if (!oeuvres.isEmpty()) {
                    GeneratePdf.generatePDF(oeuvres, file);
                    System.out.println("PDF generated successfully.");
                } else {
                    System.out.println("No data to generate PDF.");
                }
            } else {
                System.out.println("No file selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while generating PDF.");
        }
    }

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\HP\\IdeaProjects\\workshop\\src\\main\\resources\\img"));
        showOeuvres1();
    }



}
