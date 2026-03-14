package task_4.task_4_2;

public class StringPoolLab {
    public static void main(String[] args) {
        // Создание строк разными способами
        String s1 = "Hello";                    // литерал
        String s2 = "Hello";                    // литерал
        String s3 = new String("Hello");         // новый объект
        String s4 = new String("Hello");         // ещё один новый объект
        String s5 = s3.intern();                  // интернированная версия s3
        String s6 = "Hel" + "lo";                  // конкатенация литералов (на этапе компиляции)
        String half = "Hel";
        String s7 = half + "lo";                   // конкатенация с переменной (во время выполнения)

        System.out.println("=== Исследование String Pool ===\n");

        // === Сравнение s1 и s2 ===
        // Прогноз: true (оба литерала, ссылаются на один объект в String Pool)
        System.out.println("s1 vs s2:");
        System.out.println("  Прогноз: == true, .equals() true");
        System.out.println("  Результат: == " + (s1 == s2) + ", .equals() " + s1.equals(s2));
        System.out.println();

        // === Сравнение s1 и s3 ===
        // Прогноз: false (s1 в пуле, s3 в куче - разные объекты)
        System.out.println("s1 vs s3:");
        System.out.println("  Прогноз: == false, .equals() true");
        System.out.println("  Результат: == " + (s1 == s3) + ", .equals() " + s1.equals(s3));
        System.out.println();

        // === Сравнение s3 и s4 ===
        // Прогноз: false (два разных объекта в куче, созданных через new)
        System.out.println("s3 vs s4:");
        System.out.println("  Прогноз: == false, .equals() true");
        System.out.println("  Результат: == " + (s3 == s4) + ", .equals() " + s3.equals(s4));
        System.out.println();

        // === Сравнение s1 и s5 ===
        // Прогноз: true (s5 - интернированная версия s3, попадает в пул)
        System.out.println("s1 vs s5:");
        System.out.println("  Прогноз: == true, .equals() true");
        System.out.println("  Результат: == " + (s1 == s5) + ", .equals() " + s1.equals(s5));
        System.out.println();

        // === Сравнение s1 и s6 ===
        // Прогноз: true (конкатенация литералов выполняется на этапе компиляции)
        System.out.println("s1 vs s6:");
        System.out.println("  Прогноз: == true, .equals() true");
        System.out.println("  Результат: == " + (s1 == s6) + ", .equals() " + s1.equals(s6));
        System.out.println();

        // === Сравнение s1 и s7 ===
        // Прогноз: false (конкатенация с переменной создаёт новый объект в рантайме)
        System.out.println("s1 vs s7:");
        System.out.println("  Прогноз: == false, .equals() true");
        System.out.println("  Результат: == " + (s1 == s7) + ", .equals() " + s1.equals(s7));
        System.out.println();

        // === Сравнение StringBuilder и s1 ===
        StringBuilder sb = new StringBuilder();
        sb.append('H').append('e').append('l').append('l').append('o');
        String s8 = sb.toString();

        System.out.println("StringBuilder vs s1:");
        System.out.println("  Прогноз: == false, .equals() true");
        System.out.println("  Результат: == " + (s1 == s8) + ", .equals() " + s1.equals(s8));
        System.out.println();
    }
}