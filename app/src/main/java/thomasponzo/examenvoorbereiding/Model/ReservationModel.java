package thomasponzo.examenvoorbereiding.Model;

//Fetch the information
public class ReservationModel {

    //Global variables

    private String dateNumber;
    private String personNumber;
    private String phoneNumber;
    private String reservationName;
    private String tableNumBer;
    private String timeNumber;

    public ReservationModel() {
    }

    //Contructor
    public ReservationModel(String dateNumber, String personNumber, String phoneNumber, String reservationName, String tableNumBer, String timeNumber) {
        this.dateNumber = dateNumber;
        this.personNumber = personNumber;
        this.phoneNumber = phoneNumber;
        this.reservationName = reservationName;
        this.tableNumBer = tableNumBer;
        this.timeNumber = timeNumber;
    }

    //Getters and setters

    public String getDateNumber() {
        return dateNumber;
    }

    public void setDateNumber(String dateNumber) {
        this.dateNumber = dateNumber;
    }

    public String getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getTableNumBer() {
        return tableNumBer;
    }

    public void setTableNumBer(String tableNumBer) {
        this.tableNumBer = tableNumBer;
    }

    public String getTimeNumber() {
        return timeNumber;
    }

    public void setTimeNumber(String timeNumber) {
        this.timeNumber = timeNumber;
    }
}
