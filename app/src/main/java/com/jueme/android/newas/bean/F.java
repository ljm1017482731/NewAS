package com.jueme.android.newas.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jueme on 2020/12/21
 **/
public class F {
    @SerializedName("night_weather_code")
    private String nightWeatherCode;
    @SerializedName("day_weather")
    private String dayWeather;
    @SerializedName("night_weather")
    private String nightWeather;
    private String jiangshui;
    @SerializedName("air_press")
    private String airPress;
    @SerializedName("night_wind_power")
    private String nightWindPower;
    @SerializedName("day_wind_power")
    private String dayWindPower;
    @SerializedName("day_weather_code")
    private String dayWeatherCode;
    @SerializedName("sun_begin_end")
    private String sunBeginEnd;
    private String ziwaixian;
    @SerializedName("day_weather_pic")
    private String dayWeatherPic;
    private int weekday;
    @SerializedName("night_air_temperature")
    private String nightAirTemperature;
    @SerializedName("day_wind_direction")
    private String dayWindDirection;
    @SerializedName("day_air_temperature")
    private String dayAirTemperature;
    @SerializedName("night_wind_direction")
    private String nightWindDirection;
    @SerializedName("night_weather_pic")
    private String nightWeatherPic;
    private String day;

    public void setNightWeatherCode(String nightWeatherCode) {
        this.nightWeatherCode = nightWeatherCode;
    }

    public String getNightWeatherCode() {
        return nightWeatherCode;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setJiangshui(String jiangshui) {
        this.jiangshui = jiangshui;
    }

    public String getJiangshui() {
        return jiangshui;
    }

    public void setAirPress(String airPress) {
        this.airPress = airPress;
    }

    public String getAirPress() {
        return airPress;
    }

    public void setNightWindPower(String nightWindPower) {
        this.nightWindPower = nightWindPower;
    }

    public String getNightWindPower() {
        return nightWindPower;
    }

    public void setDayWindPower(String dayWindPower) {
        this.dayWindPower = dayWindPower;
    }

    public String getDayWindPower() {
        return dayWindPower;
    }

    public void setDayWeatherCode(String dayWeatherCode) {
        this.dayWeatherCode = dayWeatherCode;
    }

    public String getDayWeatherCode() {
        return dayWeatherCode;
    }

    public void setSunBeginEnd(String sunBeginEnd) {
        this.sunBeginEnd = sunBeginEnd;
    }

    public String getSunBeginEnd() {
        return sunBeginEnd;
    }

    public void setZiwaixian(String ziwaixian) {
        this.ziwaixian = ziwaixian;
    }

    public String getZiwaixian() {
        return ziwaixian;
    }

    public void setDayWeatherPic(String dayWeatherPic) {
        this.dayWeatherPic = dayWeatherPic;
    }

    public String getDayWeatherPic() {
        return dayWeatherPic;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setNightAirTemperature(String nightAirTemperature) {
        this.nightAirTemperature = nightAirTemperature;
    }

    public String getNightAirTemperature() {
        return nightAirTemperature;
    }

    public void setDayWindDirection(String dayWindDirection) {
        this.dayWindDirection = dayWindDirection;
    }

    public String getDayWindDirection() {
        return dayWindDirection;
    }

    public void setDayAirTemperature(String dayAirTemperature) {
        this.dayAirTemperature = dayAirTemperature;
    }

    public String getDayAirTemperature() {
        return dayAirTemperature;
    }

    public void setNightWindDirection(String nightWindDirection) {
        this.nightWindDirection = nightWindDirection;
    }

    public String getNightWindDirection() {
        return nightWindDirection;
    }

    public void setNightWeatherPic(String nightWeatherPic) {
        this.nightWeatherPic = nightWeatherPic;
    }

    public String getNightWeatherPic() {
        return nightWeatherPic;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDay() {
        return day;
    }

}