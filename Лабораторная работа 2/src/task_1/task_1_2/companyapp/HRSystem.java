package task_1.task_1_2.companyapp;

import task_1.task_1_2.companycore.EmployeeFixed;

public class HRSystem {
    public static void main(String[] args) {
        EmployeeFixed emp = new EmployeeFixed("Иван", 30, 80000, "secret");

        System.out.println(emp.getName());            // Строка A
        System.out.println(emp.getAge());             // Строка B
        System.out.println(emp.getSalary());          // Строка C
        System.out.println(emp.password);        // Строка D
        System.out.println(emp.getRole());       // Строка E
        emp.promote(5000);                       // Строка F
        emp.printSummary();                      // Строка G
        emp.validatePassword("secret");          // Строка H
    }
}
