package com.jueme.android.newas.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Jueme on 2020/12/28
 **/
@Entity(tableName = "city_table")
public class City {
    @PrimaryKey(autoGenerate = true) // 将id设置为主键，并且自增
    private int id;
    @ColumnInfo(name = "cityName")   // 城市的名称
    private String cityName;
    @ColumnInfo(name = "position",defaultValue= "0")   // 用来记录位置
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public City() {
    }

    public City(String cityName) {
        this.cityName = cityName;
    }

    public City(String cityName, int position) {
        this.cityName = cityName;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
