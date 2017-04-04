package logic.film;

public class Seance {

    private int seanceId; // уникальный номер сеансов
    private String seanceDate; // дата сеанса
    private int ticketCost; // стоимость билета
    private String hallName; // название кинозала
    private String[][] hallSeatsState; // состояние мест в кинозале на данный сеанс

    public Seance(int seanceId, String seanceDate, int ticketCost, String hallName, String[][] hallSeatsState) {
        this.seanceId = seanceId;
        this.seanceDate = seanceDate;
        this.ticketCost = ticketCost;
        this.hallName = hallName;
        this.hallSeatsState = hallSeatsState;
    }

    public int getTicketCost() {
        return ticketCost;
    }

    // вывести на экран информацию о сеансе
    public String printSeance() {
        return String.format(" \n" +
                "Сеанс №%d:\n" +
                "Начало: %s \n" +
                "Цена 1 билета: %d р.\n" +
                "%s", seanceId, seanceDate, ticketCost, printCinHallState());
    }

    @Override
    public String toString() {
        return String.format(" \n" +
                "Сеанс №%d:\n" +
                "Начало: %s \n" +
                "Кинозал: %s", seanceId, seanceDate, hallName);
    }

    public int getSeanceId() {
        return seanceId;
    }


    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String[][] getHallSeatsState() {
        return hallSeatsState;
    }

    public void setHallSeatsState(String[][] hallSeatsState) {
        this.hallSeatsState = hallSeatsState;
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



}
