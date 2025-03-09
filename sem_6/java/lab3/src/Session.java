
public class Session {
    String filmName;
    int duration;
    private final String time;
    private final Hall hall;

    public Session(String filmName, int duration, String time, Hall hall) {
        this.filmName = filmName;
        this.duration = duration;
        this.time = time;
        this.hall = hall;
    }

    public String getFilmName() {
        return filmName;
    }

    public String getTime() {
        return time;
    }

    public Hall getHall() {
        return hall;
    }
}