package weather.app;

import weather.app.dto.WeatherDto;
import weather.app.service.YandexApiService;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static final String MESSAGE_INPUT_LAT = "Введите широту:";
    private static final String MESSAGE_INPUT_LON = "Введите долготу:";
    private static final String MESSAGE_INPUT_LIMIT = "Укажите за какой период посчитать прогноз средней температуры (начиная с сегодняшнего дня, по-умолчанию 0; макс 7):";
    private static final String MESSAGE_OUTPUT_CURRENT_TEMP = "Текущая температура: ";
    private static final String MESSAGE_OUTPUT_FORECAST_TEMP = "Прогноз средней температуры: ";
    private static final String MESSAGE_OUTPUT_YANDEX_ANSW = "Ответ от yandex weather: ";
    private static final String MESSAGE_OUTPUT_EXIT = "Завершаем работу.\n-----------------";
    private static final String MESSAGE_WARNING_LIMIT_MAX = "Внимание! Вы указали значение limit > 7, прогноз будет предоставлен на ближайшие 7 дней.";
    private static final String MESSAGE_ERROR_LAT = "Введено неверное значение широты. Введите другое значение.";
    private static final String MESSAGE_ERROR_LON = "Введено неверное значение долготы. Введите другое значение.";
    private static final String MESSAGE_ERROR_INPUT_VALUE = "Ошибка: введите корректное число. Вы ввели ";


    public static void main(String[] args) {
        try {
            Main.scanner = new Scanner(System.in);
            YandexApiService yandexApiService = new YandexApiService();
            int step = 0;

            while (true) {
                double lat = 0;
                double lon = 0;
                int limit = 0;

                switch (step) {
                    case 0:
                        System.out.println(Main.MESSAGE_INPUT_LAT);
                        lat = Main.validateInputDouble(Main.scanner);
                        if (lat == Double.POSITIVE_INFINITY || lat == Double.NEGATIVE_INFINITY || lat == 0.0) {
                            System.out.println(MESSAGE_ERROR_LAT);
                            continue;
                        }
                        step++;
                    case 1:
                        System.out.println(Main.MESSAGE_INPUT_LON);
                        lon = Main.validateInputDouble(Main.scanner);
                        if (lon == Double.POSITIVE_INFINITY || lon == Double.NEGATIVE_INFINITY || lon == 0.0) {
                            System.out.println(MESSAGE_ERROR_LON);
                            continue;
                        }
                        step++;
                    case 2:
                        System.out.println(Main.MESSAGE_INPUT_LIMIT);
                        limit = validateInputInt(scanner);
                        if (limit == -1) {
                            continue;
                        }
                        if (limit > 7) {
                            System.out.println(Main.MESSAGE_WARNING_LIMIT_MAX);
                        }
                    default:
                        step = 0;
                }

                WeatherDto dto = yandexApiService.getTemp(lat, lon, limit);

                System.out.println(MESSAGE_OUTPUT_CURRENT_TEMP + dto.getCurrentTemp());
                if (limit > 0) {
                    System.out.println(MESSAGE_OUTPUT_FORECAST_TEMP + dto.getAvgTempForPeriod());
                    for (String avgTempPeriod : dto.getAvgTempPeriods()) {
                        System.out.println(avgTempPeriod);
                    }
                }

                System.out.println(MESSAGE_OUTPUT_YANDEX_ANSW + dto.getYandexJsonResponse().toString());
                System.out.println("----------\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Double validateInputDouble(Scanner scanner) {
        String input = null;

        try {
            String next = scanner.next();
            quit(next);
            input = next.replace(",", ".");
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_INPUT_VALUE + input);
        }
        return 0.0;
    }

    private static Integer validateInputInt(Scanner scanner) {
        String next = null;
        try {
            next = scanner.next();
            quit(next);

            return Integer.parseInt(next);
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_INPUT_VALUE + next);
        }
        return -1;
    }

    private static void quit(String input)
    {
        if (input.equals("q")){
            System.out.println(MESSAGE_OUTPUT_EXIT);
            System.exit(0);
        }
    }
}