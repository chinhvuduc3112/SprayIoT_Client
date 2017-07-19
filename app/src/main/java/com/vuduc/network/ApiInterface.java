package com.vuduc.network;


import com.vuduc.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by VuDuc on 7/17/2017.
 */

public interface ApiInterface {
    @GET("weather?")
    Call<WeatherResponse> getWeatherPresent(@Query("q") String cityName, @Query("appid") String apiKey);
}
