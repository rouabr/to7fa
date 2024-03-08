package controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.Oeuvre;
import services.ServiceOeuvre;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Acceuil extends Application implements Initializable {
    private List<Oeuvre> ouevs = new ArrayList<>();

    @FXML
    private Button Categorie;

    @FXML
    private ChoiceBox<String> Sort;

    @FXML
    private GridPane grid;

    @FXML
    private Button myprofile;

    @FXML
    private Button rechercher;

    @FXML
    private TextField search;


    @FXML
    void myprofile(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UserOeuvre.fxml"));
        Stage window =(Stage) myprofile.getScene().getWindow();
        window.setScene(new Scene(root));

    }

    @FXML
    void userOeuvre(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserOeuvre.fxml"));
        Parent root = loader.load();

        UserOeuvre controller = loader.getController();

    }
    @Override
    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("Acceuil.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private List<Oeuvre> getData() {
        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.selectAll()) {

                ouev = new Oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setTitre(item.getTitre());
                ouev.setDate(item.getDate());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());
                ouev.setLienImg(item.getLienImg());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }

    @FXML
    void search2(KeyEvent event) {
        grid.getChildren().clear(); // Nettoyer la grille

        ouevs.addAll(getData2());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                Item itemController = fxmlLoader.getController();
                itemController.setData(ouevs.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane,new Insets(20));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Oeuvre> getData2() {
        grid.getChildren().clear(); // Nettoyer la grille

        ServiceOeuvre sp = new ServiceOeuvre();
        List<Oeuvre> ouevs = new ArrayList<>();
        Oeuvre ouev;
        try {
            for (Oeuvre item : sp.searchByTitre(search.getText())) {
                 System.out.println("oo");
                ouev = new Oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setTitre(item.getTitre());
                ouev.setDate(item.getDate());
                ouev.setPrix(item.getPrix());
                ouev.setStatus(item.getStatus());
                ouev.setLienImg(item.getLienImg());



                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ouevs;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ouevs.addAll(getData());
        Sort.setItems(FXCollections.observableArrayList(
               "disponible","indisponible"
        ));
        Sort.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Appliquer le filtre par ville
                onSortSelected(newValue);
            }
        });
        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                Item itemController = fxmlLoader.getController();
                itemController.setData(ouevs.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void afficherResultats(List<Oeuvre> listeOeuvres) {
        grid.getChildren().clear(); // Nettoyer la grille

        int column = 0;
        int row = 1;
        for (Oeuvre oeuvre : listeOeuvres) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
            try {
                AnchorPane anchorPane = fxmlLoader.load();
                Item itemController = fxmlLoader.getController();
                itemController.setData(oeuvre);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void search(KeyEvent event) {
        System.out.println("aa");
        ouevs.addAll(getData2());
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                Item itemController = fxmlLoader.getController();
                itemController.setData(ouevs.get(i));
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void RechercherOeuvre(ActionEvent event) {
        String nomRecherche = search.getText().trim(); // Récupérer le texte saisi dans le champ de recherche
        if (!nomRecherche.isEmpty()) { // Vérifier si le champ de recherche n'est pas vide
            try {
                ServiceOeuvre service = new ServiceOeuvre();
                List<Oeuvre> sallelist = service.searchByTitre(nomRecherche); // Appeler une méthode de service pour rechercher par nom

                // Vérifier si la liste des cabines est vide
                if (sallelist.isEmpty()) {
                    // Afficher une alerte indiquant que la cabine n'existe pas
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Oeuvre non trouvée");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucune Oeuvre ne correspond au nom saisi.");
                    alert.show();
                } else {
                    // Afficher les résultats de la recherche dans la grille
                    afficherResultats(sallelist);
                }
            } catch (SQLException e) {
                // Gérer les erreurs de manière appropriée
                e.printStackTrace(); // Print the stack trace of the exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la recherche");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur est survenue lors de la recherche. Veuillez réessayer plus tard.");
                alert.show();
            }
        } else {
            // Si le champ de recherche est vide, afficher un message à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champ de recherche vide");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir le nom de la cabine à rechercher.");
            alert.show();
        }
    }
    private void onSortSelected(String status) {
        ObservableList<Oeuvre> filteredOeuvres = FXCollections.observableArrayList();

        // Iterate over the original list and filter based on the status
        for (Oeuvre oeuvre : ouevs) {
            if (oeuvre.getStatus().equalsIgnoreCase(status)) {
                filteredOeuvres.add(oeuvre); // Add the matching oeuvre to the filtered list
            }
        }

        // Update the UI with the filtered list of oeuvres
        afficherResultats(filteredOeuvres);
    }
    /*@FXML
    private void onSortSelected(ActionEvent event) {
        ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
        int selectedIndex = Sort.getSelectionModel().getSelectedIndex();

        // Optionally, you can preselect an item
        if (selectedIndex == -1) {
            Sort.getSelectionModel().select(0); // Preselect the first item if no item is selected
        }

        try {
            // Fetch the original list of Oeuvre objects from the database or another data source
            List<Oeuvre> originalOeuvresList = serviceOeuvre.selectAll();

            // Now sort the list based on the selected index
            List<Oeuvre> sortedResults;
            switch (selectedIndex) {
                case 0:
                    sortedResults = serviceOeuvre.triDesc(originalOeuvresList, 0);
                    break;
                case 1:
                    sortedResults = serviceOeuvre.triAsc(originalOeuvresList, 1);
                    break;
                default:
                    // Handle other sorting options if needed
                    return;
            }

            // Load sorted results into the grid
            grid.getChildren().clear(); // Clear existing content

            int column = 0;
            int row = 1;
            for (Oeuvre oeuvre : sortedResults) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                Item itemController = fxmlLoader.getController();
                itemController.setData(oeuvre);

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
            }
        } catch (SQLException | IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while sorting artwork.");
            e.printStackTrace();
        }
    }*/
//@FXML
//void RechercherOeuvre(ActionEvent event) {
//    String searchTerm = Rechercher.getText().trim();
//    if (!searchTerm.isEmpty()) {
//        try {
//            ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
//            List<Oeuvre> searchResults = serviceOeuvre.searchByTitre(Integer.parseInt(searchTerm));
//            // Process search results
//            for (Oeuvre oeuvre : searchResults) {
//                // Display the artwork in the grid or handle it as needed
//            }
//        } catch (NumberFormatException e) {
//            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid integer for the ID.");
//            e.printStackTrace();
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while searching for artwork.");
//            e.printStackTrace();
//        }
//    } else {
//        showAlert(Alert.AlertType.WARNING, "Empty Search", "Please enter a search term.");
//    }
//}
//    @FXML
//    private void onSortSelected(ActionEvent event) {
//        ServiceOeuvre sO = new ServiceOeuvre();
//        int i = Sort.getSelectionModel().getSelectedIndex();
//        System.out.println(i);
//        switch(i){
//            case 0:
//                this.events =  sO.triDesc(0);
//                loadEvents(this.events);
//                break;
//            case 1:
//                this.events =  SE.triAsc(1);
//                loadEvents(this.events);
//                break;
//
//
//        }
//    }
//@FXML
//private void onSortSelected(ActionEvent event) {
//    ServiceOeuvre serviceOeuvre = new ServiceOeuvre();
//    int selectedIndex = Sort.getSelectionModel().getSelectedIndex();
//
//    // Optionally, you can preselect an item
//    if (selectedIndex == -1) {
//        Sort.getSelectionModel().select(0); // Preselect the first item if no item is selected
//    }
//
//    try {
//        // Fetch the original list of Oeuvre objects from the database or another data source
//        List<Oeuvre> originalOeuvresList = serviceOeuvre.selectAll();
//
//        // Now sort the list based on the selected index
//        List<Oeuvre> sortedResults;
//        switch (selectedIndex) {
//            case 0:
//                sortedResults = serviceOeuvre.triDesc(originalOeuvresList, 0);
//                break;
//            case 1:
//                sortedResults = serviceOeuvre.triAsc(originalOeuvresList, 1);
//                break;
//            default:
//                // Handle other sorting options if needed
//                return;
//        }
//
//        // Load sorted results into the grid
//        grid.getChildren().clear(); // Clear existing content
//
//        int column = 0;
//        int row = 1;
//        for (Oeuvre oeuvre : sortedResults) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
//            AnchorPane anchorPane = fxmlLoader.load();
//            Item itemController = fxmlLoader.getController();
//            itemController.setData(oeuvre);
//
//            if (column == 3) {
//                column = 0;
//                row++;
//            }
//            grid.add(anchorPane, column++, row); //(child,column,row)
//        }
//    } catch (SQLException | IOException e) {
//        showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while sorting artwork.");
//        e.printStackTrace();
//    }
//}

}
