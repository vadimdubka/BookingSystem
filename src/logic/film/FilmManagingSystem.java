package logic.film;

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
import java.util.regex.Pattern;
/*Класс доя управления фильмами и получения необходимой о них информации*/
public class FilmManagingSystem {
    private static FilmManagingSystem filmManagSyst; // экземпляр синглтона
    private ArrayList<Film> films = new ArrayList<>(); // список фильмов
    private String filmsSource = "films.json"; //адрес файла с данными

    private FilmManagingSystem() {
    }

    // получить экземпляр синглтона
    public static FilmManagingSystem getFilmManagingSystemInst() {
        if (filmManagSyst == null) {
            filmManagSyst = new FilmManagingSystem();
            filmManagSyst.loadFilmsFromFile();
        }
        return filmManagSyst;
    }

    // загрузить список фильмов и сеансов из файла
    private void loadFilmsFromFile() {
        if (films != null) {
            films.clear();
        }

        File filmsFile = new File(filmsSource);
        BufferedReader reader = null;
        StringBuilder jsonFile = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filmsFile));
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
            long filmsIdCounter = (long) jsonObj.get("filmsIdCounter");
            Film.setFilmsIdCounter((int) filmsIdCounter);

            JSONArray films = (JSONArray) jsonObj.get("films");
            Iterator<JSONObject> filmsIter = films.iterator();
            while (filmsIter.hasNext()) {
                JSONObject film = filmsIter.next();
                long filmId = (long) film.get("filmId");
                String filmName = (String) film.get("filmName");
                long seanceIdCounter = (long) film.get("seanceIdCounter");

                Film filmNew = new Film((int) filmId, filmName, (int) seanceIdCounter);

                JSONArray seances = (JSONArray) film.get("seances");
                Iterator<JSONObject> seancesIter = seances.iterator();
                while (seancesIter.hasNext()) {
                    JSONObject seance = seancesIter.next();
                    long seanceId = (long) seance.get("seanceId");
                    String seanceDate = (String) seance.get("seanceDate");
                    long ticketCost = (long) seance.get("ticketCost");
                    String hallName = (String) seance.get("hallName");
                    JSONArray hallSeatsState = (JSONArray) seance.get("hallSeatsState");
                    Iterator<String> hallSeatsStateIter = hallSeatsState.iterator();
                    String[][] hallSeatsArr = new String[6][8];
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 8; j++) {
                            hallSeatsArr[i][j] = hallSeatsStateIter.next();
                        }
                    }

                    Seance seanceNew = new Seance((int) seanceId, seanceDate, (int) ticketCost, hallName, hallSeatsArr);
                    filmNew.getSeances().add(seanceNew);
                }

                this.films.add(filmNew);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // получить фильм по его id
    public Film getFilmByID(String filmId) {
        for (Film film : films) {
            String filmIdS = String.valueOf(film.getFilmId());
            if (filmId.equals(filmIdS)) return film;
        }
        return null;
    }

    // проверка корректности введенного номера сеанса
    public Seance checkSeanceId(Film film, String seanceId) {
        for (Seance seance : film.getSeances()) {
            String seanceIdS = String.valueOf(seance.getSeanceId());
            if (seanceId.equals(seanceIdS)) {
                return seance;
            }
        }
        System.out.println("Введена неверная комнда. Попробуйте еще раз.");
        return null;
    }

    // проверка наличия свободных мест на сеансе и проверка корректности введенных данных, если место не занято - помечаем занятым.
    public ArrayList<Integer> checkSeatsAndBook(Seance seance, String seatsNumber) {
        Pattern p = Pattern.compile("(\\d+){1}(\\s+(\\d+))*");
        if (p.matcher(seatsNumber).matches()) { // если введенная строка соответствует шаблону
            ArrayList<Integer> seats = new ArrayList<>();
            String[][] hallState = seance.getHallSeatsState();

            String[] numbers = seatsNumber.split(" "); // получаем массив чисел в виде строк
            int[][] seatIdexes = new int[numbers.length][];// массив для хранения индексов найденных мест
            for (int l = 0; l < numbers.length; l++) { // для каждого из элементов массива
                int seat = Integer.parseInt(numbers[l]);
                if (seat <= 0 || seat > 58) {
                    System.out.println("Номера мест введены некорректно. Попробуйте еще раз.");
                    return null;
                }

                for (int i = 0; i < 6; i++) {
                    int count = i * 10;
                    for (int j = 0; j < 8; j++) {
                        count++;
                        if (count == seat) {
                            if (!"--".equals(hallState[5 - i][j]) && !seats.contains(seat)) {
                                seatIdexes[l] = new int[]{5 - i, j};
                                seats.add(seat);
                            } else {
                                System.out.println("Одно из мест уже занято. Попробуйте еще раз.");
                                return null;
                            }
                        }
                    }
                }
            }
            // если было найдено хоть 1 место
            if (!(seats.size() == 0)) {
                // если все места оказались свободны - помечаем места в зале как занятые
                for (int[] seatInd : seatIdexes) {
                    if (seatInd != null) {
                        hallState[seatInd[0]][seatInd[1]] = "--";
                    }
                }
                return seats;
            } else {
                System.out.println("Номера мест введены некорректно. Попробуйте еще раз.");
                return null;
            }
        } else {
            System.out.println("Номера мест введены некорректно. Попробуйте еще раз.");
            return null;
        }
    }

    // просмотреть названия всех фильмов
    public void printFilmNames() {
        for (Film film : films) {
            System.out.println(film);
        }
    }

    // распечатать расписание сеансов фильма по его номеру+проверка корректности введенного номера фильма
    public Film printFilmTable(String filmId) {
        Film film = getFilmByID(filmId);
        if (film != null) {
            System.out.println(film.printFilmSeancesTable());
            return film;
        } else {
            System.out.println("Введена неверная комнда. Попробуйте еще раз.");
            return null;
        }
    }
}
