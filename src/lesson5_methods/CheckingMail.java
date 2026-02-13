package lesson5_methods;

public class CheckingMail {
    public static boolean isEmailValid(String email){
        return !email.isEmpty();
    }

    public static void main(String[] args) {
        boolean check = isEmailValid("test@yandex.ru");
        System.out.println(check);
    }
}
