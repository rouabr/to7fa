package models;

public class Musee {
    private int id_musee;
    private String nom_musee;
    private String adresse;
    private String ville;
    private int nbr_tickets_disponibles;
    private String description;
    private String image_path;
    public Musee(){

    }
    public Musee(int id_musee, String nom_musee, String adresse, String ville,int nbr_tickets_disponibles,String description, String image_path) {
        this.id_musee = id_musee;
        this.nom_musee = nom_musee;
        this.adresse = adresse;
        this.ville = ville;
        this.nbr_tickets_disponibles=nbr_tickets_disponibles;
        this.description = description;
        this.image_path = image_path;

    }
    public Musee(String nom_musee, String adresse, String ville,int nbr_tickets_disponibles,String description,String image_path) {
        this.nom_musee = nom_musee;
        this.adresse = adresse;
        this.ville = ville;
        this.nbr_tickets_disponibles=nbr_tickets_disponibles;
        this.description = description;
        this.image_path = image_path;

    }
    public Musee(String nom_musee, String adresse, String ville,int nbr_tickets_disponibles,String description) {
        this.nom_musee = nom_musee;
        this.adresse = adresse;
        this.ville = ville;
        this.nbr_tickets_disponibles=nbr_tickets_disponibles;
        this.description = description;

    }
    public int getId_musee(){return id_musee;}
    public String getNom_musee(){return nom_musee;}
    public String getAdresse(){return adresse;}
    public String getVille(){return ville;}
    public int getNbr_tickets_disponibles(){return nbr_tickets_disponibles;}
    public String getDescription(){return description;}

    public String getImage_path() {
        return image_path;
    }

    public void setId_musee(int id_musee) {
        this.id_musee = id_musee;
    }

    public void setNom_musee(String nom_musee) {
        this.nom_musee = nom_musee;
    }
    public void setAdresse(String adresse){this.adresse = adresse;}
    public void setVille(String ville){this.ville = ville;}
    public void setNbr_tickets_disponibles(int nbr_tickets_disponibles){this.nbr_tickets_disponibles=nbr_tickets_disponibles;}
    public void setDescription(String description){this.description = description;}

   public void setImage_path(String image_path){this.image_path=image_path;}
}
