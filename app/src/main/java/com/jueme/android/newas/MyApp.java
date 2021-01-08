package com.jueme.android.newas;

import android.app.Application;
import android.content.Context;

import com.lljjcoder.style.citylist.utils.CityListLoader;


/**
 * Created by Jueme on 2020/12/28
 **/

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 预先加载一级列表所有城市的数据
         */
        CityListLoader.getInstance().loadCityData(this);

        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);

    }

}
