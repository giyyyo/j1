public class CourierDelivery implements DeliveryMethod{
    private final double cost;

    public CourierDelivery (double cost){
        this.cost = cost;
    }

    @Override public double calculateCost(){
        return cost;
    }
    @Override public String getDescription(){
        return "Доставка курьером";
    }
    @Override public String getEstimatedTime() { return "1-2 дня"; }
    @Override public boolean requiresCourier() { return true; }
}
