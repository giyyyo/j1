public class Pickup implements DeliveryMethod {

    @Override public double calculateCost(){
        return 0;
    }
    @Override public String getDescription(){ return "Самовывоз"; }
    @Override public String getEstimatedTime() { return "Можно забрать после подтверждения заказа"; }
    @Override public boolean requiresCourier () { return false; }
}
