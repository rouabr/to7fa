package tn.esprit;
import controllers.translator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.panierat;
import services.servicePanier;
import utils.DBconnection;

import java.sql.SQLException;


public class fxMain extends Application {
    public  static  final String CURRENCY="$";
    public  static  final String Article="Article ID : ";
    double x,y = 0;
    public static void main(String[] args)
    {

        DBconnection cn1 = DBconnection.getInstance();



        servicePanier sp = new servicePanier();


        try {
            // Call the selectAll method to get all basket items

            // Display basket items
            for (panierat item : sp.selectAll()) {
                System.out.println("ID: " + item.getId_panier());

                System.out.println("User ID: " + item.getId_user());
                System.out.println("Art Piece ID: " + item.getId_oeuvre());
                System.out.println("---------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("/panier.fxml"));
       Parent parent = loader.load();
        File css = new File("style.css");
        Scene scene = new Scene(parent);
        stage.setTitle("ajouter une commande");
        stage.setScene(scene);
        stage.show();*/
   // Parent root = FXMLLoader.load(getClass().getResource("/views/panier.fxml"));
     Parent root = FXMLLoader.load(getClass().getResource("/backviews/back.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("panier");
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });


        stage.setScene(new Scene(root));
      stage.show();
        ////////////////////////////////////////////////////////////////////////////////////////













    }
}
