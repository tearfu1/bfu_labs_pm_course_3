import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hall {
    private final String name;
    private char[][] seats; // 'O' - свободно, 'X' - занято
    private final List<Session> sessions = new ArrayList<>();

    public Hall(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void initializeSeats(int rows, int cols) {
        seats = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            Arrays.fill(seats[i], 'O');
        }
    }

    public boolean hasSeatsConfigured() {
        return seats != null;
    }

    public void printSeats() {
        if (seats == null) {
            System.out.println("Конфигурация зала не задана.");
            return;
        }
        for (char[] seat : seats) {
            for (char c : seat) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public boolean bookSeat(int row, int col) {
        if (seats == null || row < 0 || row >= seats.length || col < 0 || col >= seats[0].length)
            return false;
        if (seats[row][col] == 'X')
            return false;
        seats[row][col] = 'X';
        return true;
    }

    public boolean hasAvailableSeat() {
        if (seats == null)
            return false;
        for (char[] row : seats) {
            for (char seat : row) {
                if (seat == 'O') return true;
            }
        }
        return false;
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
