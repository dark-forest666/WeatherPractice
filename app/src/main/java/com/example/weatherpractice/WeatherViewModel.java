package com.example.weatherpractice;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.weatherpractice.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.weatherpractice.model.GeoResponse;

public class WeatherViewModel extends AndroidViewModel {

    private MutableLiveData<WeatherResponse> weatherData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private ApiService apiService;

    public WeatherViewModel(@NonNull Application application) {
        super(application);

        // 初始化 Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restapi.amap.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public LiveData<WeatherResponse> getWeatherData() {
        return weatherData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // 获取天气数据
    // WeatherViewModel.java（修改 fetchWeather 方法）
    public void fetchWeatherByCityName(String cityName, String apiKey) {
        // 第一步：获取 adcode
        Call<GeoResponse> geoCall = apiService.getGeoCode(cityName, apiKey);
        geoCall.enqueue(new Callback<GeoResponse>() {
            @Override
            public void onResponse(Call<GeoResponse> call, Response<GeoResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    String adcode = response.body().getAdcode();
                    // 第二步：用 adcode 查天气
                    fetchWeatherByAdcode(adcode, apiKey);
                } else {
                    errorMessage.setValue("无法找到城市「" + cityName + "」，请检查名称是否正确");
                }
            }

            @Override
            public void onFailure(Call<GeoResponse> call, Throwable t) {
                errorMessage.setValue("地理编码失败：" + t.getMessage());
            }
        });
    }

    // 私有方法：通过 adcode 获取天气
    private void fetchWeatherByAdcode(String adcode, String apiKey) {
        Call<WeatherResponse> weatherCall = apiService.getWeatherInfo(adcode, "base", apiKey);
        weatherCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    weatherData.setValue(response.body());
                } else {
                    errorMessage.setValue("天气数据获取失败：" + response.code());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                errorMessage.setValue("网络错误：" + t.getMessage());
            }
        });
    }
}
