public class ExpressDelivery implements DeliveryMethod{
    private double cost;

    public ExpressDelivery(double cost) {
        this.cost = cost;
    }

    @Override
    public double calculateCost(){
        return cost;
    }

    @Override
    public String getDescription(){
        return "Экспресс доставка";
    }

    }
