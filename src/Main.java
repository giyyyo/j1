import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        OrderService service = new OrderService();

        //add some couriers
        service.addCourier(new Courier("Richie Tozier"));
        service.addCourier(new Courier("Timothee Shalamet"));
        service.addCourier(new Courier("Gerard Way"));

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        System.out.println("Добро подаловать!/n");

        //main cycle
        while(isRunning){
            System.out.println("--- МЕНЮ ---");
            System.out.println("1. Создать новый заказ");
//            System.out.println("4. Добавить товар в заказ");
            System.out.println("2. Назначить курьера на заказ");
//            System.out.println("5. Завершить доставку");
            System.out.println("3. Показать все заказы");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choiceStr = scanner.nextLine();
            int choice = 0;
            try{
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e){
                System.out.println("Пожалуйста, введите число!");
                continue;
            }

            switch (choice){
                case 1:
                    createNewOrder(scanner, service);
                    break;
//                case 4:
//                    addItemToExistingOrder(scanner, service);
//                    break;
                case 2:
                    assignCourier(scanner, service);
                    break;
//                case 5:
//                    completeOrder(canner, service);
//                    break;
                case 3:
                    service.showAllOrders();
                    break;
                case 0:
                    System.out.println("Программа завершена.");
                    break;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
        scanner.close();
    }

    private static void createNewOrder(Scanner scanner, OrderService service){
        System.out.println("--- Создание нового заказа ---");

        System.out.println("Введите имя клиента: ");
        String name = scanner.nextLine();

        System.out.println("Введите город: ");
        String city = scanner.nextLine();

        System.out.println("Введите улицу:");
        String street = scanner.nextLine();

        System.out.println("Введите номер дома:");
        int house = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите номер квартиры (0 если нет):");
        int appartment = Integer.parseInt(scanner.nextLine());

        Address address = new Address(city, street, house, appartment);
        Client client = new Client(name, address);

        System.out.println("Выберите способ доставки:");
        System.out.println("1. Самовывоз (бесплатно");
        System.out.println("2. Курьер (150 руб.)");
        System.out.println("3. Экспресс-доставка (300 руб.)");
        int deliveryChoice = Integer.parseInt(scanner.nextLine());

        DeliveryMethod deliveryMethod = null;
        switch(deliveryChoice) {
            case 1:
                deliveryMethod = new Pickup();
                break;
            case 2:
                deliveryMethod = new CourierDelivery(150);
                break;
            case 3:
                deliveryMethod = new ExpressDelivery(300);
                break;
            default:
                System.out.println("Неверный пункт меню.");
                return;
        }

        Order order = service.createOrder(client, deliveryMethod);

        boolean addingItems = true;
        while(addingItems){
            System.out.println("Добавить товар в заказ (1 - да, 0 - нет, перейти к оформлению)");
            int addChoice = Integer.parseInt(scanner.nextLine());

            if(addChoice == 1){
                System.out.println("Введите название товара: ");
                String productName = scanner.nextLine();

                System.out.println("Введите количество товара: ");
                int quantity = Integer.parseInt(scanner.nextLine());

                System.out.println("Введите цену за штуку: ");
                int price = Integer.parseInt(scanner.nextLine());

                OrderItem item = new OrderItem(productName, quantity, price);
                order.addItem(item);
                System.out.println("Товар " + productName + " был добавлен.");
            } else{
                addingItems = false;
            }
        }
        System.out.println("Заказ успешно оформлен! Итоговая сумма: " + order.calcTotalCost() + " руб.");
    }

    private static void assignCourier(Scanner scanner, OrderService service){
        List<Order> orders = service.getOrders();
        List<Courier> couriers = service.getCouriers();

        if(orders.isEmpty()){
            System.out.println("Нет заказов для назначения курьера");
            return;
        }

        System.out.println("Выберите заказ: ");
        for(int i =0; i < orders.size(); i++){
            System.out.println((i+1) + " . Заказ для: " + orders.get(i).getClient().getName() + "/n Статус: " + orders.get(i).getStatus());
        }
        int orderIndex = Integer.parseInt(scanner.nextLine())-1;

        System.out.println("Выберите курьера: ");
        for (int i=0; i < couriers.size(); i++){
            String status = couriers.get(i).isBisy()? " занят": " свободен";
            System.out.println((i+1) + ". " + couriers.get(i).getName() + status);
        }
        int courierIndex = Integer.parseInt(scanner.nextLine())-1;

        if (orderIndex >= 0 && orderIndex <= orders.size() && courierIndex >= 0 && courierIndex <= couriers.size()){
            service.assignCourierToOrder(orders.get(orderIndex), couriers.get(courierIndex));
        } else {
            System.out.println("Неверный номер заказа или курьера.");
        }
    }
}
