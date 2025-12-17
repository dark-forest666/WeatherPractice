package com.example.weatherpractice;

import com.example.weatherpractice.model.GeoResponse;
import com.example.weatherpractice.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // 高德天气接口// ApiService.java（追加方法）
    @GET("v3/geocode/geo")
    Call<GeoResponse> getGeoCode(
            @Query("address") String address,
            @Query("key") String apiKey
    );
    @GET("v3/weather/weatherInfo")
    Call<WeatherResponse> getWeatherInfo(
            @Query("city") String city,
            @Query("extensions") String extensions,
            @Query("key") String apiKey
    );
}
