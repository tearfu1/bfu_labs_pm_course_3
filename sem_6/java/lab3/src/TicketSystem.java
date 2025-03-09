import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TicketSystem {
    private List<Cinema> cinemas = new ArrayList<>();

    // Сеттер для передачи тестовых данных
    public void setCinemas(List<Cinema> cinemas) {
        this.cinemas = cinemas;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Добро пожаловать в билетную систему.");
        System.out.println("Выберите роль: 1 - Администратор, 2 - Пользователь");
        int role = sc.nextInt();
        sc.nextLine(); // consume newline

        if (role == 1) {
            String ADMIN_LOGIN = "admin";
            String ADMIN_PASSWORD = "admin123";
            if (login(sc, ADMIN_LOGIN, ADMIN_PASSWORD)) {
                adminMenu(sc);
            } else {
                System.out.println("Неверные учетные данные.");
            }
        } else if (role == 2) {
            String USER_LOGIN = "user";
            String USER_PASSWORD = "user123";
            if (login(sc, USER_LOGIN, USER_PASSWORD)) {
                userMenu(sc);
            } else {
                System.out.println("Неверные учетные данные.");
            }
        } else {
            System.out.println("Неверный выбор.");
        }
    }

    private boolean login(Scanner sc, String expectedLogin, String expectedPassword) {
        System.out.print("Введите логин: ");
        String login = sc.nextLine();
        System.out.print("Введите пароль: ");
        String password = sc.nextLine();
        return login.equals(expectedLogin) && password.equals(expectedPassword);
    }

    private Cinema chooseCinema(Scanner sc) {
        if (cinemas.isEmpty()) {
            System.out.println("Нет доступных кинотеатров.");
            return null;
        }
        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).getName());
        }
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > cinemas.size()) {
            System.out.println("Неверный выбор кинотеатра.");
            return null;
        }
        return cinemas.get(choice - 1);
    }

    private Hall chooseHall(Scanner sc, Cinema cinema) {
        if (cinema.getHalls().isEmpty()) {
            System.out.println("В этом кинотеатре нет залов.");
            return null;
        }
        System.out.println("Выберите зал:");
        List<Hall> halls = cinema.getHalls();
        for (int i = 0; i < halls.size(); i++) {
            System.out.println((i + 1) + ". " + halls.get(i).getName());
        }
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > halls.size()) {
            System.out.println("Неверный выбор зала.");
            return null;
        }
        return halls.get(choice - 1);
    }

    private Session chooseSession(Scanner sc, Hall hall) {
        if (hall.getSessions().isEmpty()) {
            System.out.println("В этом зале нет сеансов.");
            return null;
        }
        System.out.println("Выберите сеанс:");
        List<Session> sessions = hall.getSessions();
        for (int i = 0; i < sessions.size(); i++) {
            System.out.println((i + 1) + ". " + sessions.get(i).getFilmName() +
                    " в " + sessions.get(i).getTime());
        }
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice < 1 || choice > sessions.size()) {
            System.out.println("Неверный выбор сеанса.");
            return null;
        }
        return sessions.get(choice - 1);
    }

    private void adminMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Администраторское меню ---");
            System.out.println("1. Добавить кинотеатр");
            System.out.println("2. Добавить зал в кинотеатр");
            System.out.println("3. Задать конфигурацию кресел");
            System.out.println("4. Создать сеанс");
            System.out.println("5. Просмотреть все данные");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addCinema(sc);
                    break;
                case 2:
                    addHall(sc);
                    break;
                case 3:
                    setSeatConfiguration(sc);
                    break;
                case 4:
                    createSession(sc);
                    break;
                case 5:
                    viewAllData();
                    break;
                case 6:
                    System.out.println("Выход из администраторского режима.");
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        } while (choice != 6);
    }

    private void userMenu(Scanner sc) {
        int choice;
        do {
            System.out.println("\n--- Пользовательское меню ---");
            System.out.println("1. Купить билет");
            System.out.println("2. Найти ближайший сеанс для фильма");
            System.out.println("3. Просмотреть все ближайшие сеансы");
            System.out.println("4. Показать план зала сеанса");
            System.out.println("5. Выход");
            System.out.print("Выберите опцию: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    sellTicket(sc);
                    break;
                case 2:
                    searchNearestSession(sc);
                    break;
                case 3:
                    viewAllUpcomingSessions();
                    break;
                case 4:
                    printHallPlan(sc);
                    break;
                case 5:
                    System.out.println("Выход из пользовательского режима.");
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        } while (choice != 5);
    }

    private void addCinema(Scanner sc) {
        System.out.print("Введите название кинотеатра: ");
        String name = sc.nextLine();
        Cinema cinema = new Cinema(name);
        cinemas.add(cinema);
        System.out.println("Кинотеатр добавлен.");
    }

    private void addHall(Scanner sc) {
        Cinema cinema = chooseCinema(sc);
        if (cinema == null) return;
        System.out.print("Введите название зала: ");
        String hallName = sc.nextLine();
        Hall hall = new Hall(hallName);
        cinema.addHall(hall);
        System.out.println("Зал добавлен в кинотеатр " + cinema.getName());
    }

    private void setSeatConfiguration(Scanner sc) {
        Cinema cinema = chooseCinema(sc);
        if (cinema == null) return;
        Hall hall = chooseHall(sc, cinema);
        if (hall == null) return;
        System.out.print("Введите количество рядов: ");
        int rows = sc.nextInt();
        System.out.print("Введите количество мест в ряду: ");
        int cols = sc.nextInt();
        sc.nextLine();
        hall.initializeSeats(rows, cols);
        System.out.println("Конфигурация зала задана.");
    }

    private void createSession(Scanner sc) {
        Cinema cinema = chooseCinema(sc);
        if (cinema == null) return;
        Hall hall = chooseHall(sc, cinema);
        if (hall == null) return;
        System.out.print("Введите название фильма: ");
        String filmName = sc.nextLine();
        System.out.print("Введите продолжительность фильма (в минутах): ");
        int duration = sc.nextInt();
        sc.nextLine();
        System.out.print("Введите время начала сеанса (например, 18:30): ");
        String time = sc.nextLine();
        Session session = new Session(filmName, duration, time, hall);
        hall.addSession(session);
        System.out.println("Сеанс создан.");
    }

    private void viewAllData() {
        if (cinemas.isEmpty()) {
            System.out.println("Нет доступных кинотеатров.");
            return;
        }
        System.out.println("\n--- Все данные ---");
        for (Cinema cinema : cinemas) {
            System.out.println("Кинотеатр: " + cinema.getName());
            if (cinema.getHalls().isEmpty()) {
                System.out.println("  Нет залов.");
            } else {
                for (Hall hall : cinema.getHalls()) {
                    System.out.println("  Зал: " + hall.getName());
                    if (hall.hasSeatsConfigured()) {
                        System.out.println("    Конфигурация кресел:");
                        hall.printSeats();
                    } else {
                        System.out.println("    Конфигурация не задана.");
                    }
                    if (hall.getSessions().isEmpty()) {
                        System.out.println("    Нет сеансов.");
                    } else {
                        for (Session session : hall.getSessions()) {
                            System.out.println("    Сеанс: " + session.getFilmName() +
                                    ", длительность: " + session.duration +
                                    " мин, время: " + session.getTime());
                        }
                    }
                }
            }
        }
    }

    private void sellTicket(Scanner sc) {
        Cinema cinema = chooseCinema(sc);
        if (cinema == null) return;
        Hall hall = chooseHall(sc, cinema);
        if (hall == null) return;
        Session session = chooseSession(sc, hall);
        if (session == null) return;
        if (!session.getHall().hasSeatsConfigured()) {
            System.out.println("Конфигурация зала не задана.");
            return;
        }
        session.getHall().printSeats();
        System.out.print("Введите ряд для бронирования: ");
        int row = sc.nextInt();
        System.out.print("Введите место в ряду: ");
        int col = sc.nextInt();
        sc.nextLine();
        if (session.getHall().bookSeat(row - 1, col - 1)) {
            System.out.println("Билет куплен.");
        } else {
            System.out.println("Место занято или неверные координаты.");
        }
    }

    private void searchNearestSession(Scanner sc) {
        System.out.print("Введите название фильма: ");
        String filmName = sc.nextLine();
        LocalTime now = LocalTime.now();

        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.getHalls()) {
                for (Session session : hall.getSessions()) {
                    try {
                        LocalTime sessionTime = LocalTime.parse(session.getTime());
                        if (session.getFilmName().equalsIgnoreCase(filmName) &&
                                sessionTime.isAfter(now) &&
                                session.getHall().hasAvailableSeat()) {
                            System.out.println("Ближайший сеанс: кинотеатр " +
                                    cinema.getName() + ", зал " + hall.getName() +
                                    ", время " + session.getTime());
                            return;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Неверный формат времени сеанса: " + session.getTime());
                    }
                }
            }
        }
        System.out.println("Сеанс не найден.");
    }

    private void viewAllUpcomingSessions() {
        LocalTime now = LocalTime.now();
        boolean found = false;
        System.out.println("\n--- Ближайшие сеансы ---");
        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.getHalls()) {
                for (Session session : hall.getSessions()) {
                    try {
                        LocalTime sessionTime = LocalTime.parse(session.getTime());
                        if (sessionTime.isAfter(now)) {
                            System.out.println("Кинотеатр: " + cinema.getName() +
                                    ", Зал: " + hall.getName() +
                                    ", Фильм: " + session.getFilmName() +
                                    ", Время: " + session.getTime());
                            found = true;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Неверный формат времени сеанса: " + session.getTime());
                    }
                }
            }
        }
        if (!found) {
            System.out.println("Нет ближайших сеансов.");
        }
    }

    private void printHallPlan(Scanner sc) {
        Cinema cinema = chooseCinema(sc);
        if (cinema == null) return;
        Hall hall = chooseHall(sc, cinema);
        if (hall == null) return;
        Session session = chooseSession(sc, hall);
        if (session == null) return;
        session.getHall().printSeats();
    }
}
