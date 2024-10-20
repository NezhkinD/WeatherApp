package weather.app.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;
import weather.app.dto.WeatherDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class YandexApiService {
    private final String yandexApiKey;
    private final String yandexApiAddress;

    public YandexApiService() {
        Dotenv dotenv = Dotenv.load();
        this.yandexApiKey = dotenv.get("YANDEX_API_KEY");
        this.yandexApiAddress = dotenv.get("YANDEX_API_ADDRESS");
    }

    public WeatherDto getTemp(double lat, double lon, int limit) throws URISyntaxException, IOException, InterruptedException {
        String uri = yandexApiAddress + "?lat=" + lat + "&lon=" + lon;
        if (limit > 0) {
            uri = uri + "&limit=" + limit;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(uri)).header("X-Yandex-API-Key", yandexApiKey).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject jsonResponse = new JSONObject(response.body());
        int currentTemp = jsonResponse.getJSONObject("fact").getInt("temp");
        WeatherDto yandexApiServiceDto = new WeatherDto(jsonResponse, currentTemp);

        return this.getAvgTemp(jsonResponse, yandexApiServiceDto);
    }

    private WeatherDto getAvgTemp(JSONObject jsonObject, WeatherDto dto) {
        ArrayList<Integer> avgTemps = new ArrayList<Integer>();
        ArrayList<String> dates = new ArrayList<String>();

        JSONArray forecasts = jsonObject.getJSONArray("forecasts");
        for (int i = 0; i < forecasts.length(); i++) {
            JSONObject forecast = forecasts.getJSONObject(i);
            String date = forecast.getString("date");
            int tempAvg = forecast.getJSONObject("parts").getJSONObject("day").getInt("temp_avg");

            avgTemps.add(tempAvg);
            dates.add(date);
            dto.addAvgTempPeriod(date + ": " + tempAvg);
        }

        double average = avgTemps
                .stream()
                .mapToInt(val -> val)
                .average()
                .orElse(0);

        String averageFormated = String.format("%.1f",average);
        dto.setAvgTempForPeriod(averageFormated + " (за период с " + dates.get(0) + " по " + dates.get(dates.size() - 1) + ")");
        return dto;
    }
}
