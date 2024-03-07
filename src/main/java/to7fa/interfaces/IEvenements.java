package to7fa.interfaces;
import to7fa.entities.evenement;

import java.util.List;

import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface IEvenements {


    public ObservableList<evenement> afficherEvents();



    public void ajouterEvenement();
    public void ajouterEvenement2(evenement event);

    public List<evenement> afficherEvenements();



    public void modifierEvenement(evenement event);




    public boolean supprimerEvenement(int ID_event);



    public int getId(String id);
    public int getId2(String id);
    public String getNom(int id);



}
