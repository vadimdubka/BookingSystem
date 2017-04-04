package logic.order;

import logic.film.Film;
import logic.film.Seance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {
    // счетчик заказов
    private static int ordersIdCounter = 0;
    // уникальный номер заказа
    private int oderId;
    // дата заказа
    private String oderDate;
    // Фильм, на который сделан заказ
    private Film film;
    // Сеанс, на который сделан заказ
    private Seance seance;
    // общая стоимость заказа
    private int oderCost;
    // номера мест
    private ArrayList<Integer> seats;

    private SimpleDateFormat sdf = new SimpleDateFormat("k:mm dd.MM.yy");

    // для создания нового объекта заказа при загрузке из файла
    public Order(int oderId, String oderDate, Film film, Seance seance, ArrayList<Integer> seats) {
        this.oderId = oderId;
        this.oderDate = oderDate;
        this.film = film;
        this.seance = seance;
        this.seats = seats;
    }

    // для создания нового объекта заказа в системе
    public Order(Film film, Seance seance, ArrayList<Integer> seats) {
        ordersIdCounter++;
        this.oderId = ordersIdCounter;
        this.oderDate = sdf.format(new Date());
        this.film = film;
        this.seance = seance;
        this.seats = seats;
    }


    @Override
    public String toString() {
        String seatsNumbers = seats.toString();

        return String.format("*************************\n" +
                        "Номер заказа: %d\n" +
                        "Дата заказа: %s\n" +
                        "%s \n" +
                        "%s \n" +
                        "Номера мест: %s\n" +
                        "Стоимость заказа: %dр.\n" +
                        "*************************\n",
                oderId, oderDate, film, seance, seatsNumbers, calculeteOderCost());
    }

    //    рассчитать стоимость заказа
    private int calculeteOderCost() {
        return oderCost = seance.getTicketCost() * seats.size();
    }

    public static void setOrdersIdCounter(int ordersIdCounter) {
        Order.ordersIdCounter = ordersIdCounter;
    }

    public int getOderId() {
        return oderId;
    }
}
