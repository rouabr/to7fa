package models;

public class panierat {
    private int id_panier;

    private int id_user;
    private int id_oeuvre;
    public panierat()
    {
    }
    public panierat(int id_user , int id_oeuvre) {
        this.id_user = id_user;
        this.id_oeuvre = id_oeuvre;
    }
    public panierat(int id_panier, int id_user, int id_oeuvre) {
        this.id_panier = id_panier;

        this.id_user = id_user;
        this.id_oeuvre = id_oeuvre;
    }



    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }
}
