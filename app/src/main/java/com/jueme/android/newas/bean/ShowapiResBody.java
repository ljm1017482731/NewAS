package com.jueme.android.newas.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jueme on 2020/12/21
 **/
public class ShowapiResBody {
    private String time;
    @SerializedName("ret_code")
    private int retCode;
    private Now now;
    @SerializedName("cityInfo")
    private Cityinfo cityinfo;
    @SerializedName("showapi_fee_code")
    private int showapiFeeCode;

    private List<AlarmList> alarmList;

    private F f1;
    private F f2;
    private F f3;
    private F f4;
    private F f5;
    private F f6;
    private F f7;

    public F getF1() {
        return f1;
    }

    public void setF1(F f1) {
        this.f1 = f1;
    }

    public F getF2() {
        return f2;
    }

    public void setF2(F f2) {
        this.f2 = f2;
    }

    public F getF3() {
        return f3;
    }

    public void setF3(F f3) {
        this.f3 = f3;
    }

    public F getF4() {
        return f4;
    }

    public void setF4(F f4) {
        this.f4 = f4;
    }

    public F getF5() {
        return f5;
    }

    public void setF5(F f5) {
        this.f5 = f5;
    }

    public F getF6() {
        return f6;
    }

    public void setF6(F f6) {
        this.f6 = f6;
    }

    public F getF7() {
        return f7;
    }

    public void setF7(F f7) {
        this.f7 = f7;
    }

    private List<HourDataList> hourDataList;

    public List<HourDataList> getHourDataList() {
        return hourDataList;
    }

    public void setHourDataList(List<HourDataList> hourDataList) {
        this.hourDataList = hourDataList;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public Now getNow() {
        return now;
    }

    public void setCityinfo(Cityinfo cityinfo) {
        this.cityinfo = cityinfo;
    }

    public Cityinfo getCityinfo() {
        return cityinfo;
    }

    public void setShowapiFeeCode(int showapiFeeCode) {
        this.showapiFeeCode = showapiFeeCode;
    }

    public int getShowapiFeeCode() {
        return showapiFeeCode;
    }

    public List<AlarmList> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmList> alarmList) {
        this.alarmList = alarmList;
    }

}