package logic.film;

import java.util.Arrays;

/* Описывает объект одного из сеансов для каждого фильма.*/
public class Seance {
    private int seanceId; // уникальный номер сеансов
    private String seanceDate; // дата сеанса
    private int ticketCost; // стоимость билета
    private String hallName; // название кинозала
    private String[][] hallSeatsState; // состояние мест в кинозале на данный сеанс

    Seance(int seanceId, String seanceDate, int ticketCost, String hallName, String[][] hallSeatsState) {
        this.seanceId = seanceId;
        this.seanceDate = seanceDate;
        this.ticketCost = ticketCost;
        this.hallName = hallName;
        this.hallSeatsState = hallSeatsState;
    }

    // вывести на экран полную информацию о сеансе
    String printSeance() {
        return String.format("Сеанс №%d:\n" +
                "Начало: %s \n" +
                "Цена 1 билета: %d р.\n" +
                "%s", seanceId, seanceDate, ticketCost, printCinHallState());
    }

    // вывести на экран информацию о свободных и занятых местах
    private String printCinHallState() {
        StringBuilder seatsPring = new StringBuilder();
        seatsPring.append("Кинозал: ").append(hallName).append("\n");
        for (String[] strings : hallSeatsState) {
            for (String s : strings) {
                seatsPring.append(s).append(" ");
            }
            seatsPring.append("\n");
        }

        return seatsPring.toString();
    }

    public int getTicketCost() {
        return ticketCost;
    }

    int getSeanceId() {
        return seanceId;
    }

    String[][] getHallSeatsState() {
        return hallSeatsState;
    }

    @Override
    public String toString() {
        return String.format("Сеанс №%d:\n" +
                "Начало: %s \n" +
                "Кинозал: %s", seanceId, seanceDate, hallName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seance)) return false;

        Seance seance = (Seance) o;

        if (seanceId != seance.seanceId) return false;
        if (ticketCost != seance.ticketCost) return false;
        if (seanceDate != null ? !seanceDate.equals(seance.seanceDate) : seance.seanceDate != null) return false;
        if (hallName != null ? !hallName.equals(seance.hallName) : seance.hallName != null) return false;
        return Arrays.deepEquals(hallSeatsState, seance.hallSeatsState);

    }

    @Override
    public int hashCode() {
        int result = seanceId;
        result = 31 * result + ticketCost;
        return result;
    }
}
