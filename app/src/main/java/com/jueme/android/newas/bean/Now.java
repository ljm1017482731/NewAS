package com.jueme.android.newas.bean;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Jueme on 2020/12/21
 **/
public class Now {

    @SerializedName("weather_code")
    private String weatherCode;
    @SerializedName("aqiDetail")
    private Aqidetail aqidetail;
    @SerializedName("wind_direction")
    private String windDirection;
    @SerializedName("temperature_time")
    private String temperatureTime;
    @SerializedName("wind_power")
    private String windPower;
    private String aqi;
    private String sd;
    @SerializedName("weather_pic")
    private String weatherPic;
    private String weather;
    private String rain;
    private String temperature;
    public void setWeatherCode(String weatherCode) {
         this.weatherCode = weatherCode;
     }
     public String getWeatherCode() {
         return weatherCode;
     }

    public void setAqidetail(Aqidetail aqidetail) {
         this.aqidetail = aqidetail;
     }
     public Aqidetail getAqidetail() {
         return aqidetail;
     }

    public void setWindDirection(String windDirection) {
         this.windDirection = windDirection;
     }
     public String getWindDirection() {
         return windDirection;
     }

    public void setTemperatureTime(String temperatureTime) {
         this.temperatureTime = temperatureTime;
     }
     public String getTemperatureTime() {
         return temperatureTime;
     }

    public void setWindPower(String windPower) {
         this.windPower = windPower;
     }
     public String getWindPower() {
         return windPower;
     }

    public void setAqi(String aqi) {
         this.aqi = aqi;
     }
     public String getAqi() {
         return aqi;
     }

    public void setSd(String sd) {
         this.sd = sd;
     }
     public String getSd() {
         return sd;
     }

    public void setWeatherPic(String weatherPic) {
         this.weatherPic = weatherPic;
     }
     public String getWeatherPic() {
         return weatherPic;
     }

    public void setWeather(String weather) {
         this.weather = weather;
     }
     public String getWeather() {
         return weather;
     }

    public void setRain(String rain) {
         this.rain = rain;
     }
     public String getRain() {
         return rain;
     }

    public void setTemperature(String temperature) {
         this.temperature = temperature;
     }
     public String getTemperature() {
         return temperature;
     }

}