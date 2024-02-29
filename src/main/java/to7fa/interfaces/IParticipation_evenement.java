package to7fa.interfaces;

import to7fa.entities.participation_evenement;

import java.util.List;

import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public interface IParticipation_evenement {


    public ObservableList<participation_evenement> afficherParts();
    public void ajouterParticipationEvenement(participation_evenement participation);


    public List<participation_evenement> afficherParticipations();


    public boolean supprimerParticipation(int ID_participation);
    public void modifierParticipationEvenement(participation_evenement participation);

    public int getId2(String id);



}
