import java.util.*;

public class Main {
    public static void main(String[] args) {
        TicketSystem system = new TicketSystem();
        system.setCinemas(initTestData());
        system.start();
    }

    // Метод для инициализации тестовых данных
    private static List<Cinema> initTestData() {
        List<Cinema> cinemas = new ArrayList<>();

        // Создаем кинотеатр
        Cinema cinema1 = new Cinema("Кинотеатр 'Парадиз'");

        // Создаем первый зал с конфигурацией и сеансом
        Hall hall1 = new Hall("Зал 1");
        hall1.initializeSeats(5, 5); // 5 рядов по 5 мест
        Session session1 = new Session("Фильм А", 120, "18:00", hall1);
        hall1.addSession(session1);

        // Создаем второй зал с конфигурацией и сеансом
        Hall hall2 = new Hall("Зал 2");
        hall2.initializeSeats(4, 4); // 4 ряда по 4 места
        Session session2 = new Session("Фильм Б", 90, "20:00", hall2);
        hall2.addSession(session2);

        // Добавляем залы в кинотеатр
        cinema1.addHall(hall1);
        cinema1.addHall(hall2);

        // Добавляем кинотеатр в список
        cinemas.add(cinema1);

        return cinemas;
    }
}