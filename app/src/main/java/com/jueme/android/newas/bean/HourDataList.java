package com.jueme.android.newas.bean;

/**
 * Created by Jueme on 2020/12/21
 **/
public class HourDataList {

    /**
     * weather_code : 00
     * aqiDetail : {"num":"39","co":"0.8","so2":"6","area":"丽江","o3":"51","no2":"20","aqi":"38","quality":"优质","pm10":"38","pm2_5":"20","o3_8h":"49","primary_pollutant":""}
     * wind_direction : 西风
     * temperature_time : 00:00
     * wind_power : 1级
     * aqi : 38
     * sd : 67%
     * weather_pic : http://app1.showapi.com/weather/icon/night/00.png
     * weather : 晴
     * rain : 0.0
     * temperature : 0
     */

    private String weather_code;
    /**
     * num : 39
     * co : 0.8
     * so2 : 6
     * area : 丽江
     * o3 : 51
     * no2 : 20
     * aqi : 38
     * quality : 优质
     * pm10 : 38
     * pm2_5 : 20
     * o3_8h : 49
     * primary_pollutant :
     */

    private AqiDetailBean aqiDetail;
    private String wind_direction;
    private String temperature_time;
    private String wind_power;
    private String aqi;
    private String sd;
    private String weather_pic;
    private String weather;
    private String rain;
    private String temperature;

    public String getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(String weather_code) {
        this.weather_code = weather_code;
    }

    public AqiDetailBean getAqiDetail() {
        return aqiDetail;
    }

    public void setAqiDetail(AqiDetailBean aqiDetail) {
        this.aqiDetail = aqiDetail;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getTemperature_time() {
        return temperature_time;
    }

    public void setTemperature_time(String temperature_time) {
        this.temperature_time = temperature_time;
    }

    public String getWind_power() {
        return wind_power;
    }

    public void setWind_power(String wind_power) {
        this.wind_power = wind_power;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getSd() {
        return sd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getWeather_pic() {
        return weather_pic;
    }

    public void setWeather_pic(String weather_pic) {
        this.weather_pic = weather_pic;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public static class AqiDetailBean {
        private String num;
        private String co;
        private String so2;
        private String area;
        private String o3;
        private String no2;
        private String aqi;
        private String quality;
        private String pm10;
        private String pm2_5;
        private String o3_8h;
        private String primary_pollutant;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2_5() {
            return pm2_5;
        }

        public void setPm2_5(String pm2_5) {
            this.pm2_5 = pm2_5;
        }

        public String getO3_8h() {
            return o3_8h;
        }

        public void setO3_8h(String o3_8h) {
            this.o3_8h = o3_8h;
        }

        public String getPrimary_pollutant() {
            return primary_pollutant;
        }

        public void setPrimary_pollutant(String primary_pollutant) {
            this.primary_pollutant = primary_pollutant;
        }
    }
}
