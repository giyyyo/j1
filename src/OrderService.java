import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private List<Order> orders;
    private List<Courier> couriers;

    public List<Order> getOrder() { return orders; }
    public List<Courier> getCouriers() { return couriers; }

    public OrderService() {
        this.orders = new ArrayList<>();
        this.couriers = new ArrayList<>();
    }

    public void addCourier (Courier courier) {
        couriers.add(courier);
    }

    public Order createOrder(Client client, DeliveryMethod deliveryMethod) {
        Order newOrder = new Order(client, deliveryMethod);
        orders.add(newOrder);
        System.out.println("Заказ создан для:"+ client.getName());
        return newOrder;
    }

    public void addItemTorder(Order order, OrderItem item) {
        order.addItem(item);
        System.out.println(item.getProductName() + "добавлен в заказ");
    }

    public void assignourierToOrder(Order order, Courier courier) {
        if (courier.isBisy()){
            System.out.println("Курьер" + courier.getName() +"занят");
            return;
        }
        order.assignedCourier(courier);
        courier.assignToOrder();
        System.out.println("Курьер" + courier.getName() + "назначен на заказ");
    }

    public void completeDelivery(Order order) {
        if(order.getStatus().equals("Доставлен")) {
            System.out.println("Заказ уже доставлен");
        }
        order.setStatus("Доставлен");
        Courier courier  = order.getAssignedCourier();
        if(courier != null){
            courier.release();
            System.out.println("Заказ доставлен курьером");
        } else {
            System.out.println("Заказ получен"); //pickup
        }
    }

    public void showAllOrders() {
        if (orders.isEmpty()){
            System.out.println("Заказов пока нет");
            return;
        }
        System.out.println("Список заказов:");
        for (int i=0; i < orders.size(); i++){
            System.out.println("Заказ №" + (i+1) + ": ");
            System.out.println(orders.get(i).getOrderInfo());
            System.out.println("--------");
        }
    }

    public void showAllCouriers() {
        System.out.println("Курьеры:");
        for (Courier courier : couriers) {
            String status = courier.isBisy() ? "Занят" : "Свободен";
            System.out.println(courier.getName() + " - " + status);
        }
    }

}
