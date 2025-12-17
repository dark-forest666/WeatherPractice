package com.example.weatherpractice;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import com.example.weatherpractice.model.WeatherResponse;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textCity, textWeather, textTemp, textHumidity, textWind, textTime, textError;
    private CardView cardWeather;
    private Button btnFetch;
    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        btnFetch = findViewById(R.id.btnFetch);
        cardWeather = findViewById(R.id.cardWeather);
        textCity = findViewById(R.id.textCity);
        textWeather = findViewById(R.id.textWeather);
        textTemp = findViewById(R.id.textTemp);
        textHumidity = findViewById(R.id.textHumidity);
        textWind = findViewById(R.id.textWind);
        textTime = findViewById(R.id.textTime);
        textError = findViewById(R.id.textError);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        // 观察数据变化
        viewModel.getWeatherData().observe(this, weather -> {
            if (weather != null && weather.getForecasts() != null && weather.getForecasts().length > 0) {
                WeatherResponse.Forecast forecast = weather.getForecasts()[0];
                textCity.setText("城市：" + forecast.getCity().getName());
                textWeather.setText("天气：" + forecast.getLive().getWeather());
                textTemp.setText("温度：" + forecast.getLive().getTemperature() + "°C");
                textHumidity.setText("湿度：" + forecast.getLive().getHumidity() + "%");
                textWind.setText("风力：" + forecast.getLive().getWindDirection() + forecast.getLive().getWindPower());
                textTime.setText("更新时间：" + forecast.getLive().getReportTime());

                cardWeather.setVisibility(View.VISIBLE);
                textError.setVisibility(View.GONE);
            }
        });

        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null) {
                textError.setText(message);
                textError.setVisibility(View.VISIBLE);
                cardWeather.setVisibility(View.GONE);
            }
        });

        btnFetch.setOnClickListener(v -> {
            String cityName = editTextCity.getText().toString().trim();
            if (cityName.isEmpty()) {
                textError.setText("请输入城市名称！");
                textError.setVisibility(View.VISIBLE);
                return;
            }

            String apiKey = "4d4766ab506fbb703067fe0ce7e3c09f";
            viewModel.fetchWeatherByCityName(cityName, apiKey);
        });
    }
}