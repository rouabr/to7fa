package models;

public class oeuvre {
    private int id_oeuvre;
    private String name;
    private String imgSrc;
    private double price;
    private String color;

    public oeuvre() {
  System.out.println(id_oeuvre);
    }

    public int getId_oeuvre() {
        return id_oeuvre;
    }

    public void setId_oeuvre(int id_oeuvre) {
        this.id_oeuvre = id_oeuvre;
    }

    public oeuvre(int id_oeuvre,String name, String imgSrc, double price, String color) {
        this.id_oeuvre = id_oeuvre;
        this.name = name;
        this.imgSrc = imgSrc;
        this.price = price;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
