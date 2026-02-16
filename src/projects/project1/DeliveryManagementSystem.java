package projects.project1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class DeliveryManagementSystem {
    public static void main(String[] args) {
        //создаём курьера
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя курьера:");
        String courierName = scanner.nextLine();
        Courier newCourier = new Courier(courierName);

        //созадаём заказ (несколько товаров в цикле)
        String answer;
        BigDecimal orderPrice = BigDecimal.ZERO;
        int orderQuantity;

        do {
            System.out.println("Введите название товара:");
            String orderName = scanner.nextLine();

            System.out.println("Введите цену за 1 единицу товара:");
            //исключение для цены товара. Нельзя ввести буквы
            while (true) {
                try {
                    orderPrice = new BigDecimal(scanner.nextLine());
                    break;// BigDecimal для цены
                } catch (NumberFormatException e){
                    System.out.println("Неверный ввод. Используйте цифры.");
                    System.out.println("Введите цену за 1 единицу товара:");
                }

            }

            System.out.println("Введите колличество единиц товара:");
            //исключение для количества товара. Нельзя ввести буквы
            while (true) {
                try {
                    orderQuantity = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Неверный ввод. Используйте цифры.");
                    System.out.println("Введите колличество единиц товара:");
                }
            }

            //собираем позицию заказа
            Order newOrder1 = new Order(orderName, orderPrice, orderQuantity);

            //отдаём курьеру позицию
            newCourier.takeOrder(newOrder1);

            System.out.println("Добавить ещё один товар? (да/нет)");
            answer = scanner.nextLine();} while (!(answer.equals("нет")));

        //выводим информацию кто доставил и что
        newCourier.deliver();
    }
}

//интерфейс для доставщика (считать сумму заказа, выводить что доставил и сколько стоил заказ + проверка пустой ли рюкзак)
interface Deliverable {
    void deliver ();
}

class Order {
    private String itemName;
    private BigDecimal itemPrice;
    private int itemQuantity;

    //конструктор для создания заказа
    public Order (String newItemName, BigDecimal newItemPrice, int newItemQuantity) {
        this.itemName = newItemName;

        if (newItemQuantity <= 0) {
            this.itemQuantity = 0;
            System.out.println("Невозможное количество. Запишите товар заново");
        } else {
            this.itemQuantity = newItemQuantity;
        }

        //если цена меньше нуля, мы обнуляем и цену, и количество
        if (newItemPrice.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("Невозможная цена. Запишите товар заново.");
            this.itemPrice = BigDecimal.ZERO;
            this.itemQuantity = 0;
            return;
        }

        //берём только целую часть
        BigDecimal rubles = newItemPrice.setScale(0, RoundingMode.DOWN);

        //берём остаток от деления на 1 и двигаем запятую вправо на количество знаков после запятой
        BigDecimal kopecks = newItemPrice.remainder(BigDecimal.ONE).movePointRight(newItemPrice.scale());

        //получаем из копеек рубли и оставшиеся копейки
        BigDecimal kopecksAsRubles = kopecks.divide(new BigDecimal("100"));

        //складываем изначальные рубли и новые
        this.itemPrice = rubles.add(kopecksAsRubles);
    }

    //геттеры названия, цени и количества
    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }
}

//класс реализующий интерфейс Deliverable
class Courier implements Deliverable {
    private String name;
    //здесь могут лежать только объекты типа Order
    private ArrayList<Order> backpack;

    //конструктор создаём курьера имя + пустой рюкзак
    public Courier (String newName) {
        this.name = newName;
        this.backpack = new ArrayList<>();
    }

    //метод для "упаковки" всего заказа.
    //берём только заказы только из Order
    public void takeOrder (Order order) {
        //если количество 1 товара больше 0, то добавляем в рюкзак
        //если было добавлен товаров одного вида меньше 0 или 0, то сообщаем об этом
        if (order.getItemQuantity() > 0) {
            backpack.add(order);
        }
    }

    //метод из интерфейса
    public void deliver (){

        //если пустой рюкзак сообщаем и выходим из метода
        if (backpack.isEmpty()) {
            System.out.println("Курьер не брал заказа!");
            return;
        }

        //так как объект ставим 0 вот так
        BigDecimal total = BigDecimal.ZERO;

        System.out.println("Курьер " + name + " доставил следующие заказы:");


        for (Order item : backpack) {

            //умножаем через multiply на новый объект (по факту int, но BigDecimal
            BigDecimal itemTotal = (item.getItemPrice().multiply(new BigDecimal(item.getItemQuantity())));
            //так прибавляем потому что BigDecimal
            total = total.add(itemTotal);
            System.out.println(item.getItemName() + " - " + item.getItemPrice() + " руб." + ", количество: "
                    + item.getItemQuantity() + ".");
        }
        System.out.println("Общая сумма заказа: " + total.setScale(2, RoundingMode.HALF_UP) + " рублей.");
        backpack.clear();
    }
}