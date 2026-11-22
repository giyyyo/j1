import java.util.Scanner;

public class Helper {
    private final Scanner scanner;

    public Helper (Scanner scanner){
        this.scanner = scanner;

    }

    public String readLine(){
        while(true){
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.print("Поле не должно быть пустым.");
        }
    }

    public int readInt(String str, int min, int max){
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
}
