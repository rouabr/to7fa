package controllers;

import com.google.protobuf.compiler.PluginProtos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Musee;
import javafx.geometry.Pos;
import models.User;
import services.ServiceMusee;
import services.ServiceUser;
import utils.DBConnection;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    public ReservationFXML() {
        serviceMusee = new ServiceMusee();
    }

    @FXML
    public void initialize() {
        ObservableList<Musee> musees = getMusees();
        afficherMusees(musees);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
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
            museeBox.setPrefSize(200, 210); // Set the preferred size of the VBox

            // Create a Label to display the museum's name
            Label nomLabel = new Label("Le musée de " + musee.getNom_musee());
            nomLabel.setStyle("-fx-font-size: 11pt; -fx-font-weight: bold;"); // Set style for the label
            museeBox.getChildren().add(nomLabel); // Add the name Label to the VBox


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
                    // Center the image horizontally
                    imageView.setStyle("-fx-border-radius: 40; -fx-background-radius: 40; -fx-shape: \"M180,90 L180,90 L180,90 C180,138.666667 138.666667,180 90,180 C41.3333333,180 0,138.666667 0,90 C0,41.3333333 41.3333333,0 90,0 C138.666667,0 180,41.3333333 180,90 Z\";");

                    imageView.setEffect(new DropShadow());
                    museeBox.getChildren().add(imageView); // Add the ImageView to the VBox
                } else {
                    // Handle the case where the image file does not exist
                    System.out.println("Image file does not exist at: " + imagePath);
                }
            } else {
                // Handle the case where the image path is null
                System.out.println("Image path is null for musee: " + musee.getNom_musee());
            }

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
    @FXML
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
}




