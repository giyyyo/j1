public class Pickup implements DeliveryMethod {
    private double cost;

    @Override
    public double calculateCost(){
        return 0;
    }

    @Override
    public String getDescription(){
        return "Самовывоз";
    }
}
