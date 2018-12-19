package thomasponzo.examenvoorbereiding.Model;

public class OrderMenuModel {

    //Global variables

    private String name;
    private  String picture;

    public OrderMenuModel() {
    }

    //Constructor

    public OrderMenuModel(String name, String picture) {
        this.name = name;
        this.picture = picture;
    }

    //Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
