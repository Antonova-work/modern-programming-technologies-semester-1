package task_2.task_2_2;

// 1. Sealed интерфейс PaymentMethod
sealed interface PaymentMethod permits CreditCard, BankTransfer, CryptoWallet {
    String process(double amount);
    double fee(double amount);
}

// 2. Record CreditCard
record CreditCard(String cardNumber, String holder) implements PaymentMethod {

    @Override
    public String process(double amount) {
        // Получаем последние 4 цифры номера карты
        String lastFour = cardNumber.length() >= 4 ?
                cardNumber.substring(cardNumber.length() - 4) :
                cardNumber;
        return String.format("Оплата картой *%s: %.0f руб.", lastFour, amount);
    }

    @Override
    public double fee(double amount) {
        return amount * 0.02; // 2% комиссия
    }
}

// 3. Record BankTransfer
record BankTransfer(String bankName, String iban) implements PaymentMethod {

    @Override
    public String process(double amount) {
        return String.format("Перевод через %s: %.0f руб.", bankName.toUpperCase(), amount);
    }

    @Override
    public double fee(double amount) {
        return 50.0; // Фиксированная комиссия 50 руб.
    }
}

// 4. Record CryptoWallet
record CryptoWallet(String address, String currency) implements PaymentMethod {

    @Override
    public String process(double amount) {
        return String.format("Криптоплатёж (%s): %.0f руб.", currency, amount);
    }

    @Override
    public double fee(double amount) {
        return amount * 0.015; // 1.5% комиссия
    }
}

// 5. Класс PaymentProcessor со статическим методом describe
class PaymentProcessor {
    public static void describe(PaymentMethod pm) {
        // Используем switch с паттерн-матчингом (Java 21+)
        String description = switch (pm) {
            case CreditCard c -> String.format("  Кредитная карта: %s, владелец: %s",
                    maskCardNumber(c.cardNumber()), c.holder());
            case BankTransfer b -> String.format("  Банковский перевод: %s, IBAN: %s",
                    b.bankName(), maskIban(b.iban()));
            case CryptoWallet w -> String.format("  Криптокошелёк: %s, валюта: %s",
                    maskAddress(w.address()), w.currency());
        };
        System.out.println(description);
    }

    // Вспомогательные методы для маскирования данных
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber.length() <= 4) return cardNumber;
        return "*" + cardNumber.substring(cardNumber.length() - 4);
    }

    private static String maskIban(String iban) {
        if (iban.length() <= 4) return iban;
        return iban.substring(0, 2) + "****" + iban.substring(iban.length() - 4);
    }

    private static String maskAddress(String address) {
        if (address.length() <= 6) return address;
        return address.substring(0, 6) + "..." + address.substring(address.length() - 4);
    }
}

// Главный класс для демонстрации
public class PaymentDemo {
    public static void main(String[] args) {
        PaymentMethod[] payments = {
                new CreditCard("4111222233334444", "Иван Петров"),
                new BankTransfer("Сбербанк", "RU1234567890"),
                new CryptoWallet("0xABC123DEF456", "BTC")
        };

        double amount = 10000;
        for (PaymentMethod pm : payments) {
            System.out.println(pm.process(amount));
            System.out.printf(" Комиссия: %.2f руб.%n", pm.fee(amount));
            PaymentProcessor.describe(pm);
            System.out.println();
        }
    }
}