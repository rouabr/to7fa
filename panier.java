package controllers;


import javafx.scene.control.*;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import tn.esprit.SceneConroller;
import tn.esprit.fxMain;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.oeuvre;
import services.servicePanier;
import tn.esprit.MyListener;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;

import static controllers.ajoutercommande2.StaticimgVeri;


public class panier extends Ajoutercommande implements Initializable {


    @FXML
    private ImageView artImgLab;

    @FXML
    private Label artNameLab;

    @FXML
    private Label artPrixLab;

    @FXML
    private VBox chosenartcard;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    private Button handScenetwo;
    @FXML
    private AnchorPane scene1;
    @FXML
    private Label artNameLab11;

    @FXML
    private Button gotoAdd;
    @FXML
    private Button reloadbtn;
    @FXML
    private TextField searchbtn;




    @FXML
    void switchToScene2(ActionEvent event)throws IOException {
        ouevs.clear();
        ouevs.addAll(getData());
        if(ouevs.size()==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Basket Alert");
            alert.setHeaderText(null);
            alert.setContentText("Please select an item.");
            alert.showAndWait();

        } else {
            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutercommande.fxml"));
            Stage window = (Stage) handScenetwo.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
            StaticName.setText(artNameLab.getText());
            StaticPrice.setText(artPrixLab.getText());
            StaticTotal.setText(artPrixLab.getText());
            artPrixLab.setText("");
            //  StaticimgVeri.setImage(image);

        }

    }

    private MyListener myListener;
    @FXML
    private HBox hboxx;
    private List<oeuvre> ouevs = new ArrayList<>();
    private Image image;
    private oeuvre chosenOeuvre;
    // private MyListener myListener;

    private List<oeuvre> getData() {

        servicePanier sp = new servicePanier();
        List<oeuvre> ouevs = new ArrayList<>();
        oeuvre ouev;

        try {
            for (oeuvre item : sp.selectAlloeuvre()) {

                ouev = new oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setName(item.getName());
                ouev.setPrice(item.getPrice());
                ouev.setImgSrc(item.getImgSrc());
                ouev.setColor("ba8f7c");
                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ouevs;
    }
    private List<oeuvre> getDataselected() {

        servicePanier sp = new servicePanier();
        List<oeuvre> ouevs = new ArrayList<>();
        oeuvre ouev;

        try {
            for (oeuvre item : sp.selectByname(searchbtn.getText())) {

                ouev = new oeuvre();
                ouev.setId_oeuvre(item.getId_oeuvre());
                ouev.setName(item.getName());
                ouev.setPrice(item.getPrice());
                ouev.setImgSrc(item.getImgSrc());
                ouev.setColor("ba8f7c");
                ouevs.add(ouev);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ouevs;
    }

    private void setChosenoeuvre(oeuvre ouev) {
        artNameLab.setText(ouev.getName());
        artPrixLab.setText(fxMain.CURRENCY + ouev.getPrice());
        image = new Image(getClass().getResourceAsStream(ouev.getImgSrc()));
        artImgLab.setImage(image);
        chosenartcard.setStyle("-fx-background-color: white;\n" +
                "    -fx-background-radius: 30;");
    }
    public void setChosenOeuvre(oeuvre chosenOeuvre) {
        this.chosenOeuvre = chosenOeuvre;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> possibleWordsList = new ArrayList<>();
        String[] words={"aa","nn","b"};
        ouevs.addAll(getData());


        for (oeuvre item : ouevs) {

            possibleWordsList.add(item.getName());
        }


           // TextFields.bindAutoCompletion(searchbtn,words);



        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));
                    System.out.println(oeu.getName());
                    System.out.println(oeu.getId_oeuvre());

                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                itemController itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(ouevs.get(i), myListener);
                System.out.println("lala");

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void refreshUI() {
        // Clear existing items from the grid
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();

        ouevs.addAll(getData());
        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));
                    System.out.println(oeu.getName());
                    System.out.println(oeu.getId_oeuvre());

                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                itemController itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(ouevs.get(i), myListener);
                System.out.println("lala");

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshUIselcted() {
        // Clear existing items from the grid
        grid.getChildren().clear();

        // Reload data
        ouevs.clear();

        ouevs.addAll(getDataselected());
        if (ouevs.size() > 0) {
            setChosenoeuvre(ouevs.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(oeuvre oeu) {
                    setChosenoeuvre(oeu);
                    artNameLab11.setText(Integer.toString(oeu.getId_oeuvre()));
                    System.out.println(oeu.getName());
                    System.out.println(oeu.getId_oeuvre());

                }
            };
        }


        int column = 0;
        int row = 1;
        try {

            for (int i = 0; i < ouevs.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/views/items.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                System.out.println("ii");
                itemController itemController = fxmlLoader.getController();
                System.out.println("oo");
                itemController.setData(ouevs.get(i), myListener);
                System.out.println("lala");

                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(12));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void cancel(ActionEvent event) {


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete?");

        // Show the alert and wait for user response
        confirmationAlert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    // Assuming you have a reference to your database handler or DAO class
                    servicePanier dbHandler = new servicePanier();
                    dbHandler.deleteOne(4, Integer.parseInt(artNameLab11.getText()));
                    // Optionally, you can show a success message or update your UI here
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle the exception according to your application's error handling strategy
                    // Optionally, you can show an error message to the user here
                }
            }
        });
        artNameLab11.setText("select an item !");
        artNameLab.setText("");
        Image image = new Image(getClass().getResourceAsStream("/img/brow.PNG"));
        artPrixLab.setText("");
        artImgLab.setImage(image);

        refreshUI();
    }

    @FXML
    void goToAddCommandes(ActionEvent event) throws IOException {
        ouevs.clear();
        ouevs.addAll(getData());
        if(ouevs.size()==0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Basket Alert");
            alert.setHeaderText(null);
            alert.setContentText("The basket is empty!\nPlease select an item from shop.");
            alert.showAndWait();

        } else
        {
            System.out.println("kobi");
            Parent root = FXMLLoader.load(getClass().getResource("/views/ajoutertoutlescommandes.fxml"));
            System.out.println("chwaya");
            Stage window = (Stage) gotoAdd.getScene().getWindow();
            window.setScene(new Scene(root, 1315, 800));
        }
    }
    @FXML
    void search(KeyEvent event) throws SQLException {
        refreshUIselcted();
    }
    @FXML
    void search2(KeyEvent event) {
        refreshUIselcted();
    }

    @FXML
    void reload(ActionEvent event) {
       refreshUI();
    }

    }






