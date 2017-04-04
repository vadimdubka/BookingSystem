package logic.order;

import logic.film.Film;
import logic.film.Seance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* Описывает объект объект заказа/брони билетов на определенный фильм и сеанс.*/
public class Order {
    private static int ordersIdCounter = 0; // счетчик заказов
    private int oderId;  // уникальный номер заказа
    private String oderDate; // дата заказа
    private Film film;// Фильм, на который сделан заказ
    private Seance seance; // Сеанс, на который сделан заказ
    private int oderCost; // общая стоимость заказа
    private ArrayList<Integer> seats; // номера мест
    private SimpleDateFormat sdf = new SimpleDateFormat("k:mm dd.MM.yy"); // для форматирования даты в необходимый формат

    // для создания нового объекта заказа пользователем в системе
    public Order(Film film, Seance seance, ArrayList<Integer> seats) {
        ordersIdCounter++;
        this.oderId = ordersIdCounter;
        this.oderDate = sdf.format(new Date());
        this.film = film;
        this.seance = seance;
        this.seats = seats;
    }

    // для создания нового объекта заказа при загрузке данных из файла
    Order(int oderId, String oderDate, Film film, Seance seance, ArrayList<Integer> seats) {
        this.oderId = oderId;
        this.oderDate = oderDate;
        this.film = film;
        this.seance = seance;
        this.seats = seats;
    }

    // рассчитать стоимость заказа
    private int calculeteOderCost() {
        return oderCost = seance.getTicketCost() * seats.size();
    }

    static void setOrdersIdCounter(int ordersIdCounter) {
        Order.ordersIdCounter = ordersIdCounter;
    }

    int getOderId() {
        return oderId;
    }

    @Override
    public String toString() {
        String seatsNumbers = seats.toString();

        return String.format("*************************\n" +
                        "Номер заказа: %d\n" +
                        "Дата заказа: %s\n" +
                        "Фильм: %s\n" +
                        "%s \n" +
                        "Номера мест: %s\n" +
                        "Стоимость заказа: %dр.\n" +
                        "*************************\n",
                oderId, oderDate, film, seance, seatsNumbers, calculeteOderCost());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (oderId != order.oderId) return false;
        if (oderCost != order.oderCost) return false;
        if (oderDate != null ? !oderDate.equals(order.oderDate) : order.oderDate != null) return false;
        if (film != null ? !film.equals(order.film) : order.film != null) return false;
        if (seance != null ? !seance.equals(order.seance) : order.seance != null) return false;
        return seats != null ? seats.equals(order.seats) : order.seats == null;
    }

    @Override
    public int hashCode() {
        int result = oderId % 10;
        result = 31 * result + oderCost + seats.size();
        return result;
    }
}
