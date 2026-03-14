package task_7.task_7_3;

import java.util.*;
import java.util.function.*;

public class TextPipeline {

    public static void main(String[] args) {
        System.out.println("=== ЧАСТЬ А: Композиция функций ===\n");

        // Создаем отдельные функции
        Function<String, String> trim = String::trim;
        Function<String, String> lower = String::toLowerCase;
        Function<String, String> removeExtraSpaces = s -> s.replaceAll("\\s+", " ");
        Function<String, String> capitalize = s -> {
            if (s == null || s.isEmpty()) return s;
            return s.substring(0, 1).toUpperCase() + s.substring(1);
        };

        // Композиция через andThen()
        Function<String, String> normalize = trim
                .andThen(lower)
                .andThen(removeExtraSpaces)
                .andThen(capitalize);

        // Тестируем на разных строках
        List<String> testStrings = Arrays.asList(
                "  привет   МИР   ",
                "этот   текст     содержит   много   пробелов  ",
                "  JAVA   Программирование  ",
                "один"
        );

        System.out.println("Результаты нормализации:");
        for (String s : testStrings) {
            System.out.printf("Исходная: \"%s\"%n", s);
            System.out.printf("Нормализованная: \"%s\"%n%n", normalize.apply(s));
        }

        System.out.println("\n=== ЧАСТЬ В: Локальный класс WordCounter ===\n");

        // Текст для анализа
        String text = "кот собака кот птица рыба кот собака рыба кот птица кот";
        String normalized = normalize.apply(text);
        System.out.println("Нормализованный текст: " + normalized);
        System.out.println();

        // Локальный класс WordCounter
        class WordCounter {
            private final String text;

            public WordCounter(String text) {
                this.text = text;
            }

            public Map<String, Integer> count() {
                if (text == null || text.trim().isEmpty()) {
                    return new HashMap<>();
                }

                String[] words = text.split("\\s+");
                Map<String, Integer> frequency = new HashMap<>();

                for (String word : words) {
                    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
                }

                return frequency;
            }

            public String mostFrequent() {
                Map<String, Integer> frequency = count();
                if (frequency.isEmpty()) {
                    return null;
                }

                return frequency.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
            }

            public void printStatistics() {
                Map<String, Integer> frequency = count();
                System.out.println("Частотный словарь:");
                frequency.entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));

                System.out.println("\nСамое частое слово: " + mostFrequent() +
                        " (встречается " + frequency.get(mostFrequent()) + " раз)");
            }
        }

        // Используем локальный класс
        WordCounter counter = new WordCounter(normalized);
        counter.printStatistics();

        System.out.println("\n=== Дополнительный тест с другим текстом ===\n");

        String anotherText = "яблоко груша яблоко банан груша яблоко апельсин банан яблоко";
        String normalizedAnother = normalize.apply(anotherText);

        WordCounter anotherCounter = new WordCounter(normalizedAnother);
        anotherCounter.printStatistics();

        System.out.println("\n=== Тест с пустой строкой ===\n");
        WordCounter emptyCounter = new WordCounter("");
        System.out.println("Частотный словарь для пустой строки: " + emptyCounter.count());
        System.out.println("Самое частое слово: " + emptyCounter.mostFrequent());
    }
}