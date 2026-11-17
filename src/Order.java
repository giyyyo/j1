import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Client client;
    private final List<OrderItem> items;
    private final DeliveryMethod deliveryMethod;
    private Courier assignedCourier;
    private OrderStatus status;
    private final int number;

    public Order(int number, Client client , DeliveryMethod deliveryMethod){
        this.client = client;
        this.deliveryMethod = deliveryMethod;
        this.items = new ArrayList<>();
        this.status = OrderStatus.NEW;
        this.number = number;
    }

    public OrderStatus getStatus() { return status; }
    public Client getClient() { return client; }
    public boolean isEmpty() {return items.isEmpty(); }
    public Courier getAssignedCourier() {return assignedCourier;}
    public int getNumber() { return number; }
    public DeliveryMethod getDeliveryMethod() { return deliveryMethod; }
    //public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }

    public void addItem(OrderItem item){
        if (status != OrderStatus.NEW)
            throw new IllegalStateException("Нельзя изменять состав заказа после начала его обработки.");
        items.add(item);
    }
    public void assignCourier(Courier courier){
        if (!deliveryMethod.requiresCourier())
            throw new IllegalStateException("Для самовывоза курьер не требуется.");
        if (status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED)
            throw new IllegalStateException("Нельзя назначить курьера для завершённого заказа.");
        this.assignedCourier = courier;
        status = OrderStatus.COURIER_ASSIGNED;
    }

    public void setStatus(OrderStatus newStatus) {
        boolean allowed =
                (status == OrderStatus.NEW && (newStatus == OrderStatus.CANCELLED
                        || (!deliveryMethod.requiresCourier() && newStatus == OrderStatus.IN_DELIVERY)))
                        || (status == OrderStatus.COURIER_ASSIGNED
                        && (newStatus == OrderStatus.IN_DELIVERY || newStatus == OrderStatus.CANCELLED))
                        || (status == OrderStatus.IN_DELIVERY
                        && (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED));

        if (!allowed)
            throw new IllegalStateException("Недопустимый переход статуса: " + status + " → " + newStatus);
        status = newStatus;
    }

    public double calcTotalCost(){
        double ItemsCost = 0;

        for (OrderItem item : items){
            ItemsCost +=item.getTotalPrise();
        }

        return ItemsCost + deliveryMethod.calculateCost();
    }

    public String getOrderInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Заказ №").append(number).append('\n');
        info.append("Пользователь: ").append(client.getName()).append("\n");
        info.append("Адрес: ").append(client.getAddress().getFullAddress()).append("\n");
        info.append("Состав заказа:\n");
        if (items.isEmpty()) info.append("  (товары не добавлены)\n");
        for (OrderItem item : items) info.append("  - ").append(item).append('\n');
        info.append("Способ доставки: ").append(deliveryMethod.getDescription()).append("\n");
        info.append("Стоимость доставки: ").append(deliveryMethod.calculateCost()).append(" руб.\n");
        info.append("Срок: ").append(deliveryMethod.getEstimatedTime()).append('\n');
        info.append("Курьер: ").append(assignedCourier == null ? "не назначен" : assignedCourier.getName()).append('\n');
        info.append("Статус:").append(status).append("\n");
        info.append("Итоговая сумма:").append(calcTotalCost());
        return info.toString();
    }

}
