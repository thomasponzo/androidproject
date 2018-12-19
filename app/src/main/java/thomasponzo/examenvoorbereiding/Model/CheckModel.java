package thomasponzo.examenvoorbereiding.Model;

public class CheckModel {

    //Global variables
    private String table;
    private String date;
    private String time;
    private String amount;
    private String name;
    private String price;
    private String totalprice;
    private String moneypayed;
    private String totalpayed;
    private String back;

    public CheckModel() {
    }

    //Constructor
    public CheckModel(String table, String date, String time, String amount, String name, String price, String totalprice, String moneypayed, String totalpayed, String back) {
        this.table = table;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.name = name;
        this.price = price;
        this.totalprice = totalprice;
        this.moneypayed = moneypayed;
        this.totalpayed = totalpayed;
        this.back = back;
    }

    //Getters and setters

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getMoneypayed() {
        return moneypayed;
    }

    public void setMoneypayed(String moneypayed) {
        this.moneypayed = moneypayed;
    }

    public String getTotalpayed() {
        return totalpayed;
    }

    public void setTotalpayed(String totalpayed) {
        this.totalpayed = totalpayed;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }
}
