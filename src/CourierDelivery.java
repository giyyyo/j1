public class CourierDelivery implements DeliveryMethod{
    private double cost;

    public CourierDelivery (double cost){
        this.cost = cost;
    }

    @Override
    public double calculateCost(){
        return cost;
    }

    @Override
    public String getDescription(){
        return "Доставка курьером";
    }

}
