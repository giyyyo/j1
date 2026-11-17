public enum OrderStatus {
    NEW("Новый"),
    COURIER_ASSIGNED("Курьер назначен"),
    IN_DELIVERY("В доставке"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменён");

    private final String title;

    OrderStatus(String title) { this.title = title; }

    @Override public String toString() { return title; }
}