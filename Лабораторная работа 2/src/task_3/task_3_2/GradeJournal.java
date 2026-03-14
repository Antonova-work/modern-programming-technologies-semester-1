package task_3.task_3_2;

import java.util.Arrays;

class Student {
    private String name;
    private int[] grades;

    public Student(String name, int[] grades) {
        this.name = name;
        this.grades = grades.clone(); // Защитная копия
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return grades.clone(); // Защитная копия
    }

    public int getGradeCount() {
        return grades.length;
    }

    public double getAverage() {
        if (grades.length == 0) return 0;
        return Arrays.stream(grades).average().orElse(0);
    }

    public int getMax() {
        return Arrays.stream(grades).max().orElse(0);
    }

    public int getMin() {
        return Arrays.stream(grades).min().orElse(0);
    }

    @Override
    public String toString() {
        return String.format("%s | Оценок: %d | Средний: %.2f | Мин: %d | Макс: %d",
                name, grades.length, getAverage(), getMin(), getMax());
    }
}

public class GradeJournal {
    public static void main(String[] args) {
        Student[] students = {
                new Student("Алиса", new int[]{5, 4, 5, 5, 3}),
                new Student("Борис", new int[]{3, 3, 4}),
                new Student("Вера", new int[]{5, 5, 5, 5, 5, 4}),
                new Student("Глеб", new int[]{4, 3, 4, 5})
        };

        System.out.println("=== Журнал оценок ===\n");

        Student bestStudent = students[0];
        for (Student s : students) {
            System.out.println(s);
            if (s.getAverage() > bestStudent.getAverage()) {
                bestStudent = s;
            }
        }

        System.out.printf("%nЛучший студент: %s (средний балл: %.2f)%n",
                bestStudent.getName(), bestStudent.getAverage());
    }
}