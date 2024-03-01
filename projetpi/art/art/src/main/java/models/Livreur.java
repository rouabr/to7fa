package models;

public class Livreur {
    private int id_livreur;
    private String nom_livreur;
    private String prenom_livreur;
    private String adresse;
    private String telephone;
    private String matricule;

    public Livreur() {
    }

    public Livreur(String nom_livreur, String prenom_livreur, String adresse, String telephone, String matricule) {
        this.nom_livreur = nom_livreur;
        this.prenom_livreur = prenom_livreur;
        this.adresse = adresse;
        this.telephone = telephone;
        this.matricule = matricule;
    }

    public Livreur(int id_livreur, String nom_livreur, String prenom_livreur, String adresse, String telephone, String matricule) {
        this.id_livreur = id_livreur;
        this.nom_livreur = nom_livreur;
        this.prenom_livreur = prenom_livreur;
        this.adresse = adresse;
        this.telephone = telephone;
        this.matricule = matricule;
    }

    public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }

    public void setNom_livreur(String nom_livreur) {
        this.nom_livreur = nom_livreur;
    }

    public void setPrenom_livreur(String prenom_livreur) {
        this.prenom_livreur = prenom_livreur;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getId_livreur() {
        return id_livreur;
    }

    public String getNom_livreur() {
        return nom_livreur;
    }

    public String getPrenom_livreur() {
        return prenom_livreur;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getMatricule() {
        return matricule;
    }
}
