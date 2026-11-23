import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static final ServiceMenu sv = new ServiceMenu();

    public static void main(String[] args){
        sv.addCouriers();

        boolean isRunning = true;
        System.out.println("Добро пожаловать!\n");

        while(isRunning){
            Menu.printMenu();
            int choice = sv.getHelp().readInt("Выберите действие: ", 0, 6 );

            switch (choice){
                case 1 -> sv.createNewOrder();
                case 2 -> sv.MenuCall(2);
                case 3 -> sv.assignCourier();
                case 4 -> sv.changeStatus();
                case 5 -> sv.MenuCall(5);
                case 6 -> sv.showOrderByNumber();
                case 0 -> isRunning = false;
                default -> System.out.println("Неверный пункт меню.");
            }
        }
        System.out.println("Программа завершена.");
    }
}
