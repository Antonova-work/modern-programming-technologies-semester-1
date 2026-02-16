package lessons.lesson12_map;

import java.util.HashMap;

public class PhoneBook {
    public static void main(String[] args) {
        HashMap<String, String> phoneBook = new HashMap<>();

        phoneBook.put("Мама", "+79778345623");
        phoneBook.put("Папа", "+79779345623");
        phoneBook.put("Брат", "+79774345623");

        System.out.println("Номер телефона мамы: " + phoneBook.get("Мама"));

        phoneBook.put("Мама", "+79998887766");
        System.out.println("Новый номер телефона мамы: " + phoneBook.get("Мама"));
        System.out.println("Номеров в телефонной книге: " + phoneBook.size());

    }
}
