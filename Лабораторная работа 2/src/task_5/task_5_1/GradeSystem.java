package task_5.task_5_1;

import java.util.*;

// 1. Enum Grade
enum Grade {
    A("Отлично", 4.0),
    B("Хорошо", 3.0),
    C("Удовлетворительно", 2.0),
    D("Неудовлетворительно", 1.0),
    F("Провал", 0.0);

    private final String description;
    private final double gpaValue;

    // Конструктор enum
    Grade(String description, double gpaValue) {
        this.description = description;
        this.gpaValue = gpaValue;
    }

    // Геттеры
    public String getDescription() {
        return description;
    }

    public double getGpaValue() {
        return gpaValue;
    }

    // Метод для проверки проходной оценки
    public boolean isPassing() {
        return this != F && this != D; // D и F считаются непроходными
    }

    // Статический метод для преобразования числовой оценки в Grade
    public static Grade fromScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Оценка должна быть от 0 до 100");
        }
        if (score >= 90) return A;
        if (score >= 80) return B;
        if (score >= 70) return C;
        if (score >= 60) return D;
        return F;
    }

    @Override
    public String toString() {
        return name() + " (" + description + ", GPA: " + gpaValue + ")";
    }
}

// 2. Record Student с компактным конструктором
record Student(String name, int id) {

    // Компактный конструктор с валидацией
    public Student {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым или null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным числом");
        }
        // Оставляем имя без лишних пробелов
        name = name.trim();
    }

    // Дополнительный метод для удобного вывода
    @Override
    public String toString() {
        return String.format("%s (ID: %d)", name, id);
    }
}

// 3. Главный класс
public class GradeSystem {

    public static void main(String[] args) {
        // Создаем студентов с числовыми оценками
        List<StudentScore> studentScores = Arrays.asList(
                new StudentScore(new Student("Анна Смирнова", 101), 95),
                new StudentScore(new Student("Иван Петров", 102), 82),
                new StudentScore(new Student("Мария Иванова", 103), 78),
                new StudentScore(new Student("Петр Сидоров", 104), 65),
                new StudentScore(new Student("Ольга Козлова", 105), 45),
                new StudentScore(new Student("Дмитрий Новиков", 106), 91),
                new StudentScore(new Student("Елена Морозова", 107), 73)
        );

        // Используем EnumMap для группировки студентов по оценкам
        EnumMap<Grade, List<Student>> gradeMap = new EnumMap<>(Grade.class);

        // Инициализируем пустые списки для всех оценок
        for (Grade grade : Grade.values()) {
            gradeMap.put(grade, new ArrayList<>());
        }

        // Заполняем карту
        for (StudentScore ss : studentScores) {
            Grade grade = Grade.fromScore(ss.score);
            gradeMap.get(grade).add(ss.student);
        }

        // Создаем EnumSet проходных оценок
        EnumSet<Grade> passingGrades = EnumSet.of(Grade.A, Grade.B, Grade.C);
        // Альтернативный способ через фильтрацию:
        // EnumSet<Grade> passingGrades = EnumSet.allOf(Grade.class);
        // passingGrades.removeIf(grade -> !grade.isPassing());

        // Выводим сводку
        System.out.println("=== СИСТЕМА ОЦЕНОК ===\n");

        double totalGpa = 0;
        int totalStudents = 0;

        for (Grade grade : Grade.values()) {
            List<Student> students = gradeMap.get(grade);
            int count = students.size();
            totalGpa += grade.getGpaValue() * count;
            totalStudents += count;

            System.out.printf("%s: %d студент(ов)%n", grade, count);
            if (!students.isEmpty()) {
                for (Student s : students) {
                    System.out.println("  - " + s);
                }
            } else {
                System.out.println("  - нет студентов");
            }
            System.out.println();
        }

        // Выводим информацию о проходных оценках
        System.out.println("=== ПРОХОДНЫЕ ОЦЕНКИ ===");
        System.out.println("Проходные оценки: " + passingGrades);

        // Подсчитываем средний GPA
        double averageGpa = totalStudents > 0 ? totalGpa / totalStudents : 0;
        System.out.printf("%nСредний GPA всех студентов: %.2f%n", averageGpa);

        // Подробная статистика
        System.out.println("\n=== ДЕТАЛЬНАЯ СТАТИСТИКА ===");
        for (Grade grade : Grade.values()) {
            List<Student> students = gradeMap.get(grade);
            if (!students.isEmpty()) {
                System.out.printf("%s: %.2f (%d студ.)%n",
                        grade.name(), grade.getGpaValue(), students.size());
            }
        }

        // Статистика по проходным/непроходным оценкам
        int passingCount = 0;
        int failingCount = 0;
        for (Grade grade : Grade.values()) {
            if (grade.isPassing()) {
                passingCount += gradeMap.get(grade).size();
            } else {
                failingCount += gradeMap.get(grade).size();
            }
        }
        System.out.printf("%nПроходных оценок: %d, Непроходных: %d%n",
                passingCount, failingCount);
    }

    // Вспомогательный класс для хранения студента и его числовой оценки
    private static class StudentScore {
        Student student;
        int score;

        StudentScore(Student student, int score) {
            this.student = student;
            this.score = score;
        }
    }
}