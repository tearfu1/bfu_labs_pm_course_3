import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private final String name;
    private final List<Hall> halls = new ArrayList<>();

    public Cinema(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addHall(Hall hall) {
        halls.add(hall);
    }

    public List<Hall> getHalls() {
        return halls;
    }
}
