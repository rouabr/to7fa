package models;

import java.util.Date;

public class livraison {

        private  int id_livraison;
        private String date_livraison;
        private String adresse_livraison;
        private String status_livraison;
        private Float frais_livraison;

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    private int id_commande;

    public String getNom_coomande() {
        return nom_coomande;
    }

    public void setNom_coomande(String nom_coomande) {
        this.nom_coomande = nom_coomande;
    }

    public String getNom_livreur() {
        return Nom_livreur;
    }

    public void setNom_livreur(String nom_livreur) {
        Nom_livreur = nom_livreur;
    }

    private String nom_coomande;
    private String Nom_livreur;

    public int getId_livreur() {
        return id_livreur;
    }

    public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }

    private int id_livreur;

        public livraison() {

        }


        public livraison(String date_livraison, String adresse_livraison, String status_livraison, Float frais_livraison, String nom_livreur, String nom_commande) {

            this.date_livraison = date_livraison;
            this.adresse_livraison = adresse_livraison;
            this.status_livraison = status_livraison;
            this.frais_livraison = frais_livraison;
            this.Nom_livreur = nom_livreur;
            this.nom_coomande = nom_commande;
        }

    public livraison(int id_livraison,String date_livraison, String adresse_livraison, String status_livraison, Float frais_livraison, String nom_livreur, String nom_commande) {
          this.id_livraison=id_livraison;
        this.date_livraison = date_livraison;
        this.adresse_livraison = adresse_livraison;
        this.status_livraison = status_livraison;
        this.frais_livraison = frais_livraison;
        this.Nom_livreur = nom_livreur;
        this.nom_coomande = nom_commande;
    }

        // Getters and Setters

        public int getID() {
            return id_livraison;
        }

        public void setID(int id_livraison) {
            this.id_livraison=id_livraison;
        }



        public String getDate() {
            return date_livraison;
        }

        public void setDate(String date_livraison) {
            this.date_livraison = date_livraison;
        }

        public String getAdresse() {
            return adresse_livraison;
        }

        public void setAdresse(String adresse_livraison) {
            this.adresse_livraison = adresse_livraison;
        }

        public String getStatus() {
            return status_livraison;
        }

        public void setStatus(String status_livraison) {
            this.status_livraison = status_livraison;
        }


        public Float getFrais() {
        return frais_livraison;
        }

        public void setFrais(Float frais_livraison) {
        this.frais_livraison = frais_livraison;
        }






    }

