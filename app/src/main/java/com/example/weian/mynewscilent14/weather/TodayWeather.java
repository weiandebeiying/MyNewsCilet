package com.example.weian.mynewscilent14.weather;

/**
 * Created by weian on 2017/7/6.
 */

public class TodayWeather {
    private String city;
    private String aqi;
    private String temperature;
    private String time;

    @Override
    public String toString() {
        return "TodayWeather{" +
                "city='" + city + '\'' +
                ", aqi='" + aqi + '\'' +
                ", temperature='" + temperature + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TodayWeather(String city, String aqi, String temperature, String time) {
        this.city = city;
        this.aqi = aqi;
        this.temperature = temperature;
        this.time = time;
    }
}
