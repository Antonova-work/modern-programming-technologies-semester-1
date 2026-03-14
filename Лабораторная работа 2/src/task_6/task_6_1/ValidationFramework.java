package task_6.task_6_1;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Часть А: Аннотация TestInfo
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface TestInfo {
    String author();
    String date();
    String description() default "";
    int priority() default 5;
}

// Часть В: Аннотации для валидации
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotNull {
    String message() default "Поле не может быть пустым";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Range {
    int min();
    int max();
    String message() default "Значение вне допустимого диапазона";
}

// Класс RegistrationForm с аннотированными полями
class RegistrationForm {
    @NotNull(message = "Имя обязательно")
    private String name;

    @NotNull
    private String email;

    @Range(min = 18, max = 120, message = "Возраст должен быть от 18 до 120")
    private int age;

    public RegistrationForm(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}

// Класс Validator с методом validate
class Validator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            try {
                // Проверка @NotNull
                if (field.isAnnotationPresent(NotNull.class)) {
                    Object value = field.get(obj);
                    NotNull annotation = field.getAnnotation(NotNull.class);

                    if (value == null || (value instanceof String && ((String) value).isEmpty())) {
                        errors.add(annotation.message());
                    }
                }

                // Проверка @Range
                if (field.isAnnotationPresent(Range.class)) {
                    Object value = field.get(obj);
                    Range annotation = field.getAnnotation(Range.class);

                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        if (intValue < annotation.min() || intValue > annotation.max()) {
                            errors.add(annotation.message());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Ошибка доступа к полю: " + field.getName());
            }
        }

        return errors;
    }
}

// Тестовый класс с примером использования
public class ValidationFramework {
    public static void main(String[] args) {
        RegistrationForm valid = new RegistrationForm("Иван", "ivan@mail.ru", 25);
        RegistrationForm invalid = new RegistrationForm("", null, 15);

        System.out.println("=== Валидация корректной формы ===");
        List<String> errors1 = Validator.validate(valid);
        System.out.println(errors1.isEmpty() ? "Все поля валидны!" : errors1);

        System.out.println("\n=== Валидация некорректной формы ===");
        List<String> errors2 = Validator.validate(invalid);
        errors2.forEach(e -> System.out.println(" - " + e));
    }
}