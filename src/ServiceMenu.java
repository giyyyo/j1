import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceMenu {
    private final OrderService service;
    private final Scanner scanner;
    private final Helper helper;

    public ServiceMenu (OrderService service, Scanner scanner, Helper helper){
        this.service = service;
        this.scanner = scanner;
        this.helper = helper;
    }

    public void createNewOrder() {
        System.out.println("--- Создание нового заказа ---");

        System.out.println("Введите имя клиента: ");
        String name = helper.readLine();

        System.out.println("Введите город: ");
        String city = helper.readLine();

        System.out.println("Введите улицу:");
        String street = helper.readLine();

        System.out.println("Введите дом:");
        String house = helper.readLine();

        System.out.print("Квартира: ");
        String apartment = scanner.nextLine();

        Client client = new Client(name, new Address(city, street, house, apartment));
        DeliveryMethod method = chooseDeliveryMethod();
        List<OrderItem> items = new ArrayList<>();

        boolean addingItems = true;
        while (addingItems) {
            Menu.showProductMenu();
            int productChoice = helper.readInt("Товар:", 0, 12);
            if (productChoice == 0) {
                addingItems = false;
            } else {
                int quantity = helper.readInt("Количество товаров:", 1, 1000);
                Menu.Product product = Menu.getProduct(productChoice);
                items.add(new OrderItem(product.name(), quantity, product.price()));
                System.out.println("Товар добавлен.");
            }
        }
        if (items.isEmpty()) {
            System.out.println("Заказ не создан, тк товары не выбраны.");
            return;
        }
        Order order = service.createOrder(client, method);
        for (OrderItem item : items) {
            service.addItemToOrder(order, item);
        }
        System.out.println("\nЗаказ создан:\n" + order.getOrderInfo());
    }

    private DeliveryMethod chooseDeliveryMethod(){
        Menu.showDelivery();
        int choice = helper.readInt("Выберите доставку:", 1, 3);
        return switch (choice) {
            case 1 -> new CourierDelivery(250);
            case 2 -> new ExpressDelivery(500);
            default -> new Pickup();
        };
    }

    public void assignCourier(){
        List<Order> orders = service.getOrders();
        if(orders.isEmpty()){
            System.out.println("Нет заказов");
            return;
        }
        try {
            service.showAllOrders();
            int number = helper.readInt("Введите номер заказа:", 1, 1000);
            Order order = findOrder(number);

            service.showAllCouriers();
            int num = helper.readInt("Введите номер курьера:", 1, service.getCouriers().size()) - 1;
            service.assignCourierToOrder(order, service.getCouriers().get(num));
            System.out.println("Курьер назначен.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }

    public void changeStatus(){
        if (service.getOrders().isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        try{
            service.showAllOrders();
            int number = helper.readInt("Номер заказа:", 1, 1000);
            Order order = findOrder(number);

            System.out.println("Выберите новый статус:");
            System.out.println("1. В доставке");
            System.out.println("2. Доставлен");
            System.out.println("3. Отменён");
            int choice = helper.readInt("Ваш выбор: ", 1, 3);
            OrderStatus status = switch (choice) {
                case 1 -> OrderStatus.IN_DELIVERY;
                case 2 -> OrderStatus.DELIVERED;
                default -> OrderStatus.CANCELLED;
            };
            service.changeOrderStatus(order, status);
            System.out.println("Статус изменён: " + order.getStatus());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public void showOrderByNumber() {
        if (service.getOrders().isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        try {
            int number = helper.readInt("Введите номер заказа: ", 1, 1000);
            System.out.println(findOrder(number).getOrderInfo());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private Order findOrder(int number){
        for (Order order : service.getOrders())
            if (order.getNumber() == number) return order;
        throw new IllegalArgumentException("Заказ с таким номером не найден.");
    }

}
