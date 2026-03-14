package task_2.task_2_3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Интерфейс Loggable с default, static и private методами
interface Loggable {
    // Абстрактный метод
    String getComponentName();

    // Private метод для форматирования времени (доступен только внутри интерфейса)
    private String formatTimestamp() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return now.format(formatter);
    }

    // Default метод для обычного логирования
    default void log(String message) {
        System.out.printf("[%s] [%s] %s%n",
                formatTimestamp(), getComponentName(), message);
    }

    // Default метод для логирования ошибок
    default void logError(String message) {
        System.out.printf("[%s] [%s] ОШИБКА: %s%n",
                formatTimestamp(), getComponentName(), message);
    }

    // Static метод
    static String getLogLevel() {
        return "INFO";
    }
}

// Класс DatabaseService
class DatabaseService implements Loggable {

    @Override
    public String getComponentName() {
        return "DatabaseService";
    }

    public void connect(String url) {
        log("Подключение к " + url);
        // Симуляция подключения
        log("Подключение установлено");
    }
}

// Класс AuthService
class AuthService implements Loggable {

    @Override
    public String getComponentName() {
        return "AuthService";
    }

    public void login(String username, boolean success) {
        if (success) {
            log("Вход пользователя: " + username + " – успешно");
        } else {
            logError("Вход пользователя: " + username + " – отказано");
        }
    }
}

// Главный класс для демонстрации
public class LoggableDemo {
    public static void main(String[] args) throws InterruptedException {
        DatabaseService db = new DatabaseService();
        AuthService auth = new AuthService();

        System.out.println("Уровень логирования: " + Loggable.getLogLevel());
        System.out.println();

        db.connect("jdbc:postgresql://localhost/mydb");
        System.out.println();

        auth.login("admin", true);

        // Небольшая задержка, чтобы время отличалось (опционально)
        // Thread.sleep(1000);

        auth.login("hacker", false);
    }
}