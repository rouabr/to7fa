package controllers.backCommande;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.commande;
import models.oeuvre;
import services.serviceCommande;
import services.servicePanier;
import utils.DBconnection;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;



public class dashboard implements Initializable {


    @FXML
    private TableView<commande> CommandeTable;

    @FXML
    private TableColumn<commande, Date> Datecoloun;

    @FXML
    private TableColumn<commande, Integer> IdColoun;

    @FXML
    private TableColumn<commande, Integer> IdOeuvreColoun;

    @FXML
    private TableColumn<commande, String> NameColoun;

    @FXML
    private TableColumn<commande, String> PaymentColoun;

    @FXML
    private TableColumn<commande, Float> Pricecoloun;

    ObservableList<commande> commandelist;
    int aindex=-1;
    Connection cnx;
    ResultSet rs=null;
    PreparedStatement pst=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("hello");

        try {
            // Call the selectAllTableview() method to retrieve data
            serviceCommande sp=new serviceCommande();
            ObservableList<commande> commandes = sp.selectAllTableview();
System.out.println("hello");
            // Set data to TableView
            CommandeTable.setItems(commandes);

          loaddata();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            // Handle exceptions
        }



    }


    private void loaddata() throws SQLException, ParseException {
       IdColoun.setCellValueFactory(new PropertyValueFactory<>("id"));
      /*  Pricecoloun.setCellValueFactory(new PropertyValueFactory<commande,Float>("prix_coomande"));
        NameColoun.setCellValueFactory(new PropertyValueFactory<>("name_commande"));
        Datecoloun.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        PaymentColoun.setCellValueFactory(new PropertyValueFactory<>("payment"));*/
        IdOeuvreColoun.setCellValueFactory(new PropertyValueFactory<>("id_oeuvre"));
    }
}
