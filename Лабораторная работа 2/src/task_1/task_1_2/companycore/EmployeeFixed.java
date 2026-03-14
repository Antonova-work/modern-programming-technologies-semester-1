package task_1.task_1_2.companycore;

public class EmployeeFixed {
    // Все поля private
    private String name;
    private int age;
    private double salary;
    public String password;

    // Конструктор
    public EmployeeFixed(String name, int age, double salary, String password) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.password = password;
    }

    // Геттеры (без геттера для password)
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    // Публичный метод promote
    public void promote(double raise) {
        if (raise > 0) {
            this.salary += raise;
        }
    }

    // Публичный метод для вывода информации
    public void printSummary() {
        System.out.println(name + ", " + age + " лет, " + salary + " руб.");
    }

    // Публичный метод получения роли
    public String getRole() {
        return "Employee";
    }

    // Публичный метод аутентификации
    public boolean authenticate(String input) {
        return validatePassword(input);
    }

    // Приватный метод валидации пароля
    public boolean validatePassword(String input) {
        return password != null && password.equals(input);
    }
}