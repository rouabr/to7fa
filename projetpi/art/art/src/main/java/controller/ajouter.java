package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.livraison;
import services.servicePerson;
import utils.DBConnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ajouter {
    @FXML
    private TableView<livraison> tvlivraison;

    @FXML
    private Button btnajouter;

    @FXML
    private Button btnmodifier;

    @FXML
    private Button btnsupprimer;

    @FXML
    private TableColumn<livraison, String> ciladresse;

    @FXML
    private TableColumn<livraison, String> colcommande;

    @FXML
    private TableColumn<livraison, String> coldate;

    @FXML
    private TableColumn<livraison, Float> colfrais;

    @FXML
    private TableColumn<livraison, Integer> colid;

    @FXML
    private TableColumn<livraison, String> colnom;

    @FXML
    private TableColumn<livraison, String> colstatus;

    @FXML
    private TextField tfadresse;

    @FXML
    private TextField tfcommande;

    @FXML
    private TextField tfdate;

    @FXML
    private TextField tffrais;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfstatus;

    @FXML
    void ajouterLivraison(ActionEvent event) {
        String frais=tffrais.getText();


        try {
            float newfrais = Float.parseFloat(frais);

            livraison p = new livraison(tfdate.getText(), tfadresse.getText(), tfstatus.getText(),newfrais,tfnom.getText(), tfcommande.getText() );
            servicePerson sp = new servicePerson();
            sp.insertOne(p);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }
    public ObservableList<livraison> getLivraison() {
        ObservableList<livraison> livraisons = FXCollections.observableArrayList();
        String query = "SELECT * FROM livraison";
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                livraison liv = new livraison();
                liv.setID(rs.getInt("id_livraison"));
                liv.setAdresse(rs.getString("adresse_livraison"));
                liv.setDate(rs.getString("date_livraison"));
                liv.setFrais(rs.getFloat("frais_livraison"));
                liv.setName(rs.getString("name_transporteur"));
                liv.setStatus(rs.getString("status_livraison"));


                livraisons.add(liv);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des musées.", e);
        }
        return livraisons;
    }

    @FXML
    public void showLivraisons(){

        ObservableList<livraison> list = getLivraison();
        tvlivraison.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<livraison,Integer>("id_livraison"));
        ciladresse.setCellValueFactory(new PropertyValueFactory<livraison,String>("adresse_livraison"));
        coldate.setCellValueFactory(new PropertyValueFactory<livraison,String>("date_livraison"));
        colstatus.setCellValueFactory(new PropertyValueFactory<livraison,String>("status_livraison"));
        colfrais.setCellValueFactory(new PropertyValueFactory<livraison,Float>("frais_livraison"));
        colnom.setCellValueFactory(new PropertyValueFactory<livraison,String>("name_transporteur"));
    }
    @FXML
    void modifierLivraison(ActionEvent event) {
        String frais=tffrais.getText();
        try {
            float newfrais = Float.parseFloat(frais);

            livraison p = new livraison(Integer.parseInt(tfid.getText()) , tfdate.getText(), tfadresse.getText(), tfstatus.getText(),newfrais,tfnom.getText(), tfcommande.getText() );
            servicePerson sp = new servicePerson();
            sp.updateOne(p);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void supprimerLivraison(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnajouter != null : "fx:id=\"btnajouter\" was not injected: check your FXML file 'aboud.fxml'.";
        assert btnmodifier != null : "fx:id=\"btnmodifier\" was not injected: check your FXML file 'aboud.fxml'.";
        assert btnsupprimer != null : "fx:id=\"btnsupprimer\" was not injected: check your FXML file 'aboud.fxml'.";
        assert ciladresse != null : "fx:id=\"ciladresse\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colcommande != null : "fx:id=\"colcommande\" was not injected: check your FXML file 'aboud.fxml'.";
        assert coldate != null : "fx:id=\"coldate\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colfrais != null : "fx:id=\"colfrais\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colid != null : "fx:id=\"colid\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colnom != null : "fx:id=\"colnom\" was not injected: check your FXML file 'aboud.fxml'.";
        assert colstatus != null : "fx:id=\"colstatus\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfadresse != null : "fx:id=\"tfadresse\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfcommande != null : "fx:id=\"tfcommande\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfdate != null : "fx:id=\"tfdate\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tffrais != null : "fx:id=\"tffrais\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfid != null : "fx:id=\"tfid\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tfstatus != null : "fx:id=\"tfstatus\" was not injected: check your FXML file 'aboud.fxml'.";
        assert tvlivraison != null : "fx:id=\"tvlivraison\" was not injected: check your FXML file 'aboud.fxml'.";
showLivraisons();
    }


}