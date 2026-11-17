import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderService service = new OrderService();

    public static void main(String[] args){
        //add some couriers
        service.addCourier(new Courier("Johnson The Rock"));
        service.addCourier(new Courier("Dinosaur"));
        service.addCourier(new Courier("Penguin"));

        boolean isRunning = true;
        System.out.println("Добро пожаловать!\n");

        //main cycle
        while(isRunning){
            printMenu();
            int choice = readInt("Выберите действие: ", 0, 6 );

            switch (choice){
                case 1 -> createNewOrder();
                case 2 -> service.showAllOrders();
                case 3 -> assignCourier();
                case 4 -> changeStatus();
                case 5 -> service.showAllCouriers();
                case 6 -> showOrderByNumber();
                case 0 -> isRunning = false;
                default -> System.out.println("Неверный пункт меню.");
            }
        }
        System.out.println("Программа завершена.");
    }

    private static void printMenu(){
        System.out.println("--- МЕНЮ ---");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Показать все заказы");
        System.out.println("3. Назначить курьера на заказ");
        System.out.println("4. Изенить статус заказа");
        System.out.println("5. Показать курьера");
//        System.out.println("6. Найти заказ по номеру");
        System.out.println("0. Выход");
    }

    private static void createNewOrder() {
        System.out.println("--- Создание нового заказа ---");

        System.out.println("Введите имя клиента: ");
        String name = readLine();

        System.out.println("Введите город: ");
        String city = readLine();

        System.out.println("Введите улицу:");
        String street = readLine();

        System.out.println("Введите дом:");
        String house = readLine();

        System.out.print("Квартира: ");
        String apartment = scanner.nextLine();

        Client client = new Client(name, new Address(city, street, house, apartment));
        DeliveryMethod method = chooseDeliveryMethod();
        List<OrderItem> items = new ArrayList<>();

        boolean addingItems = true;
        while (addingItems) {
            showProductMenu();
            int productChoice = readInt("Товар:", 0, 5);
            if (productChoice == 0) {
                addingItems = false;
            } else {
                int quantity = readInt("Количество товаров:", 1, 1000);
                Product product = getProduct(productChoice);
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

    private static DeliveryMethod chooseDeliveryMethod(){
        System.out.println("Способ доставки:");
        System.out.println("1. Стандартная курьерская доставка — 250 руб.");
        System.out.println("2. Экспресс-доставка — 500 руб.");
        System.out.println("3. Самовывоз — бесплатно");
        int choice = readInt("Выберите доставку:", 1, 3);
        return switch (choice) {
            case 1 -> new CourierDelivery(250);
            case 2 -> new ExpressDelivery(500);
            default -> new Pickup();
        };
    }

    private static void showProductMenu(){
        System.out.println("\nКаталог товаров:");
        System.out.println("1. Кофе — 250 руб.");
        System.out.println("2. Чай — 180 руб.");
        System.out.println("3. Шоколад — 120 руб.");
        System.out.println("4. Печенье — 90 руб.");
        System.out.println("5. Сок — 150 руб.");
        System.out.println("0. Закончить выбор товаров.");
    }

    private record Product(String name, double price) {}
//    !!!!!!!!!!!! up

    private static Product getProduct(int choice){
        return switch (choice) {
            case 1 -> new Product("Кофе", 250);
            case 2 -> new Product("Чай", 180);
            case 3 -> new Product("Шоколад", 120);
            case 4 -> new Product("Печенье", 90);
            case 5 -> new Product("Сок", 150);
            default -> throw new IllegalArgumentException("Такого товара нет в каталоге.");
        };
    }

    private static void assignCourier(){
        List<Order> orders = service.getOrders();
        if(orders.isEmpty()){
            System.out.println("Нет заказов");
            return;
        }
        try {
            service.showAllOrders();
            int number = readInt("Введите номер заказа:", 1, 1000);
            Order order = findOrder(number);

            service.showAllCouriers();
            int num = readInt("Введите номер курьера:", 1, service.getCouriers().size()) - 1;
            service.assignCourierToOrder(order, service.getCouriers().get(num));
            System.out.println("Курьер назначен.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

    }

    private static void changeStatus(){
        if (service.getOrders().isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        try{
            service.showAllOrders();
            int number = readInt("Номер заказа:", 1, 1000);
            Order order = findOrder(number);

            System.out.println("Выберите новый статус:");
            System.out.println("1. В доставке");
            System.out.println("2. Доставлен");
            System.out.println("3. Отменён");
            int choice = readInt("Ваш выбор: ", 1, 3);
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

    private static void showOrderByNumber() {
        if (service.getOrders().isEmpty()) {
            System.out.println("Заказов пока нет.");
            return;
        }
        int number = readInt("Введите номер заказа: ", 1, 1000);
        System.out.println(findOrder(number).getOrderInfo());
    }

    private static Order findOrder(int number){
        for (Order order : service.getOrders())
            if (order.getNumber() == number) return order;
        throw new IllegalArgumentException("Заказ с таким номером не найден.");
    }

    private static int readInt(String str, int min, int max){
        while(true){
            if (!str.isEmpty()){ System.out.println(str); }
            String input = scanner.nextLine();
            try {
                int num = Integer.parseInt(input);
                if (num < min || num > max) {
                    System.out.println("Введите число от " + min + " до " + max + ".");
                    continue;
                }
                return num;
            } catch (NumberFormatException e){
                System.out.println("Некорректный ввод. Введите целое число.");
            }
        }
    }

    private static String readLine(){
        while(true){
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.print("Поле не должно быть пустым.");
        }
    }
}
