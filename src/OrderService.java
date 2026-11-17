import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private final List<Order> orders;
    private final List<Courier> couriers;
    private int nextOrderNumber = 1;

    public List<Order> getOrders() { return orders; }
    public List<Courier> getCouriers() { return couriers; }

    public OrderService() {
        this.orders = new ArrayList<>();
        this.couriers = new ArrayList<>();
    }

    public void addCourier (Courier courier) {
        couriers.add(courier);
    }

    public Order createOrder(Client client, DeliveryMethod deliveryMethod) {
        Order newOrder = new Order(nextOrderNumber++, client, deliveryMethod);
        orders.add(newOrder);
        System.out.println("Заказ создан для:"+ client.getName());
        return newOrder;
    }

    public void addItemToOrder(Order order, OrderItem item) {
        ensureOrderExists(order);
        order.addItem(item);
        System.out.println(item.getProductName() + "добавлен в заказ");
    }

    public void assignCourierToOrder(Order order, Courier courier) {
        ensureOrderExists(order);
        if (!couriers.contains(courier))
            throw new IllegalArgumentException("Такого курьера нет в списке службы доставки.");
        if (!order.getDeliveryMethod().requiresCourier())
            throw new IllegalStateException("Для выбранного способа доставки курьер не нужен.");
        if (courier.isBisy())
            throw new IllegalStateException("Этот курьер уже занят.");
        if (order.getAssignedCourier() != null)
            throw new IllegalStateException("У заказа уже есть назначенный курьер.");
        if (order.getStatus() != OrderStatus.NEW)
            throw new IllegalStateException("Нельзя назначить курьера заказу на данном этапе.");
        if (!courier.assignToOrder())
            throw new IllegalStateException("Не удалось назначить курьера.");
        order.assignCourier(courier);
        System.out.println("Курьер" + courier.getName() + "назначен на заказ");
    }

    public void changeOrderStatus(Order order, OrderStatus status) {
        ensureOrderExists(order);
        order.setStatus(status);
        if (status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED) {
            Courier courier = order.getAssignedCourier();
            if (courier != null) courier.release();
        }
    }

    public void showAllOrders() {
        if (orders.isEmpty()){
            System.out.println("Заказов пока нет");
            return;
        }
        System.out.println("Список заказов:");
        for (Order order: orders){
            System.out.println((order.getOrderInfo()));
            System.out.println(("----"));
        }
    }

    public void showAllCouriers() {
        if (couriers.isEmpty()) {
            System.out.println("Курьеров пока нет.");
            return;
        }
        System.out.println("Курьеры:");
        for (Courier courier : couriers) {
            String status = courier.isBisy() ? "Занят" : "Свободен";
            System.out.println(courier.getName() + " - " + status);
        }
    }
    private void ensureOrderExists(Order order) {
        if (!orders.contains(order))
            throw new IllegalArgumentException("Заказ не найден.");
    }

}
