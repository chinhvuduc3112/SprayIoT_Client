package com.vuduc.network;

/**
 * Created by VuDuc on 8/11/2017.
 */

public class ApiUtils {
    private ApiUtils() {}

    public static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/";
    public static final String SPRAY_IOT = "192.168.0.105:8090";

    public static WeatherApiInterface getWeatherApiService() {
        return RetrofitClient.getClient(WEATHER_URL).create(WeatherApiInterface.class);
    }

    public static SprayIoTApiInterface getSprayIoTApiInterface() {
        return RetrofitClient.getClient(SPRAY_IOT).create(SprayIoTApiInterface.class);
    }
}
