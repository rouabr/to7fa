package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import models.oeuvre;
import tn.esprit.MyListener;
import tn.esprit.fxMain;

public class commandesPrix {

    @FXML
    private Label idlab;


    @FXML
    private Label namelab;

    @FXML
    private Label pricelab;

    private oeuvre oeuv;

    public  void setDataa(oeuvre oev) {
        System.out.println("3eja");
        this.oeuv = oev;
        idlab.setText(String.valueOf(fxMain.Article+oev.getId_oeuvre()));

        namelab.setText(oev.getName());
        System.out.println("mosli");
        System.out.println("this id"+oev.getId_oeuvre());

        pricelab.setText(fxMain.CURRENCY + oev.getPrice());


    }
}
