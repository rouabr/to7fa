package models;

import java.util.Date;

public class commande  {
    private  int id;
    private float prix_coomande;
    private String name_commande;
    private Date date_commande;
    private String payment;
    private int id_oeuvre;

    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id=id;
    }



    public float getprix_commande() {
        return prix_coomande;
    }

    public void setprix_commande(float type) {
        this.prix_coomande = type;
    }

    public String getname_commande() {
        return name_commande;
    }

    public void setname_commande(String name_commande) {
        this.name_commande = name_commande;
    }

    public Date getdate_commande() {
        return date_commande;
    }
    public void setdate_commande(Date date) {
        this.date_commande = date;
    }
    public String getpayment() { return payment; }

    public void setpayment(String payment) {
        this.payment = payment;
    }


    // Constructors, getters, and setters

    public commande() {

    }
    public commande(int id, float prix_coomande, String name_commande, Date date_commande ,String payment, int id_oeuvre)  {
        this.id=id;
        this.prix_coomande = prix_coomande;
        this.name_commande = name_commande;
       this.date_commande=date_commande;
        this.payment = payment;
        this.id_oeuvre=id_oeuvre;

    }


    public commande(float prix_coomande, String name_commande, String payment,int id_oeuvre)  {
        this.prix_coomande = prix_coomande;
        this.name_commande = name_commande;

        this.payment = payment;
        this.id_oeuvre=id_oeuvre;

    }

    // Getters and Setters




}
