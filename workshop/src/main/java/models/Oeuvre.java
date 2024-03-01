package models;
import java.text.SimpleDateFormat;
import java.util.Date;
import models.Categorie;
public class Oeuvre {
    int id_oeuvre;
    String titre;
    String description;
    float prix;
    String date;
    String status;
    String lienImg;
    int id_cat;
    String nom_cat;
    int user_id;
    //Categorie categorie;

    public Oeuvre()
    {}


    //Categorie id_cat;

    public Oeuvre(String titre, String description, float prix, String date, String status, String lienImg) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.date = date;
        this.status = status;
        this.lienImg = lienImg;


    }
    public Oeuvre(String titre, String description, float prix, String date, String status, String lienImg,int id_cat) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.date = date;
        this.status = status;
        this.lienImg = lienImg;
        this.id_cat = id_cat;
        //this.nom_cat=nom_cat;

    }






    @Override
    public String toString() {
        return "Oeuvre{" +
                "id_oeuvre=" + id_oeuvre +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", lienImg='" + lienImg + '\'' +


                '}';
    }

    public String getNom_cat() {
        return nom_cat;
    }

    public void setNom_cat(String nom_cat) {
        this.nom_cat = nom_cat;
    }

    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public float getPrix() {
        return prix;
    }

    public String getDate() {
        return date;
    }
    //public Categorie getCategorie(){return categorie;}

    /*public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }*/

    public String getLienImg() {
        return lienImg;
    }

    public String getStatus() {
        return status;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setLienImg(String lienImg) {
        this.lienImg = lienImg;
    }

    public int getUser() {
        return user_id;
    }

    public void setUser(int user_id) {

        this.user_id = user_id;
    }

   /* public void setNomCategorie(String Nomcategorie){
        this.categorie.setNom_cat(Nomcategorie);

    }*/

/*public void setIdCategorie(int IdCategorie){
        this.categorie.setId_cat(IdCategorie);
    }*/

  /*  public String getNomCategorie(){
        return categorie.getNom_cat();
    }
    public int getIdCategorie(){
        return categorie.getId_cat();
    }*/

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public int getId_cat() {
        return id_cat;
    }




}
