public class Menu {

    public static void printMenu(){
        System.out.println("--- МЕНЮ ---");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Показать все заказы");
        System.out.println("3. Назначить курьера на заказ");
        System.out.println("4. Изенить статус заказа");
        System.out.println("5. Показать курьеров");
        System.out.println("6. Найти заказ по номеру");
        System.out.println("0. Выход");
    }

    public static void showProductMenu(){
        System.out.println("\nКаталог товаров:");
        System.out.println("1. Кофе — 250 руб.");
        System.out.println("2. Чай — 180 руб.");
        System.out.println("3. Шоколад — 120 руб.");
        System.out.println("4. Печенье — 90 руб.");
        System.out.println("5. Сок — 150 руб.");
        System.out.println("6. Багет — 70 руб.");
        System.out.println("7. Мороженое — 210 руб.");
        System.out.println("8. Пирог — 180 руб.");
        System.out.println("9. Горячий шоколад — 220 руб.");
        System.out.println("10. Круассан — 160 руб.");
        System.out.println("11. Хот-дог — 190 руб.");
        System.out.println("12. Хачапури — 120 руб.");
        System.out.println("0. Закончить выбор товаров.");
    }

    public static void showDelivery(){
        System.out.println("Способ доставки:");
        System.out.println("1. Стандартная курьерская доставка — 250 руб.");
        System.out.println("2. Экспресс-доставка — 500 руб.");
        System.out.println("3. Самовывоз — бесплатно");
    }

    public record Product(String name, double price) {}

    public static Product getProduct(int choice){
        return switch (choice) {
            case 1 -> new Product("Кофе", 250);
            case 2 -> new Product("Чай", 180);
            case 3 -> new Product("Шоколад", 120);
            case 4 -> new Product("Печенье", 90);
            case 5 -> new Product("Сок", 150);
            case 6 -> new Product("Багет", 70);
            case 7 -> new Product("Мороженое", 210);
            case 8 -> new Product("Пирог", 180);
            case 9 -> new Product("Горячий шоколад", 220);
            case 10 -> new Product("Круассан", 160);
            case 11 -> new Product("Хот-дог", 190);
            case 12 -> new Product("Хачапури", 120);
            default -> throw new IllegalArgumentException("Такого товара нет в каталоге.");
        };
    }
}
