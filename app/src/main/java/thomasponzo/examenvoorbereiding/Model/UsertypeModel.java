package thomasponzo.examenvoorbereiding.Model;

//Fetch the information
public class UsertypeModel {

    //Global variables

    private String stafftype;

    //Contructor

    public UsertypeModel() {
    }

    //Getters and setters

    public UsertypeModel(String stafftype) {
        this.stafftype = stafftype;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }
}
