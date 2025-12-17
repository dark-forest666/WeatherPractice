package com.example.weatherpractice.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;


public class WeatherResponse {

    private String status;
    private String count;
    private Forecast[] forecasts;
    private int infocode;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCount() { return count; }
    public void setCount(String count) { this.count = count; }

    public Forecast[] getForecasts() { return forecasts; }
    public void setForecasts(Forecast[] forecasts) { this.forecasts = forecasts; }
    public int getInfocode() { return infocode; }
    public void setInfocode(int infocode) { this.infocode = infocode; }

    // 内部类：预报信息
    public static class Forecast {

        private City city;
        private Live live;
        private Hourly[] hourly;
        private Daily[] daily;

        public City getCity() { return city; }
        public Live getLive() { return live; }
        public Hourly[] getHourly() { return hourly; }
        public Daily[] getDaily() { return daily; }

        public static class City {

            private String name;
            private String adcode;
            private String center;
            private String level;

            public String getName() { return name; }
            public String getAdcode() { return adcode; }
            public String getCenter() { return center; }
            public String getLevel() { return level; }
        }

        public static class Live {

            private String temperature;
            private String humidity;
            private String windDirection;
            @Expose
            private String windPower;
            @Expose
            private String weather;
            @Expose
            private String reportTime;

            public String getTemperature() { return temperature; }
            public String getHumidity() { return humidity; }
            public String getWindDirection() { return windDirection; }
            public String getWindPower() { return windPower; }
            public String getWeather() { return weather; }
            public String getReportTime() { return reportTime; }
        }


        public static class Hourly {

            private String time;
            private String temperature;
            private String humidity;
            private String windDirection;
            private String windPower;
            private String weather;

            public String getTime() { return time; }
            public String getTemperature() { return temperature; }
            public String getHumidity() { return humidity; }
            public String getWindDirection() { return windDirection; }
            public String getWindPower() { return windPower; }
            public String getWeather() { return weather; }
        }


        public static class Daily {

            private String date;
            private String temperatureMax;
            private String temperatureMin;
            private String weatherDay;
            private String weatherNight;

            public String getDate() { return date; }
            public String getTemperatureMax() { return temperatureMax; }
            public String getTemperatureMin() { return temperatureMin; }
            public String getWeatherDay() { return weatherDay; }
            public String getWeatherNight() { return weatherNight; }
        }
    }
}
