import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Library {
    private final List<Book> allBooks;
    private final Set<String> uniqueAuthors;
    private final Map<String, Integer> authorBookCount;

    public Library() {
        this.allBooks = new ArrayList<>();
        this.uniqueAuthors = new HashSet<>();
        this.authorBookCount = new HashMap<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            System.out.println("Нельзя добавить null книгу.");
            return;
        }

        if (allBooks.contains(book)) {
            System.out.println("Книга \"" + book.getTitle() + "\" уже существует в библиотеке.");
            return;
        }

        allBooks.add(book);
        uniqueAuthors.add(book.getAuthor());
        authorBookCount.put(book.getAuthor(), authorBookCount.getOrDefault(book.getAuthor(), 0) + 1);
        System.out.println("Книга \"" + book.getTitle() + "\" добавлена в библиотеку.");
    }

    public void removeBook(Book book) {
        if (book == null) {
            System.out.println("Нельзя удалить null книгу.");
            return;
        }
        boolean removed = allBooks.remove(book);
        if (removed) {
            String author = book.getAuthor();
            int currentCount = authorBookCount.getOrDefault(author, 0);
            if (currentCount > 1) {
                authorBookCount.put(author, currentCount - 1);
            } else {
                authorBookCount.remove(author);
                boolean authorHasOtherBooks = false;
                for (Book b : allBooks) {
                    if (b.getAuthor().equals(author)) {
                        authorHasOtherBooks = true;
                        break;
                    }
                }
                if (!authorHasOtherBooks) {
                    uniqueAuthors.remove(author);
                }
            }
            System.out.println("Книга \"" + book.getTitle() + "\" удалена из библиотеки.");
        } else {
            System.out.println("Книга \"" + book.getTitle() + "\" не найдена в библиотеке для удаления.");
        }
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public List<Book> findBooksByYear(int year) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getYear() == year) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public void printAllBooks() {
        System.out.println("\n--- Все книги в библиотеке ---");
        if (allBooks.isEmpty()) {
            System.out.println("Библиотека пуста.");
            return;
        }
        for (int i = 0; i < allBooks.size(); i++) {
            System.out.println((i + 1) + ". " + allBooks.get(i));
        }
    }

    public void printUniqueAuthors() {
        System.out.println("\n--- Уникальные авторы в библиотеке ---");
        if (uniqueAuthors.isEmpty()) {
            System.out.println("Нет информации об авторах.");
            return;
        }
        int i = 1;
        for (String author : uniqueAuthors) {
            System.out.println(i++ + ". " + author);
        }
    }

    public void printAuthorStatistics() {
        System.out.println("\n--- Статистика книг по авторам ---");
        if (authorBookCount.isEmpty()) {
            System.out.println("Нет статистики по авторам.");
            return;
        }
        authorBookCount.forEach((author, count) ->
                System.out.println("Автор: " + author + ", Количество книг: " + count)
        );
    }
}