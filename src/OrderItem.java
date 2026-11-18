public class OrderItem {
    private final String productName;
    private final int quantity;
    private final double prisePerUnit;

    public OrderItem (String productName, int quantity, double prisePerUnit){
        if (productName == null || productName.isBlank()) throw new IllegalArgumentException("Название товара не может быть пустым.");
        if (quantity <= 0) throw new IllegalArgumentException("Количество товара должно быть больше нуля.");
        if (prisePerUnit < 0) throw new IllegalArgumentException("Цена товара должна быть неотрицательным числом.");

        this.productName = productName;
        this.quantity = quantity;
        this.prisePerUnit = prisePerUnit;
    }

    public String getProductName(){return productName;}
    public double getTotalPrise(){ return prisePerUnit * quantity;}

    @Override public String toString() {return productName + " x " + quantity + " - " + getTotalPrise() + "руб.";}
}
