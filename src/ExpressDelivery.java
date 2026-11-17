public class ExpressDelivery implements DeliveryMethod{
    private final double cost;

    public ExpressDelivery(double cost) {
        this.cost = cost;
    }

    @Override public double calculateCost(){
        return cost;
    }
    @Override public String getDescription(){
        return "Экспресс доставка";
    }
    @Override public String getEstimatedTime() { return "2-4 часа"; }
    @Override public boolean requiresCourier() { return true; }
    }
