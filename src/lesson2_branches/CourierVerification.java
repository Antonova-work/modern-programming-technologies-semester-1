package lesson2_branches;

public class CourierVerification {
    public static void main(String[] args) {
        boolean isFree = true;
        double ratingCourier = 4.5;
        if (isFree && (ratingCourier >= 4.5)) {
            System.out.println("Можно назначать заказ!");
        } else {
            System.out.println("Ищем другого курьера!");
        }
    }
}