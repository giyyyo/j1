public class OrderItem {
    private final String productName;
    private final int quantity;
    private final double prisePerUnit;

    public OrderItem (String productName, int quantity, double prisePerUnit){
        //нельзя чтоб юзер оставил поля пустыми!!!
        this.productName = productName;
        this.quantity = quantity;
        this.prisePerUnit = prisePerUnit;
    }

    public String getProductName(){return productName;}
    public int getQuantity(){return quantity;}
    public double getPrisePerUnit(){return prisePerUnit;}
    public double getTotalPrise(){ return prisePerUnit * quantity;}

    @Override public String toString() {return productName + "x" + quantity + " - " + getTotalPrise() + "руб.";}
}
