package task_1.task_1_3;

import java.util.ArrayList;
import java.util.Arrays;

public class VarDemo {
    // 1. НЕ КОМПИЛИРУЕТСЯ: var нельзя использовать для полей класса
    // var field = 10; // Ошибка: 'var' is not allowed here

    public static void main(String[] args) {
        // РАБОЧИЕ ПРИМЕРЫ С VAR

        // Пример 1: примитивный тип int
        var number = 42;
        System.out.println(number + " -> " + ((Object) number).getClass().getSimpleName());

        // Пример 2: String
        var text = "Java";
        System.out.println(text + " -> " + text.getClass().getSimpleName());

        // Пример 3: ArrayList<String>
        var list = new ArrayList<String>();
        list.add("один");
        list.add("два");
        System.out.println(list + " -> " + list.getClass().getSimpleName());

        // Пример 4: массив int[]
        var array = new int[]{1, 2, 3};
        System.out.println(Arrays.toString(array) + " -> " + array.getClass().getSimpleName());

        // Пример 5: собственный объект BankAccount
        var account = new task_1.task_1_1.BankAccount("Алиса", 1000);
        System.out.println(account + " -> " + account.getClass().getSimpleName());
    }

    // 2. НЕ КОМПИЛИРУЕТСЯ: var как параметр метода
    // public void method(var param) { } // Ошибка: 'var' is not allowed here

    // Метод для демонстрации ограничений (закомментирован, чтобы код компилировался)
    /*
    public void demonstrateErrors() {
        // 3. НЕ КОМПИЛИРУЕТСЯ: var без инициализации
        // var uninitialized; // Ошибка: cannot infer type
        
        // 4. НЕ КОМПИЛИРУЕТСЯ: var с null (компилятор не может определить тип)
        // var nullValue = null; // Ошибка: variable initializer is 'null'
        
        // 5. НЕ КОМПИЛИРУЕТСЯ: var с лямбда-выражением (требуется явный тип)
        // var lambda = x -> x * 2; // Ошибка: cannot infer type
    }
    */
}