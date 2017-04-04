package logic.order;

import logic.film.Film;
import logic.film.FilmManagingSystem;
import logic.film.Seance;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/*Класс доя управления заказами и получения необходимой о них информации*/
public class OrderManagingSystem {
    private static OrderManagingSystem orderManagSyst; // экземпляр синглтона
    private ArrayList<Order> orders = new ArrayList<>(); // список запронированных заказов
    private String ordersSource = "orders.json"; // адрес файла с данными

    private OrderManagingSystem() {
    }

    // получить экземпляр синглтона
    public static OrderManagingSystem getOrderManagingSystemInst() {
        if (orderManagSyst == null) {
            orderManagSyst = new OrderManagingSystem();
            orderManagSyst.loadOdersFromFile();
        }
        return orderManagSyst;
    }

    // загрузить список заказов из файла
    private void loadOdersFromFile() {
        if (orders != null) {
            orders.clear();
        }

        FilmManagingSystem filmManagingSystem = FilmManagingSystem.getFilmManagingSystemInst();

        File ordersFile = new File(ordersSource);
        BufferedReader reader = null;
        StringBuilder jsonFile = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(ordersFile));
            String line;
            while ((line = reader.readLine()) != null) jsonFile.append(line);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObj = (JSONObject) parser.parse(jsonFile.toString());

            long ordersIdCounter = (Long) jsonObj.get("ordersIdCounter");
            Order.setOrdersIdCounter((int) ordersIdCounter);

            JSONArray orders = (JSONArray) jsonObj.get("orders");
            Iterator<JSONObject> iterator = orders.iterator();
            while (iterator.hasNext()) {
                JSONObject order = iterator.next();

                long filmId = (Long) order.get("filmId");
                long seanceId = (Long) order.get("seanceId");
                Film film = filmManagingSystem.getFilmByID(String.valueOf(filmId));
                if (film == null) break;
                Seance seance = film.getSeanceById((int) seanceId);
                if (seance == null) break;

                ArrayList<Integer> seats = new ArrayList<>();
                JSONArray seatsArr = (JSONArray) order.get("seats");
                Iterator<Long> iterSeats = seatsArr.iterator();
                while (iterSeats.hasNext()) {
                    long seat = iterSeats.next();
                    seats.add((int) seat);
                }

                long orderId = (Long) order.get("oderId");
                String oderDate = (String) order.get("oderDate");

                Order oder = new Order((int) orderId, oderDate, film, seance, seats);
                this.orders.add(oder);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // просмотреть информацию о сделанном заказе по id
    public void printOrderInformation(int id) {
        for (Order order : orders) {
            if (order.getOderId() == id) {
                System.out.println(order);
                return;
            }
        }
        System.out.println("Заказа с таким номером не существует.");
    }

    // удалить заказ из списка заказов и убрать бронь с мест в зале по id заказа
    public void deleteOrder(int id) {
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order.getOderId() == id) {
                iterator.remove();
                System.out.println("Заказ успешно удален!");
                return;
            }
        }
        System.out.println("Заказа с таким номером не существует.");
    }

    //добавить новый заказ в список заказов
    public void bookTickets(Order order) {
        orders.add(order);
    }
}
