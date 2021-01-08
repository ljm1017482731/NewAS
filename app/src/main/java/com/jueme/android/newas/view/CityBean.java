package com.jueme.android.newas.view;

import java.io.Serializable;

/**
 * Created by Jueme on 2020/12/19
 **/
public class CityBean implements Serializable {
    private String cityName;
    private String temperature;

    public CityBean() {
    }

    public CityBean(String cityName, String temperature) {
        this.cityName = cityName;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
