import java.util.List;

public class LibraryTest {
    public static void main(String[] args) {
        Library library = new Library();

        // 1. Добавление книг
        System.out.println("--- Тестирование добавления книг ---");
        Book book1 = new Book("Война и мир", "Лев Толстой", 1869);
        Book book2 = new Book("Преступление и наказание", "Федор Достоевский", 1866);
        Book book3 = new Book("Анна Каренина", "Лев Толстой", 1877);
        Book book4 = new Book("Идиот", "Федор Достоевский", 1869);
        Book book5 = new Book("Мертвые души", "Николай Гоголь", 1842);
        Book book6_duplicate = new Book("Война и мир", "Лев Толстой", 1869); // для теста удаления

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.addBook(null); // Тест добавления null
        library.addBook(book1); // Тест добавления существующей книги (просто добавится еще раз, если не делать доп. проверку)

        // 2. Вывод всех книг
        library.printAllBooks();

        // 3. Вывод уникальных авторов
        library.printUniqueAuthors();

        // 4. Вывод статистики по авторам
        library.printAuthorStatistics();

        // 5. Поиск книг по автору
        System.out.println("\n--- Тестирование поиска книг по автору ---");
        String authorToFind = "Лев Толстой";
        List<Book> booksByTolstoy = library.findBooksByAuthor(authorToFind);
        if (!booksByTolstoy.isEmpty()) {
            System.out.println("Книги автора '" + authorToFind + "':");
            booksByTolstoy.forEach(System.out::println);
        } else {
            System.out.println("Книги автора '" + authorToFind + "' не найдены.");
        }

        String nonExistentAuthor = "Александр Пушкин";
        List<Book> booksByPushkin = library.findBooksByAuthor(nonExistentAuthor);
        if (!booksByPushkin.isEmpty()) {
            System.out.println("Книги автора '" + nonExistentAuthor + "':");
            booksByPushkin.forEach(System.out::println);
        } else {
            System.out.println("Книги автора '" + nonExistentAuthor + "' не найдены.");
        }

        // 6. Поиск книг по году издания
        System.out.println("\n--- Тестирование поиска книг по году ---");
        int yearToFind = 1869;
        List<Book> booksFrom1869 = library.findBooksByYear(yearToFind);
        if (!booksFrom1869.isEmpty()) {
            System.out.println("Книги, изданные в " + yearToFind + " году:");
            booksFrom1869.forEach(System.out::println);
        } else {
            System.out.println("Книги, изданные в " + yearToFind + " году, не найдены.");
        }

        int nonExistentYear = 2025;
        List<Book> booksFrom2025 = library.findBooksByYear(nonExistentYear);
        if (!booksFrom2025.isEmpty()) {
            System.out.println("Книги, изданные в " + nonExistentYear + " году:");
            booksFrom2025.forEach(System.out::println);
        } else {
            System.out.println("Книги, изданные в " + nonExistentYear + " году, не найдены.");
        }

        // 7. Удаление книги
        System.out.println("\n--- Тестирование удаления книги ---");
        // Удаляем одну из книг Толстого (book1 совпадает с book6_duplicate по equals)
        library.removeBook(book6_duplicate);
        library.removeBook(new Book("Несуществующая книга", "Неизвестный Автор", 2000)); // Тест удаления несуществующей
        library.removeBook(null); // Тест удаления null

        // Повторный вывод информации после удаления
        System.out.println("\n--- Состояние библиотеки после удаления ---");
        library.printAllBooks();
        library.printUniqueAuthors();
        library.printAuthorStatistics();

        // Удаляем последнюю книгу Гоголя
        System.out.println("\n--- Удаление последней книги автора (Гоголь) ---");
        library.removeBook(book5);
        library.printAllBooks();
        library.printUniqueAuthors();
        library.printAuthorStatistics();
    }
}