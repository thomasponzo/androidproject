package thomasponzo.examenvoorbereiding.Model;

public class OrderModel {

    //Global variables

    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String price;

    public OrderModel() {
    }

    //Constructor

    public OrderModel(String productId, String productName, String quantity, String price) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        this.price = price;
    }

    //Getters and setters

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
