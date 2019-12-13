package com.example.weatherretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServiceInterface {
    @GET
    Call<WeatherData> getWeatherInfo(@Url String url);
}
