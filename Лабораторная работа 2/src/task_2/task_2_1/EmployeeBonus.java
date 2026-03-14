package task_2.task_2_1;

// Абстрактный класс Employee
abstract class Employee {
    protected String name;
    protected double baseSalary;

    // Конструктор
    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    // Абстрактный метод
    public abstract double calculateBonus();

    // Обычный метод
    public double totalCompensation() {
        return baseSalary + calculateBonus();
    }

    // Переопределённый toString()
    @Override
    public String toString() {
        return String.format("%s | Оклад: %.1f | Бонус: %.1f | Итого: %.1f",
                name, baseSalary, calculateBonus(), totalCompensation());
    }
}

// Класс Manager
class Manager extends Employee {
    private int teamSize;

    public Manager(String name, double baseSalary, int teamSize) {
        super(name, baseSalary);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return baseSalary * 0.15 + teamSize * 5000;
    }
}

// Класс Developer
class Developer extends Employee {
    private String language;

    public Developer(String name, double baseSalary, String language) {
        super(name, baseSalary);
        this.language = language;
    }

    @Override
    public double calculateBonus() {
        return baseSalary * 0.12;
    }
}

// Класс Intern
class Intern extends Employee {

    public Intern(String name, double baseSalary) {
        super(name, baseSalary);
    }

    @Override
    public double calculateBonus() {
        return 10000;
    }
}

// Главный класс для демонстрации
public class EmployeeBonus {
    public static void main(String[] args) {
        Employee[] team = {
                new Manager("Ольга", 120000, 5),
                new Developer("Андрей", 95000, "Java"),
                new Developer("Мария", 100000, "Python"),
                new Intern("Стажёр Петя", 30000)
        };

        System.out.println("=== Расчёт бонусов ===");
        double totalBudget = 0;
        for (Employee e : team) {
            System.out.println(e);
            totalBudget += e.totalCompensation();
        }

        System.out.printf("\nОбщий бюджет: %.0f руб.%n", totalBudget);
    }
}