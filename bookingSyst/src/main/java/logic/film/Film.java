package logic.film;// Created by sky-vd on 03.04.2017.

import java.util.ArrayList;

public class Film {
    private static int filmsIdCounter = 0; // счетчик фильмов
    private int filmId; // уникальный номер фильма
    private String filmName; // название фильма
    private int seanceIdCounter = 0; // счетчик сеансов

    private ArrayList<Seance> seances = new ArrayList<>(); // список сеансов фильма

    public Film(int filmId, String filmName, int seanceIdCounter) {
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
            filmPrint.append(seance.printSeance());
        }
        filmPrint.append("***********************");
        return filmPrint.toString();
    }

    @Override
    public String toString() {
        return String.format("%d. %s", filmId, filmName);
    }


    public int getFilmId() {
        return filmId;
    }

    // получить сеанс по id
    public Seance getSeanceById(int seanceId) {
        for (Seance seance : seances) {
            if (seance.getSeanceId() == seanceId) return seance;
        }
        return null;
    }

    public static void setFilmsIdCounter(int filmsIdCounter) {
        Film.filmsIdCounter = filmsIdCounter;
    }

    public ArrayList<Seance> getSeances() {
        return seances;
    }


    public String getFilmName() {
        return filmName;
    }
}
