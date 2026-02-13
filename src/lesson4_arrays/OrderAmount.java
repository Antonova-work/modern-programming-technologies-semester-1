package lesson4_arrays;

public class OrderAmount {
    public static void main(String[] args) {
        double[] items = {100.0, 250.0, 500.0};
        double totalSum = 0;
        for (int i = 0; i < items.length; i++) {
            totalSum += items[i];
        }
        System.out.println("Сумма заказа:" + totalSum);
    }
}
