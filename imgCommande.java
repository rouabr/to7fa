package controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.oeuvre;
import tn.esprit.MyListener;
import tn.esprit.fxMain;

public class imgCommande {
    @FXML
    private ImageView imgCommande;

    private oeuvre oeuv;
    public void setData(oeuvre oev) {

        this.oeuv = oev;

        Image image = new Image(getClass().getResourceAsStream(oev.getImgSrc()));

        imgCommande.setImage(image);
    }

}
