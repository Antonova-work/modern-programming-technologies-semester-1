package task_1.task_1_1;

public class BankAccount {
    // Приватные поля экземпляра
    private String owner;
    private double balance;
    private String accountNumber;

    // Приватные статические поля
    private static int totalAccounts = 0;
    private static String bankName;

    // Статический блок инициализации
    static {
        bankName = "Java Bank";
        System.out.println("Банковская система инициализирована");
    }

    // Блок инициализации экземпляра
    {
        totalAccounts++;
        System.out.println("Создание счёта #" + totalAccounts);
    }

    // Конструктор
    public BankAccount(String owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
        this.accountNumber = "ACC-" + totalAccounts;
    }

    // Метод пополнения
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: сумма должна быть положительной");
            return;
        }
        balance += amount;
    }

    // Метод снятия
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Ошибка: сумма должна быть положительной");
            return;
        }
        if (amount > balance) {
            System.out.println("Ошибка: недостаточно средств");
            return;
        }
        balance -= amount;
    }

    // Статический метод получения количества счетов
    public static int getTotalAccounts() {
        return totalAccounts;
    }

    // Переопределённый метод toString()
    @Override
    public String toString() {
        return String.format("[%s] %s: %.2f руб.", accountNumber, owner, balance);
    }

    public static void main(String[] args) {
        BankAccount a1 = new BankAccount("Алиса", 1000);
        BankAccount a2 = new BankAccount("Борис", 500);

        System.out.println(a1);
        System.out.println(a2);

        a1.deposit(500);
        System.out.println("После пополнения: " + a1);

        a1.withdraw(200);
        System.out.println("После снятия: " + a1);

        a1.withdraw(5000);

        a2.deposit(-100);

        System.out.println("Всего счетов: " + BankAccount.getTotalAccounts());
    }
}
