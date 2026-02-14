package projects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class DeliveryManagementSystem {
    public static void main(String[] args) {
        Order newOrder1 = new Order("Молоко", 100.24, 2);
        Order newOrder2 = new Order("Сметана", 0.99, -4);
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
    private int itemQuantity;

    public Order (String newItemName, double newItemPrice, int newItemQuantity) {
        this.itemName = newItemName;

        BigDecimal bd = BigDecimal.valueOf(newItemPrice);
        if (bd.scale() > 2) {
            this.itemPrice = bd.setScale(0, RoundingMode.DOWN).doubleValue();
            System.out.println("Cлишком много копеек. Цена товара '" + itemName + "' = " + this.itemPrice);
        } else {
            this.itemPrice = newItemPrice;
        }
        if (newItemQuantity < 0) {
            this.itemQuantity = 0;
        } else {
            this.itemQuantity = newItemQuantity;
        }
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
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
        if (order.getItemQuantity() > 0) {
            backpack.add(order);
        } else {
            System.out.println("Заказ '" + order.getItemName() + "' пуст. Добавьте хотябы один товар.");
        }
    }
    public void deliver (){
        System.out.println("Курьер " + name + " доставил следующие заказы:");

        for (Order item : backpack) {
            if (item.getItemQuantity() == 0) {
                continue;
            }
            total += (item.getItemPrice() * item.getItemQuantity());
            System.out.println(item.getItemName() + " - " + item.getItemPrice() + ", количество: "
                    + item.getItemQuantity() + ".");
        }
        total = Math.round(total * 100.0) / 100.0;
        System.out.println("Общая сумма заказа: " + total + " рублей.");
        total = 0;
        backpack.clear();
    }
}

