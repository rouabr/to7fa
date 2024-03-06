package controllers;

import com.google.protobuf.compiler.PluginProtos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Musee;
import javafx.geometry.Pos;
import models.User;
import services.ServiceMusee;
import services.ServiceUser;
import utils.DBConnection;

//import java.awt.event.ActionEvent;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReservationFXML{


    @FXML
    private FlowPane museeContainer;
    @FXML
    private Button reserverbtn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchfield;
    @FXML
    private Button weatherBtn;
    @FXML
    private VBox museevbox;
    @FXML
    private Label detailsLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private ImageView imageView;
    @FXML
    private Label nameLabel;
    private int selectedMuseeId;
    private ServiceMusee serviceMusee;
    private ObservableList<Musee> museesList;
    @FXML
    private ChoiceBox<String> sortChoiceBox;
    @FXML
    private Button defaultbtn;
    @FXML
    private Button trierbtn;

    public ReservationFXML() {
        serviceMusee = new ServiceMusee();
    }

    @FXML
    public void initialize() {
        museesList = getMusees(); // Assign the list of all museums to museesList
        afficherMusees(museesList);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                filterMusees(newValue);
            } else {
                afficherMusees(museesList); // If the search field is empty, display all museums
            }
        });

        sortChoiceBox.setItems(FXCollections.observableArrayList(
                "Jendouba", "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Kairouan",
                "Kasserine", "Kebili", "La Manouba", "Le Kef", "Mahdia", "Médenine", "Monastir",
                "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine", "Tozeur",
                "Tunis", "Zaghouan"
        ));
        sortChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Appliquer le filtre par ville
                filtrerMuseesParVilles(newValue);
            }
        });
    }
    @FXML
    void resetToDefault(ActionEvent event) {
        // Réinitialiser l'affichage pour montrer tous les musées
        afficherMusees(museesList);
    }

    private void filtrerMuseesParVilles(String villeSelectionnee) {
        // Créer une liste temporaire pour stocker les musées filtrés par ville
        ObservableList<Musee> museesFiltres = FXCollections.observableArrayList();

        // Parcourir la liste complète des musées
        for (Musee musee : museesList) {
            // Vérifier si le musée se trouve dans la ville sélectionnée
            if (musee.getVille().equalsIgnoreCase(villeSelectionnee)) {
                // Ajouter le musée à la liste des musées filtrés
                museesFiltres.add(musee);
            }
        }

        // Afficher les musées filtrés par ville
        afficherMusees(museesFiltres);
    }

    @FXML
    void trierParNom() {
        // Triez les musées par nom en ignorant la casse
        FXCollections.sort(museesList, Comparator.comparing(Musee::getNom_musee, String.CASE_INSENSITIVE_ORDER));
        // Affichez les musées triés
        afficherMusees(museesList);
    }

    private void trierParVille() {
        // Implémentez le tri par ville ici
    }
    private void filterMusees(String keyword) {
        System.out.println("filtering");
        // Créer une liste temporaire pour stocker les musées filtrés
        ObservableList<Musee> filteredList = FXCollections.observableArrayList();

        // Parcourir la liste complète des musées et ajouter ceux correspondant au critère de recherche à la liste filtrée
        for (Musee musee : museesList) {
            if (musee.getNom_musee().toLowerCase().contains(keyword.toLowerCase()) || musee.getVille().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(musee);
            }
        }

        // Afficher les musées filtrés
        afficherMusees(filteredList);
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
                musee.setImage_path(rs.getString("image_musee"));
                musees.add(musee);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des musées.", e);
        } finally {
            // Fermeture des ressources JDBC (ResultSet, PreparedStatement, Connection)
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return musees;
    }
    @FXML
    public void afficherMusees(ObservableList<Musee> musees) {
        museeContainer.getChildren().clear(); // Clear the previous content

        for (Musee musee : musees) {
            VBox museeBox = new VBox(); // Use a VBox to display the name, id, and image vertically
            museeBox.setSpacing(10); // Add spacing between elements
            museeBox.setPrefSize(200, 200); // Set the preferred size of the VBox, slightly smaller than the image

            // Create a Label to display the museum's name
            Label nomLabel = new Label("Le musée de " + musee.getNom_musee());
            nomLabel.setStyle("-fx-font-size: 11pt; -fx-font-weight: bold;"); // Set style for the label

            // Create a StackPane to hold the ImageView
            StackPane imagePane = new StackPane();

            // Create a Region to wrap the ImageView
            Region imageRegion = new Region();
            imageRegion.setPrefSize(180, 180); // Set the size of the Region to match the ImageView
            imageRegion.setStyle("-fx-background-radius: 10px; -fx-background-color: transparent;"); // Apply border radius to make the corners curvy

            // Create an ImageView to display the museum's image
            ImageView imageView = new ImageView();
            imageView.setFitWidth(180); // Set the width of the ImageView
            imageView.setFitHeight(180); // Set the height of the ImageView
            imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image

            // Load the image if the path is valid
            String imagePath = musee.getImage_path();
            if (imagePath != null) {
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    imageView.setImage(image); // Load the image into the ImageView
                    imagePane.getChildren().addAll(imageRegion, imageView); // Add the Region and ImageView to the StackPane
                    museeBox.getChildren().add(imagePane); // Add the StackPane to the VBox
                } else {
                    // Handle the case where the image file does not exist
                    System.out.println("Image file does not exist at: " + imagePath);
                }
            } else {
                // Handle the case where the image path is null
                System.out.println("Image path is null for musee: " + musee.getNom_musee());
            }

            museeBox.getChildren().add(nomLabel); // Add the name Label to the VBox

            // Set alignment for the VBox to center the contents
            museeBox.setAlignment(Pos.CENTER);

            // Set style for the VBox
            museeBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px;");

            // Add event handler to handle mouse click event on museeBox
            museeBox.setUserData(musee);
            museeBox.setOnMouseClicked(this::obtenirIdMuseeSelectionne);
            museeContainer.getChildren().add(museeBox);
            museeBox.setCursor(Cursor.HAND);
        }
    }




    public void obtenirIdMuseeSelectionne(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof VBox) {
            VBox museeBoxClicked = (VBox) mouseEvent.getSource();
            Musee musee = (Musee) museeBoxClicked.getUserData();

            if (musee != null) {
                selectedMuseeId = musee.getId_musee();
                System.out.println(selectedMuseeId);
                int idUser = 1;
                String nomMusee = musee.getNom_musee();
                String descriptionMusee = musee.getDescription();
                String imagePath = musee.getImage_path();
                String location = musee.getVille();
                cityLabel.setText("•Situé à "+location);
                nameLabel.setText("Le musée de " + nomMusee );
                nameLabel.setWrapText(true);
                detailsLabel.setText(descriptionMusee);
                detailsLabel.setWrapText(true);
                cityLabel.setAlignment(Pos.CENTER);
                nameLabel.setAlignment(Pos.CENTER);
                detailsLabel.setAlignment(Pos.CENTER);


                if (imagePath != null) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        Image image = new Image(imageFile.toURI().toString());
                        imageView.setImage(image);
                    } else {
                        System.out.println("Image file does not exist at: " + imagePath);
                    }
                } else {
                    System.out.println("Image path is null for musee: " + nomMusee);
                }
            } else {
                System.out.println("Aucun musée sélectionné.");
            }
        }
    }
    /*@FXML
    public void onReserverButtonClick(MouseEvent event) {
        System.out.println("onReserverButtonClick called");
        System.out.print(selectedMuseeId);
        if (selectedMuseeId != 0) {
            int idUser = 1;
            try {
                int availableTickets = serviceMusee.getAvailableTickets(selectedMuseeId);
                if (availableTickets == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Tickets Available");
                    alert.setHeaderText(null);
                    alert.setContentText("No tickets are available for this museum.");
                    alert.showAndWait();
                    return;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReservationFrontFXML.fxml"));
                Parent root = loader.load();

                ReservationFrontFXML controller = loader.getController();
                controller.setMaxTickets(availableTickets);
                controller.setData(selectedMuseeId,idUser);
                Stage stage = new Stage();  stage.setScene(new Scene(root));
                stage.show();
                stage.setTitle("Make reservation");

                stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun musée sélectionné.");
        }
    }
*/
    public void onReserverButtonClick(MouseEvent event) {
        System.out.println("onReserverButtonClick called");
        System.out.print(selectedMuseeId);
        if (selectedMuseeId != 0) {
            int idUser = 1;
            try {
                int availableTickets = serviceMusee.getAvailableTickets(selectedMuseeId);
                if (availableTickets == 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("No Tickets Available");
                    alert.setHeaderText(null);
                    alert.setContentText("No tickets are available for this museum.");
                    alert.showAndWait();
                    return;
                }

                // Load the CaptchaPage.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CaptchaFXML.fxml"));
                Parent root = loader.load();
                CaptchaFXML controller = loader.getController();
                controller.setMaxTickets(availableTickets);
                controller.setData(selectedMuseeId,idUser);
                // Pass necessary data to the CaptchaFXML controller if needed

                // Show the CaptchaPage.fxml in a new stage
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setTitle("Captcha Verification");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Aucun musée sélectionné.");
        }
    }
    @FXML
    void onWeatherClicked(MouseEvent event) {
        System.out.println("onReserverButtonClick called");
        ServiceMusee sm = new ServiceMusee();
        String city = sm.getCity(selectedMuseeId);
        System.out.println(city);
        if (city.isEmpty()) {
            System.out.println("Aucun musée sélectionné.");
        } else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/weatherFXML.fxml"));
                Parent root = loader.load();

                WeatherFXML controller = loader.getController();
                controller.setData1(city);
                Scene scene = new Scene(root);

                // Load the custom-shaped image for the stage
                Image image = new Image(getClass().getResourceAsStream("/assets/transparent.png"));
                scene.setFill(new ImagePattern(image));

                Stage stage = new Stage();
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT); // Make the stage transparent
                stage.setScene(scene);
                stage.show();
                stage.setTitle("Weather");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/to7fa-removebg-preview.png")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




