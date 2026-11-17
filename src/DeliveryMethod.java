public interface DeliveryMethod {
    double calculateCost();
    String getDescription();
    String getEstimatedTime();
    boolean requiresCourier();
}