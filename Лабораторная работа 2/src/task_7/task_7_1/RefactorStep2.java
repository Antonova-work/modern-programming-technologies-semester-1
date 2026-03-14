package task_7.task_7_1;

import java.util.*;
import java.util.function.*;

// Этап 2: Замена лямбд на ссылки на методы
class RefactorStep2 {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Москва", "Берлин", "Токио", "Нью-Йорк", "Париж");

        // 1. Сортировка по длине - можно заменить на Comparator.comparingInt с ссылкой на метод
        cities.sort(Comparator.comparingInt(String::length));

        // 2. Вывод каждого элемента - можно заменить на ссылку на метод
        cities.forEach(System.out::println);

        // 3. Преобразование в верхний регистр - можно заменить на ссылку на метод
        Function<String, String> toUpper = String::toUpperCase;

        // 4. Проверка длины > 5 - НЕЛЬЗЯ заменить на ссылку на метод (требуется параметр 5)
        Predicate<String> isLong = s -> s.length() > 5;

        // 5. Формирование строки с восклицательным знаком - НЕЛЬЗЯ заменить (требуется конкатенация с "!")
        Function<String, String> exclaim = s -> s + "!";

        // 6. Создание нового списка - можно заменить на ссылку на конструктор
        Supplier<List<String>> listFactory = ArrayList::new;

        // Использование
        List<String> result = listFactory.get();
        for (String city : cities) {
            if (isLong.test(city)) {
                result.add(toUpper.apply(city));
            }
        }
        System.out.println("Длинные города: " + result);
    }
}

/*
 * Этап 3: Объяснение, какие лямбды нельзя заменить на ссылки на методы
 *
 * 1. isLong (s -> s.length() > 5) - нельзя заменить на ссылку на метод, потому что
 *    это составное выражение, включающее сравнение с константой. Ссылки на методы
 *    могут вызывать только существующий метод, но не могут содержать дополнительную логику.
 *
 * 2. exclaim (s -> s + "!") - нельзя заменить на ссылку на метод, потому что
 *    это операция конкатенации строк с добавлением символа "!". Нет существующего метода,
 *    который бы добавлял "!" к строке. concat можно использовать, но это не ссылка на метод,
 *    а вызов метода объекта, и даже с concat пришлось бы передавать параметр "!".
 */