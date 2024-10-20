package weather.app.dto;

import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherDto {
    private JSONObject yandexJsonResponse;
    private int currentTemp;
    private String avgTempForPeriod;
    private ArrayList<String> avgTempPeriods;

    public WeatherDto(JSONObject yandexJsonResponse, int currentTemp) {
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
