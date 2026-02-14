package projects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class DeliveryManagementSystem {
    public static void main(String[] args) {
        Order newOrder1 = new Order("Молоко", 100.24);
        Order newOrder2 = new Order("Сметана", 0.99);
        Courier newCourier = new Courier("Саша");
        newCourier.takeOrder(newOrder1);
        newCourier.takeOrder(newOrder2);
        newCourier.deliver();
    }
}

interface Deliverable {
    void deliver ();
}

class Order {
    private String itemName;
    private double itemPrice;

    public Order (String newItemName, double newItemPrice) {
        this.itemName = newItemName;

        BigDecimal bd = BigDecimal.valueOf(newItemPrice);
        if (bd.scale() > 2) {
            this.itemPrice = bd.setScale(0, RoundingMode.DOWN).doubleValue();
            System.out.println("Cлишком много копеек. Цена товара '" + itemName + "' = " + this.itemPrice);
        } else {
            this.itemPrice = newItemPrice;
        }
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }
}

class Courier implements Deliverable {
    private String name;
    private ArrayList<Order> backpack;
    double total;

    public Courier (String newName) {
        this.name = newName;
        this.backpack = new ArrayList<>();
    }

    public void takeOrder (Order order) {
        backpack.add(order);
    }
    public void deliver (){
        System.out.println("Курьер " + name + " доставил следующие заказы:");

        for (Order item : backpack) {
            total += item.getItemPrice();
            System.out.println(item.getItemName() + " - " + item.getItemPrice());
        }
        total = Math.round(total * 100.0) / 100.0;
        System.out.println("Общая сумма заказа: " + total + " рублей.");
        total = 0;
        backpack.clear();
    }
}

