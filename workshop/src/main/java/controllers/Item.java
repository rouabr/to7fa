package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import models.Oeuvre;
import javafx.scene.image.Image;

public class Item {
    private Oeuvre oeuv;
    @FXML
    private Label dateItem;

    @FXML
    private Label idItem;

    @FXML
    private Label titreItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private Label prixItem;

    @FXML
    private Label statusItem;
    public void setData(Oeuvre oev) {
        this.oeuv = oev;
        idItem.setText(Integer.toString(oev.getId_oeuvre()));
        titreItem.setText(oev.getTitre());
        prixItem.setText(Float.toString(oev.getPrix()));
        statusItem.setText(oev.getStatus());
        dateItem.setText(oev.getDate());

        String imageUrl = oev.getLienImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageItem.setImage(image);
            } catch (Exception e) {
                // Handle the exception (e.g., log it, show an error message)
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                // You might want to set a default image here
                // Image image = new Image("default_image.png");
                // imageItem.setImage(image);
            }
        } else {
            // Si l'URL de l'image est null ou vide, vous pouvez définir une image par défaut ici
            // Image image = new Image("default_image.png");
            // imageItem.setImage(image);
        }
        System.out.println("Titre: " + oev.getTitre());
    }

}
