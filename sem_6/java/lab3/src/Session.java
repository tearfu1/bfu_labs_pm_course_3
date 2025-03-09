// Класс сеанса
class Session {
    String filmName;
    int duration; // в минутах
    private final String time; // время начала сеанса, формат "HH:mm"
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