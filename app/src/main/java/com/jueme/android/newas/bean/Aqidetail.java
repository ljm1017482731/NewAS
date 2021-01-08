package com.jueme.android.newas.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jueme on 2020/12/21
 **/
public class Aqidetail {

    private String num;
    private String co;
    private String so2;
    private String area;
    private String o3;
    private String no2;
    private String aqi;
    private String quality;
    private String pm10;
    @SerializedName("pm2_5")
    private String pm25;
    @SerializedName("o3_8h")
    private String o38h;
    @SerializedName("primary_pollutant")
    private String primaryPollutant;
    public void setNum(String num) {
         this.num = num;
     }
     public String getNum() {
         return num;
     }

    public void setCo(String co) {
         this.co = co;
     }
     public String getCo() {
         return co;
     }

    public void setSo2(String so2) {
         this.so2 = so2;
     }
     public String getSo2() {
         return so2;
     }

    public void setArea(String area) {
         this.area = area;
     }
     public String getArea() {
         return area;
     }

    public void setO3(String o3) {
         this.o3 = o3;
     }
     public String getO3() {
         return o3;
     }

    public void setNo2(String no2) {
         this.no2 = no2;
     }
     public String getNo2() {
         return no2;
     }

    public void setAqi(String aqi) {
         this.aqi = aqi;
     }
     public String getAqi() {
         return aqi;
     }

    public void setQuality(String quality) {
         this.quality = quality;
     }
     public String getQuality() {
         return quality;
     }

    public void setPm10(String pm10) {
         this.pm10 = pm10;
     }
     public String getPm10() {
         return pm10;
     }

    public void setPm25(String pm25) {
         this.pm25 = pm25;
     }
     public String getPm25() {
         return pm25;
     }

    public void setO38h(String o38h) {
         this.o38h = o38h;
     }
     public String getO38h() {
         return o38h;
     }

    public void setPrimaryPollutant(String primaryPollutant) {
         this.primaryPollutant = primaryPollutant;
     }
     public String getPrimaryPollutant() {
         return primaryPollutant;
     }

}