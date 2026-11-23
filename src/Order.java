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
    public Courier getAssignedCourier() {return assignedCourier;}
    public int getNumber() { return number; }
    public DeliveryMethod getDeliveryMethod() { return deliveryMethod; }

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
        if (status == newStatus) throw new IllegalStateException("Заказ уже имеет этот статус.");

        boolean isValid = false;
        String reason = "";

        if (status == OrderStatus.NEW) {
            if (newStatus == OrderStatus.CANCELLED) { isValid = true; }
            else if (newStatus == OrderStatus.IN_DELIVERY && !deliveryMethod.requiresCourier()) { isValid = true; }
            else if (newStatus == OrderStatus.IN_DELIVERY) { reason = "Сначала необходимо назначить курьера."; }
            else if (newStatus == OrderStatus.DELIVERED) { reason = "Нельзя завершить заказ, который ещё не был передан в доставку."; }
            else if (newStatus == OrderStatus.COURIER_ASSIGNED) { reason = "Для назначения курьера используйте функцию назначения курьера."; }
            else { reason = "Недопустимый переход из статуса «Новый»."; }
        }
        else if (status == OrderStatus.COURIER_ASSIGNED) {
            if (newStatus == OrderStatus.IN_DELIVERY || newStatus == OrderStatus.CANCELLED) { isValid = true; }
            else if (newStatus == OrderStatus.DELIVERED) { reason = "Сначала необходимо начать доставку."; }
            else { reason = "После назначения курьера заказ можно только отправить в доставку или отменить."; }
        }
        else if (status == OrderStatus.IN_DELIVERY) {
            if (newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED) { isValid = true; }
            else { reason = "Заказ в доставке можно только завершить или отменить.";}
        }
        else if (status == OrderStatus.DELIVERED) { reason = "Заказ уже доставлен. Его статус нельзя изменить."; }
        else if (status == OrderStatus.CANCELLED) { reason = "Заказ уже отменён. Его статус нельзя изменить.";
        }
        if (!isValid)
            throw new IllegalStateException("Недопустимый переход статуса:" + status + " → " + newStatus + "». Причина: " + reason);
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
