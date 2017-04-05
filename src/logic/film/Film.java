package logic.film;

import java.util.ArrayList;

/* Описывает объект объект Фильм.*/
public class Film {
    private int filmId; // уникальный номер фильма
    private String filmName; // название фильма
    private ArrayList<Seance> seances = new ArrayList<>(); // список сеансов фильма
    private static int filmsIdCounter = 0; // счетчик фильмов при необходимости добавлять фильмы
    private int seanceIdCounter = 0; // счетчик сеансов при необходимости добавлять сеансы

    Film(int filmId, String filmName, int seanceIdCounter) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.seanceIdCounter = seanceIdCounter;
    }

    // вывести на экран расписание всех сеансов фильма
    public String printFilmSeancesTable() {
        StringBuilder filmPrint = new StringBuilder();
        filmPrint.append("***********************\n");
        filmPrint.append("Фильм: ").append(filmName).append("\n");
        for (Seance seance : seances) {
            filmPrint.append("\n");
            filmPrint.append(seance.printSeance());
        }
        filmPrint.append("***********************");
        return filmPrint.toString();
    }

    // получить сеанс фильма по id сеанса
    public Seance getSeanceById(int seanceId) {
        for (Seance seance : seances) {
            if (seance.getSeanceId() == seanceId) return seance;
        }
        return null;
    }

    int getFilmId() {
        return filmId;
    }

    static void setFilmsIdCounter(int filmsIdCounter) {
        Film.filmsIdCounter = filmsIdCounter;
    }

    ArrayList<Seance> getSeances() {
        return seances;
    }

    @Override
    public String toString() {
        return String.format("%d. %s", filmId, filmName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;

        Film film = (Film) o;

        if (filmId != film.filmId) return false;
        if (filmName != null ? !filmName.equals(film.filmName) : film.filmName != null) return false;
        return seances.equals(film.seances);

    }

    @Override
    public int hashCode() {
        int result = filmId%10;
        result = 31 * result + seances.size();
        return result;
    }
}
