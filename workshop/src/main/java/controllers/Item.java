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
    private ImageView imageItem;

    @FXML
    private Label prixItem;

    @FXML
    private Label statusItem;
    public void setData(Oeuvre oev) {

        this.oeuv = oev;
        System.out.println("setrdyfu");
        idItem.setText(Integer.toString(oev.getId_oeuvre()));
        System.out.println("this id"+oev.getId_oeuvre());
        prixItem.setText(Float.toString(oev.getPrix()));
        statusItem.setText(oev.getStatus());
        dateItem.setText(oev.getDate());
        System.out.println("zefg");
        //prixLabel.setText(fxMain.CURRENCY + oev.getPrice());

      /*  Image image = new Image(getClass().getResourceAsStream(oev.getLienImg()));

        imageItem.setImage(image);
        System.out.println("zegtryu");*/
    }
}
