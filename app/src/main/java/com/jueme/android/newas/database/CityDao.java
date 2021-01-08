package com.jueme.android.newas.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Jueme on 2020/12/28
 **/
@Dao
public interface CityDao {
    @Insert// 这些修饰词会在编译时候生成代码
    void insertCitys(City... city);   // 如果是一个参数就写 Word word，多个参数就这样写

    @Update
    void updataCitys(City... city);

    @Delete
    void deleteCitys(City... city);

    @Query("UPDATE city_table set position=:toPosition where id=:id")   //Update person set name= ? , age= ? where id= ? "
    void updateByPosition(int id,int toPosition);

    @Query("UPDATE city_table set position=:toPosition where cityName=:cityName")   //Update person set name= ? , age= ? where id= ? "
    void updateByName(String cityName,int toPosition);

    @Query("DELETE FROM city_table where cityName=:cityName") //Delete from person where id= ? cityName  DELETE  FROM FORGET where wordname=:name
    void deleteCityByName(String cityName);

    @Query("DELETE FROM city_table")
    void deleteAllCitys();

    @Query("SELECT * FROM city_table")  // 获取所有的WORD，并且按照id降序排序  ORDER BY ID DESC
    List<City> getAllCitys();

    @Query("SELECT * FROM city_table ORDER BY POSITION ASC")  // 获取所有的WORD，并且按照id降序排序  ORDER BY ID DESC
    List<City> getAllCityByPosition();

    @Query("SELECT count(id) from city_table")  // 获取总数
    int getCityCount();

    @Query("SELECT MAX(position) from city_table")  // 获取最大值
    int getMaxNum();

    @Query("SELECT * FROM city_table where position=:position")  // 根据位置获取
    City getCityByPosition(int position);

    @Query("SELECT * FROM city_table where cityName=:cityName")  // 根据位置获取
    City getCityByName(String cityName);
}
