package models;

import java.util.Date;

public class ReservationMusee {
    private int reservation_id;
    private Date date_reservation;
    private int nbr_tickets_reserves;
    private User user;
    private Musee musee;

    public ReservationMusee() {}
    public ReservationMusee(int reservation_id,Date date_reservation,int nbr_tickets_reserves,User user,Musee musee){
        this.reservation_id = reservation_id;
        this.date_reservation = date_reservation;
        this.nbr_tickets_reserves = nbr_tickets_reserves;
        this.user = user;
        this.musee = musee;

    }
    public ReservationMusee(Date date_reservation,int nbr_tickets_reserves,User user,Musee musee){
        this.date_reservation = date_reservation;
        this.nbr_tickets_reserves = nbr_tickets_reserves;
        this.user = user;
        this.musee = musee;

    }
    public ReservationMusee(Date date_reservation,int nbr_tickets_reserves,Musee musee){
        this.date_reservation = date_reservation;
        this.nbr_tickets_reserves = nbr_tickets_reserves;
        this.musee = musee;

    }
    public String getUsername(){
        return user.getUser_name();
    }
    public String getNom_musee(){
        return musee.getNom_musee();
    }

    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Date getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(Date date_reservation) {
        this.date_reservation = date_reservation;
    }

    public int getNbr_tickets_reserves() {
        return nbr_tickets_reserves;
    }

    public void setNbr_tickets_reserves(int nbr_tickets_reserves) {
        this.nbr_tickets_reserves = nbr_tickets_reserves;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Musee getMusee() {
        return musee;
    }

    public void setMusee(Musee musee) {
        this.musee = musee;
    }
}
