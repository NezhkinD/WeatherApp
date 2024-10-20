package weather.app;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class YandexApiServiceDto {
    private JSONObject yandexJsonResponse;
    private int currentTemp;
    private String avgTempForPeriod;
    private ArrayList<String> avgTempPeriods;

    public YandexApiServiceDto(JSONObject yandexJsonResponse, int currentTemp) {
        this.yandexJsonResponse = yandexJsonResponse;
        this.currentTemp = currentTemp;
        this.avgTempPeriods = new ArrayList<>();
    }

    public JSONObject getYandexJsonResponse() {
        return yandexJsonResponse;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public String getAvgTempForPeriod() {
        return avgTempForPeriod;
    }

    public ArrayList<String> getAvgTempPeriods() {
        return avgTempPeriods;
    }

    public void addAvgTempPeriod(String tempWithData)
    {
        this.avgTempPeriods.add(tempWithData);
    }

    public void setAvgTempForPeriod(String avgTempForPeriod) {
        this.avgTempForPeriod = avgTempForPeriod;
    }
}
