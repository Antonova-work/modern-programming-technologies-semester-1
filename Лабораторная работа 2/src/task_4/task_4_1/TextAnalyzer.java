package task_4.task_4_1;

public class TextAnalyzer {
    private String text;

    // Конструктор
    public TextAnalyzer(String text) {
        this.text = text;
    }

    // Переопределенный toString для удобства
    @Override
    public String toString() {
        return text;
    }

    // 1. Метод для подсчета количества слов
    public int wordCount() {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        // Разделяем по пробелам и удаляем пустые элементы
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    // 2. Метод для поиска самого длинного слова
    public String longestWord() {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }

        String[] words = text.trim().split("\\s+");
        String longest = "";

        for (String word : words) {
            // Удаляем знаки препинания для сравнения длины
            String cleanWord = word.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "");
            if (cleanWord.length() > longest.length()) {
                longest = cleanWord;
            }
        }

        return longest;
    }

    // 3. Метод для обратного порядка слов
    public String reverseWords() {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        String[] words = text.trim().split("\\s+");
        StringBuilder reversed = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]);
            if (i > 0) {
                reversed.append(" ");
            }
        }

        return reversed.toString();
    }

    // 4. Метод для подсчета вхождений подстроки (без учета регистра)
    public int countOccurrences(String target) {
        if (text == null || target == null || target.isEmpty()) {
            return 0;
        }

        String lowerText = text.toLowerCase();
        String lowerTarget = target.toLowerCase();

        int count = 0;
        int index = 0;

        while ((index = lowerText.indexOf(lowerTarget, index)) != -1) {
            count++;
            index += lowerTarget.length();
        }

        return count;
    }

    // 5. Метод для проверки на палиндром
    public boolean isPalindrome() {
        if (text == null || text.isEmpty()) {
            return false;
        }

        // Оставляем только буквы (русские и английские)
        String cleaned = text.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "").toLowerCase();

        if (cleaned.isEmpty()) {
            return false;
        }

        // Проверяем, является ли строка палиндромом
        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    // Альтернативная реализация isPalindrome с использованием StringBuilder
    public boolean isPalindromeBuilder() {
        if (text == null || text.isEmpty()) {
            return false;
        }

        String cleaned = text.replaceAll("[^a-zA-Zа-яА-ЯёЁ]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();

        return cleaned.equals(reversed);
    }

    public static void main(String[] args) {
        TextAnalyzer ta = new TextAnalyzer("Java Programming is Fun and Java is Powerful");

        System.out.println("Текст: " + ta);
        System.out.println("Слов: " + ta.wordCount());
        System.out.println("Самое длинное слово: " + ta.longestWord());
        System.out.println("Слова наоборот: " + ta.reverseWords());
        System.out.println("'Java' встречается: " + ta.countOccurrences("java") + " раз(a)");
        System.out.println("'is' встречается: " + ta.countOccurrences("is") + " раз(a)");
        System.out.println("Палиндром: " + ta.isPalindrome());

        System.out.println();

        TextAnalyzer palindrome = new TextAnalyzer("А роза упала на лапу Азора");
        System.out.println("Текст: " + palindrome);
        System.out.println("Палиндром: " + palindrome.isPalindrome());

        // Дополнительные тесты
        System.out.println("\n=== Дополнительные тесты ===");

        TextAnalyzer test1 = new TextAnalyzer("Мадам");
        System.out.println("Текст: " + test1);
        System.out.println("Палиндром: " + test1.isPalindrome());

        TextAnalyzer test2 = new TextAnalyzer("Hello World");
        System.out.println("\nТекст: " + test2);
        System.out.println("Палиндром: " + test2.isPalindrome());

        TextAnalyzer test3 = new TextAnalyzer("  Много   пробелов   между   словами  ");
        System.out.println("\nТекст: " + test3);
        System.out.println("Слов: " + test3.wordCount());
        System.out.println("Слова наоборот: " + test3.reverseWords());
    }
}