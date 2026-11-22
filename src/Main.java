import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Helper helper = new Helper(scanner);
    private static final OrderService service = new OrderService();
    private static final ServiceMenu sv = new ServiceMenu(service, scanner, helper);

    public static void main(String[] args){
        service.addCourier(new Courier("Johnson The Rock"));
        service.addCourier(new Courier("Dinosaur"));
        service.addCourier(new Courier("Penguin"));

        boolean isRunning = true;
        System.out.println("Добро пожаловать!\n");

        while(isRunning){
            Menu.printMenu();
            int choice = helper.readInt("Выберите действие: ", 0, 6 );

            switch (choice){
                case 1 -> sv.createNewOrder();
                case 2 -> service.showAllOrders();
                case 3 -> sv.assignCourier();
                case 4 -> sv.changeStatus();
                case 5 -> service.showAllCouriers();
                case 6 -> sv.showOrderByNumber();
                case 0 -> isRunning = false;
                default -> System.out.println("Неверный пункт меню.");
            }
        }
        System.out.println("Программа завершена.");
    }
}
