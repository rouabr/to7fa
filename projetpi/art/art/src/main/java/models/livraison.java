package models;

import java.util.Date;

public class livraison {

        private  int id_livraison;
        private String date_livraison;
        private String adresse_livraison;
        private String status_livraison;
        private Float frais_livraison;
        private String name_transporteur;

    public String getNom_commande() {
        return nom_commande;
    }

    public void setNom_commande(String nom_commande) {
        this.nom_commande = nom_commande;
    }

    private String nom_commande;

            // Constructors, getters, and setters

        public livraison() {

        }


        public livraison(String date_livraison, String adresse_livraison, String status_livraison, Float frais_livraison, String name_transporteur, String nom_commande) {

            this.date_livraison = date_livraison;
            this.adresse_livraison = adresse_livraison;
            this.status_livraison = status_livraison;
            this.frais_livraison = frais_livraison;
            this.name_transporteur = name_transporteur;
            this.nom_commande = nom_commande;
        }

    public livraison(int id_livraison,String date_livraison, String adresse_livraison, String status_livraison, Float frais_livraison, String name_transporteur, String nom_commande) {
          this.id_livraison=id_livraison;
        this.date_livraison = date_livraison;
        this.adresse_livraison = adresse_livraison;
        this.status_livraison = status_livraison;
        this.frais_livraison = frais_livraison;
        this.name_transporteur = name_transporteur;
        this.nom_commande = nom_commande;
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

        public String getName() {
        return name_transporteur;
        }

        public void setName(String name_transporteur) {
        this.name_transporteur = name_transporteur;
        }




    }

