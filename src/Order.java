import java.util.ArrayList;
import java.util.List;

public class Order {
    private Client client;
    private List<OrderItem> items;
    private DeliveryMethod deliveryMethod;
    private Courier assignedCourier;
    private String status;

    public Order(Client client , DeliveryMethod deliveryMethod){
        this.client = client;
        this.deliveryMethod = deliveryMethod;
        this.items = new ArrayList<>();
        this.status = "Новый";
    }

    public String getStatus() { return status; }
    public Client getClient() { return client; }
    public Courier getAssignedCourier() {return assignedCourier;}

    public void setStatus(String status) {this.status = status;}

    public void addItem(OrderItem item){
        items.add(item);
    }
    public void assignedCourier(Courier courier){
        this.assignedCourier = courier;
        this.status = "Курьер назначен";
    }

    public double calcTotalCost(){
        double ItemsCost = 0;

        for (OrderItem item : items){
            ItemsCost +=item.getTotalPrise();
        }

        return ItemsCost+deliveryMethod.calculateCost();
    }

    public String getOrderInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Пользователь: ").append(client.getName()).append("/n");
        info.append("Адрес: ").append(client.getAddress().getFullAddress()).append("/n");
        info.append("Способ доставки: ").append(deliveryMethod.getDescription()).append("/n");

        if(assignedCourier !=null){
            info.append("Курьер:").append(assignedCourier.getName()).append("/n");
        }

        info.append("Статус:").append(status).append("/n");
        info.append("Итоговая сумма:").append(calcTotalCost()).append("руб. /n");

        return info.toString();
    }

}
