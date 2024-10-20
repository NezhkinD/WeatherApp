package weather.app;

import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static final String MESSAGE_INPUT_LAT = "Введите широту:";
    private static final String MESSAGE_INPUT_LON = "Введите долготу:";
    private static final String MESSAGE_INPUT_LIMIT = "Укажите за какой период посчитать прогноз средней температуры (начиная с сегодняшнего дня, по-умолчанию 0; макс 7):";
    private static final String MESSAGE_OUTPUT_CURRENT_TEMP = "Текущая температура: ";
    private static final String MESSAGE_OUTPUT_FORECAST_TEMP = "Прогноз средней температуры: ";
    private static final String MESSAGE_OUTPUT_YANDEX_ANSW = "Ответ от yandex weather: ";
    private static final String MESSAGE_WARNING_LIMIT_MAX = "Внимание! Вы указали значение limit > 7, прогноз будет предоставлен на ближайшие 7 дней.";
    private static final String MESSAGE_ERROR_LAT = "Введено неверное значение широты. Введите другое значение.";
    private static final String MESSAGE_ERROR_LON = "Введено неверное значение долготы. Введите другое значение.";
    private static final String MESSAGE_ERROR_DOUBLE = "Ошибка: введите корректное число. Вы ввели ";


    public static void main(String[] args) {
        try {
            Main.scanner = new Scanner(System.in);
            YandexApiService yandexApiService = new YandexApiService();

            while (true) {
                System.out.println(Main.MESSAGE_INPUT_LAT);
                double lat = Main.validateInputDouble(Main.scanner);
                if (lat == Double.POSITIVE_INFINITY || lat == Double.NEGATIVE_INFINITY || lat == 0.0) {
                    System.out.println(MESSAGE_ERROR_LAT);
                    continue;
                }

                System.out.println(Main.MESSAGE_INPUT_LON);
                double lon = Main.validateInputDouble(Main.scanner);
                if (lon == Double.POSITIVE_INFINITY || lon == Double.NEGATIVE_INFINITY || lon == 0.0) {
                    System.out.println(MESSAGE_ERROR_LON);
                    continue;
                }

                System.out.println(Main.MESSAGE_INPUT_LIMIT);
                int limit = scanner.nextInt();
                if (limit > 7){
                    System.out.println(Main.MESSAGE_WARNING_LIMIT_MAX);
                }

                YandexApiServiceDto dto = yandexApiService.getTemp(lat, lon, limit);

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

    protected static Double validateInputDouble(Scanner scanner) {
        String input = null;

        try {
            input = scanner.next().replace(",", ".");
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println(MESSAGE_ERROR_DOUBLE + input);
        }
        return 0.0;
    }
}