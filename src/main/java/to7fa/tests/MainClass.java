package to7fa.tests;


import to7fa.entities.evenement;
import to7fa.entities.participation_evenement;
import to7fa.services.ServiceEvenement;
import to7fa.services.ServiceParticipationEvenement;

/**
 *
 * @author Admin
 */
public class MainClass {

    public static void main (String[] args){
        //MyConnection mc =  MyConnection.getInstance();

        ServiceEvenement eventcd = new ServiceEvenement();
        java.sql.Date dateDebut = java.sql.Date.valueOf("2023-03-01");
        java.sql.Date dateFin = java.sql.Date.valueOf("2023-03-30");
        evenement event = new evenement("ElHadhra","jfkfkf","Théatre Municipal","Artistique-Musical",dateDebut,dateFin,500,"lien",35);

        java.sql.Date dateDebut2 = java.sql.Date.valueOf("2023-03-01");
        java.sql.Date dateFin2 = java.sql.Date.valueOf("2024-03-15");


        //eventcd.ajouterEvenement();
        //eventcd.ajouterEvenement2(event);

        // System.out.println(eventcd.afficherEvenements());

        eventcd.modifierEvenement(event);

        //eventcd.modifierEvenement(12,"ElHadhra","jfkfkf","Théatre MunicipalCENTREVILLE","Artistique-Musical",dateDebut,dateFin,498,2,"lien",3);
        //eventcd.modifierEvenement(10,"ZIARA-HADHRA","JVKREJGT","Théatre MunicipalCENTREVILLE","Artistique-Musical",dateDebut2,dateFin2,498,2,"lien",3);

        // eventcd.supprimerEvenement(8);
        //eventcd.supprimerEvenement(1);

        ServiceParticipationEvenement participationeventcd = new ServiceParticipationEvenement();

        participation_evenement participation = new participation_evenement(1,7,2,"0");

        participation_evenement participation2 = new participation_evenement(1,9,10,"0");

        // participationeventcd.ajouterParticipationEvenement(participation);
        //participationeventcd.ajouterParticipationEvenement(participation2);

        //System.out.println(participationeventcd.afficherParticipations());

        //participationeventcd.supprimerParticipation(5);

        //participationeventcd.modifierParticipationEvenement(1, 1, 9, dateParticipation, 5);
    }

}
