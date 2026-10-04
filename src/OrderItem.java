public class OrderItem {
    private String productName;
    private int quantity;
    private double prisePerUnit;

    public OrderItem (String productName, int quantity, double prisePerUnit){
        this.productName = productName;
        this.quantity = quantity;
        this.prisePerUnit = prisePerUnit;
    }

    public String getProductName(){return productName;}
    public int getQuantity(){return quantity;}
    public double getPrisePerUnit(){return prisePerUnit;}
    public double getTotalPrise(){ return prisePerUnit * quantity;}

}
