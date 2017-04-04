package logic.user;

import logic.film.Film;
import logic.film.FilmManagingSystem;
import logic.film.Seance;
import logic.order.Order;
import logic.order.OrderManagingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* Класс эмулирует работу каждого нового пользователя системы*/
public class UserSession {

    // начать сессию для нового пользователя
    public void startUserSession() {
        System.out.println("Добро пожаловать в наш кинотеатр!");
        FilmManagingSystem filmManagSyst = FilmManagingSystem.getFilmManagingSystemInst();
        OrderManagingSystem orderManagSyst = OrderManagingSystem.getOrderManagingSystemInst();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(System.in));
            boolean repeatCommand = true; // для остановки повторения цикла
            while (repeatCommand) {
                System.out.println(
                        "***********************\n" +
                                "Пожалуйста, введите номер интересующей вас услуги:\n" +
                                "1. Просмотреть список фильмов и забронировать билеты.\n" +
                                "2. Получить информацию о сделанном заказе.\n" +
                                "3. Отменить сделанный заказ. \n" +
                                "0. Выйти из системы.");
                String command = reader.readLine();
                try {
                    switch (command) {
                        case "1":
                            System.out.println("***********************");
                            System.out.println("Просмотр списка фильмов:");
                            filmManagSyst.printFilmNames();
                            System.out.println("***********************");

                            System.out.println("Для просмотра сеансов и бронирования билетов введите номер фильма или \"0\" для возврата в главное меню:");
                            boolean repeatLocal = true;
                            Film film = null;
                            while (repeatLocal) {
                                String filmId = reader.readLine();
                                if (filmId.equals("0")) break;
                                film = filmManagSyst.printFilmTable(filmId);
                                if (film != null) repeatLocal = false;
                            }

                            if (film != null) {
                                System.out.println("Для продолжения бранирования введите номер выбранного сеанса или \"0\" для возврата в главное меню:");
                                repeatLocal = true;
                                Seance seance = null;
                                while (repeatLocal) {
                                    String seanceId = reader.readLine();
                                    if (seanceId.equals("0")) break;
                                    seance = filmManagSyst.checkSeanceId(film, seanceId);
                                    if (seance != null) repeatLocal = false;
                                }
                                if (seance != null) {
                                    System.out.println("Введите номера мест через пробел или \"0\" для возврата в главное меню:");
                                    repeatLocal = true;
                                    ArrayList<Integer> seats = null;
                                    while (repeatLocal) {
                                        String seatsNumbeers = reader.readLine();
                                        if (seatsNumbeers.equals("0")) break;
                                        seats = filmManagSyst.checkSeatsAndBook(seance, seatsNumbeers);
                                        if (seats != null) repeatLocal = false;
                                    }
                                    if (seats != null) {
                                        Order order = new Order(film, seance, seats);
                                        orderManagSyst.bookTickets(order);
                                        System.out.println("Ваши места успешно забранированы!");
                                        System.out.println(order);
                                    }
                                }
                            }

                            break;
                        case "2":
                            System.out.println("***********************");
                            System.out.println("Получение информации о сделанном заказе.");
                            System.out.print("Введите номер своего заказа: ");
                            int orderId = Integer.parseInt(reader.readLine());
                            orderManagSyst.printOrderInformation(orderId);
                            break;
                        case "3":
                            System.out.println("***********************");
                            System.out.println("Отмена заказа.");
                            System.out.print("Введите номер своего заказа: ");
                            orderId = Integer.parseInt(reader.readLine());
                            orderManagSyst.deleteOrder(orderId);
                            System.out.println("***********************");
                            break;
                        case "0":
                            System.out.println("Всего доброго! Будем рады видеть Вас снова.");
                            repeatCommand = false;
                            break;
                        default:
                            System.out.println("Введена неверная комнда. Попробуйте еще раз.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Введена неверная комнда. Попробуйте еще раз.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
