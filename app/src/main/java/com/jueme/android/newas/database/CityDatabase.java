package com.jueme.android.newas.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by Jueme on 2020/12/28
 **/
@Database(entities = {City.class},version = 1,exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {
    public abstract CityDao getCityDao();
}
