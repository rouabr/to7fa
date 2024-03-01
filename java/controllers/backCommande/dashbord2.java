package controllers.backCommande;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.panierat;
import services.servicePanier;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;

public class dashbord2 implements Initializable {

    @FXML
    private TableView<panierat> CommandeTable;

    @FXML
    private TableColumn<panierat, Integer> IDoeuvre;

    @FXML
    private TableColumn<panierat, Integer> IDuser;

    @FXML
    private TableColumn<panierat, Integer> IdColoun;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
  System.out.println("bbaa");
        try {
            // Call the selectAllTableviewservicePanier method to retrieve data
            servicePanier sp=new servicePanier();
            ObservableList<panierat> paniers = sp.selectAlloeuvreTableview();
            System.out.println("hello");
            // Set data to TableView
            CommandeTable.setItems(paniers);

            loaddata();

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    private void loaddata() throws SQLException, ParseException {
        IdColoun.setCellValueFactory(new PropertyValueFactory<>("id_panier"));
       IDuser.setCellValueFactory(new PropertyValueFactory<panierat,Integer>("id_user"));

        IDoeuvre.setCellValueFactory(new PropertyValueFactory<>("id_oeuvre"));
    }
}
