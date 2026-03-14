package task_8.task_8_1;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

// 1. enum Genre
enum Genre {
    FICTION("Художественная литература"),
    SCIENCE("Наука"),
    HISTORY("История"),
    PROGRAMMING("Программирование"),
    ART("Искусство");

    private final String russianName;

    Genre(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }

    public static Genre fromString(String name) {
        for (Genre genre : values()) {
            if (genre.russianName.equalsIgnoreCase(name)) {
                return genre;
            }
        }
        throw new IllegalArgumentException("Неизвестный жанр: " + name);
    }
}

// 2. record Book
record Book(String title, String author, int year, Genre genre, double price) {
    public Book {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Автор не может быть пустым");
        }
        int currentYear = Year.now().getValue();
        if (year < 1450 || year > currentYear) {
            throw new IllegalArgumentException("Год должен быть между 1450 и " + currentYear);
        }
        if (price < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной");
        }
    }
}

// 3. sealed interface LibraryItem
sealed interface LibraryItem extends Searchable permits PhysicalBook, EBook {
    String getInfo();
}

record PhysicalBook(Book book, String shelf) implements LibraryItem {
    @Override
    public String getInfo() {
        return String.format("[Физическая] %s, %s - Полка: %s",
                book.title(), book.author(), shelf);
    }
}

record EBook(Book book, String format, double sizeMB) implements LibraryItem {
    @Override
    public String getInfo() {
        return String.format("[Электронная] %s, %s - Формат: %s, %.1f MB",
                book.title(), book.author(), format, sizeMB);
    }
}

// 4. interface Searchable
interface Searchable {
    default boolean matches(String query) {
        return this.toString().toLowerCase().contains(query.toLowerCase());
    }

    static <T extends Searchable> List<T> search(List<T> items, String query) {
        return items.stream()
                .filter(item -> item.matches(query))
                .collect(Collectors.toList());
    }
}

// 5. Класс Library
class Library {
    private List<LibraryItem> items = new ArrayList<>();

    public void add(LibraryItem item) {
        items.add(item);
    }

    public List<LibraryItem> getItems() {
        return items;
    }

    public void printCatalog() {
        for (LibraryItem item : items) {
            switch (item) {
                case PhysicalBook pb -> System.out.println(pb.getInfo());
                case EBook eb -> System.out.println(eb.getInfo());
            }
        }
    }

    public Map<Genre, List<LibraryItem>> groupByGenre() {
        return items.stream()
                .collect(Collectors.groupingBy(
                        item -> switch (item) {
                            case PhysicalBook pb -> pb.book().genre();
                            case EBook eb -> eb.book().genre();
                        },
                        () -> new EnumMap<>(Genre.class),
                        Collectors.toList()
                ));
    }

    public double totalValue() {
        return items.stream()
                .map(item -> switch (item) {
                    case PhysicalBook pb -> pb.book().price();
                    case EBook eb -> eb.book().price();
                })
                .reduce(0.0, Double::sum);
    }

    public Optional<LibraryItem> mostExpensive() {
        return items.stream()
                .max(Comparator.comparing(item -> switch (item) {
                    case PhysicalBook pb -> pb.book().price();
                    case EBook eb -> eb.book().price();
                }));
    }

    public List<String> authorsByGenre(Genre genre) {
        return items.stream()
                .filter(item -> switch (item) {
                    case PhysicalBook pb -> pb.book().genre() == genre;
                    case EBook eb -> eb.book().genre() == genre;
                })
                .map(item -> switch (item) {
                    case PhysicalBook pb -> pb.book().author();
                    case EBook eb -> eb.book().author();
                })
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}

// 6. Main класс
public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Добавляем книги
        library.add(new PhysicalBook(
                new Book("Война и мир", "Лев Толстой", 1869, Genre.HISTORY, 1500.00),
                "A1"
        ));

        library.add(new PhysicalBook(
                new Book("Преступление и наказание", "Федор Достоевский", 1866, Genre.FICTION, 1200.00),
                "B2"
        ));

        library.add(new EBook(
                new Book("Краткая история времени", "Стивен Хокинг", 1988, Genre.SCIENCE, 800.00),
                "PDF", 5.2
        ));

        library.add(new PhysicalBook(
                new Book("Искусство программирования", "Дональд Кнут", 1968, Genre.PROGRAMMING, 3500.00),
                "C3"
        ));

        library.add(new EBook(
                new Book("Java. Эффективное программирование", "Джошуа Блох", 2018, Genre.PROGRAMMING, 2000.00),
                "EPUB", 3.8
        ));

        library.add(new PhysicalBook(
                new Book("Мона Лиза", "Уолтер Айзексон", 2017, Genre.ART, 1800.00),
                "D4"
        ));

        library.add(new EBook(
                new Book("Sapiens. Краткая история человечества", "Юваль Ной Харари", 2011, Genre.HISTORY, 950.00),
                "MOBI", 4.5
        ));

        library.add(new PhysicalBook(
                new Book("1984", "Джордж Оруэлл", 1949, Genre.FICTION, 700.00),
                "E5"
        ));

        System.out.println("=== Каталог библиотеки ===");
        library.printCatalog();

        System.out.println("\n=== Группировка по жанрам ===");
        Map<Genre, List<LibraryItem>> byGenre = library.groupByGenre();
        for (Map.Entry<Genre, List<LibraryItem>> entry : byGenre.entrySet()) {
            System.out.println(entry.getKey().getRussianName() + ": " + entry.getValue().size() + " книг");
        }

        System.out.println("\n=== Общая стоимость библиотеки ===");
        System.out.printf("Суммарная стоимость: %.2f руб.\n", library.totalValue());

        System.out.println("\n=== Самая дорогая книга ===");
        library.mostExpensive().ifPresent(item -> {
            System.out.println(switch (item) {
                case PhysicalBook pb -> pb.book().title() + " - " + pb.book().price() + " руб.";
                case EBook eb -> eb.book().title() + " - " + eb.book().price() + " руб.";
            });
        });

        System.out.println("\n=== Авторы в жанре 'Программирование' ===");
        List<String> authors = library.authorsByGenre(Genre.PROGRAMMING);
        authors.forEach(System.out::println);

        System.out.println("\n=== Поиск по названию (пример с Searchable) ===");
        // Исправлено: явно указываем тип
        List<LibraryItem> searchResults = Searchable.<LibraryItem>search(library.getItems(), "история");
        searchResults.forEach(item -> System.out.println(item.getInfo()));
    }
}