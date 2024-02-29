package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.commande;
import services.serviceCommande;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ajoutercommande2 implements Initializable {
    @FXML
    private Label cardVeri;

    @FXML
    private ImageView imgVeri;

    @FXML
    private Label nameVeri;

    @FXML
    private Label priceVeri;

    @FXML
    private Button orderter;
    public static Label StaticNameVeri;
    public static Label StaticPriceVeri;
    public static Label StaticCardVeri;
    public static ImageView StaticimgVeri;

    @FXML
    void addCommande2(ActionEvent event) {

        String priceText = priceVeri.getText();
        if (priceText.length() > 1) {
            priceText = priceText.substring(1); // Remove the first character
        }
        System.out.println(priceText);
        float priceFloat = Float.parseFloat(priceText);





       try {

           commande p = new commande(priceFloat, nameVeri.getText(),cardVeri.getText(),2);

            serviceCommande sp = new serviceCommande();
            sp.insertOne(p);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success");
           alert.setContentText("Data inserted successfully!");
           alert.showAndWait();

           Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
           Stage window =(Stage) orderter.getScene().getWindow();
           window.setScene(new Scene(root,1315,800));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        } catch (IOException e) {
           throw new RuntimeException(e);
       }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StaticNameVeri=nameVeri;
        StaticPriceVeri=priceVeri;
        StaticCardVeri=cardVeri;
        StaticimgVeri=imgVeri;

    }
}
