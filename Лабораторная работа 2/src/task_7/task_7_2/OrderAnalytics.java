package task_7.task_7_2;

import java.util.*;

import static java.util.stream.Collectors.*;

record Order(String customer, String product, double price, int quantity, String category) {
    double total() {
        return price * quantity;
    }
}

public class OrderAnalytics {
    public static void main(String[] args) {
        List<Order> orders = createOrders();

        System.out.println("=== ВСЕ ЗАКАЗЫ ===");
        orders.forEach(System.out::println);
        System.out.println();

        // 1. Заказы дороже 5000 руб.
        System.out.println("=== ЗАКАЗЫ ДОРОЖЕ 5000 РУБ. ===");
        orders.stream()
                .filter(o -> o.total() > 5000)
                .forEach(System.out::println);
        System.out.println();

        // 2. Уникальные имена клиентов (отсортированные)
        System.out.println("=== УНИКАЛЬНЫЕ КЛИЕНТЫ ===");
        List<String> uniqueCustomers = orders.stream()
                .map(Order::customer)
                .distinct()
                .sorted()
                .collect(toList());
        System.out.println(uniqueCustomers);
        System.out.println();

        // 3. Общая выручка
        System.out.println("=== ОБЩАЯ ВЫРУЧКА ===");
        double totalRevenue = orders.stream()
                .mapToDouble(Order::total)
                .sum();
        System.out.printf("%.2f руб.%n%n", totalRevenue);

        // 4. Самый дорогой заказ
        System.out.println("=== САМЫЙ ДОРОГОЙ ЗАКАЗ ===");
        Optional<Order> maxOrder = orders.stream()
                .max(Comparator.comparingDouble(Order::total));
        maxOrder.ifPresent(System.out::println);
        System.out.println();

        // 5. Количество заказов в каждой категории
        System.out.println("=== СТАТИСТИКА ПО КАТЕГОРИЯМ ===");
        Map<String, Long> categoryCount = orders.stream()
                .collect(groupingBy(Order::category, counting()));
        categoryCount.forEach((cat, count) -> System.out.println(cat + ": " + count));
        System.out.println();

        // 6. Средняя стоимость заказа по каждому клиенту
        System.out.println("=== СРЕДНЯЯ СТОИМОСТЬ ЗАКАЗА ПО КЛИЕНТАМ ===");
        Map<String, Double> avgByCustomer = orders.stream()
                .collect(groupingBy(Order::customer,
                        averagingDouble(Order::total)));
        avgByCustomer.forEach((customer, avg) ->
                System.out.printf("%s: %.2f руб.%n", customer, avg));
        System.out.println();

        // 7. Разделение на дорогие (total > 3000) и дешёвые
        System.out.println("=== РАЗДЕЛЕНИЕ НА ДОРОГИЕ (>3000) И ДЕШЁВЫЕ ===");
        Map<Boolean, List<Order>> partitioned = orders.stream()
                .collect(partitioningBy(o -> o.total() > 3000));

        System.out.println("Дорогие заказы:");
        partitioned.get(true).forEach(System.out::println);

        System.out.println("\nДешёвые заказы:");
        partitioned.get(false).forEach(System.out::println);
        System.out.println();

        // 8. Товары дороже 1000 руб., уникальные, в верхнем регистре
        System.out.println("=== ТОВАРЫ ДОРОЖЕ 1000 РУБ. (УНИКАЛЬНЫЕ, В ВЕРХНЕМ РЕГИСТРЕ) ===");
        List<String> expensiveProducts = orders.stream()
                .filter(o -> o.price() > 1000)
                .map(Order::product)
                .map(String::toUpperCase)
                .distinct()
                .collect(toList());
        System.out.println(expensiveProducts);
    }

    private static List<Order> createOrders() {
        return Arrays.asList(
                new Order("Иванов", "Ноутбук", 55000, 1, "Электроника"),
                new Order("Петров", "Мышь", 1500, 2, "Электроника"),
                new Order("Сидорова", "Книга", 800, 3, "Книги"),
                new Order("Иванов", "Телефон", 25000, 1, "Электроника"),
                new Order("Петров", "Клавиатура", 3500, 1, "Электроника"),
                new Order("Смирнова", "Книга", 1200, 2, "Книги"),
                new Order("Иванов", "Наушники", 4500, 1, "Электроника"),
                new Order("Сидорова", "Чехол", 800, 3, "Аксессуары"),
                new Order("Петров", "Монитор", 18000, 1, "Электроника"),
                new Order("Смирнова", "Коврик", 500, 2, "Аксессуары"),
                new Order("Козлов", "Планшет", 32000, 1, "Электроника"),
                new Order("Иванов", "Книга", 1500, 1, "Книги")
        );
    }
}