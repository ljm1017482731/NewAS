package com.jueme.android.newas;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.database.City;
import com.jueme.android.newas.database.CityDao;
import com.jueme.android.newas.database.CityDatabase;
import com.jueme.android.newas.network.Network;
import com.jueme.android.newas.view.CityBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator3;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Network.DataStatusListener {
    private static final String TAG = "MainActivity";

    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;

    @BindView(R.id.start_city_activity)
    ImageView start_city_activity;

    @BindView(R.id.start_test)
    ImageView start_test;

    @BindView(R.id.indicator)
    CircleIndicator3 indicator;

    private CityWeatherDetailAdapter demoAdapter;
    private FragmentManager fm;

    //private String[] cityNames;

    private List<CityBean> cityBeans;

    private List<CityBean> cityNames;

    private int currentItem;

    private String[] test;

    private int testLength = 1;

    private CityDatabase cityDatabase;
    private CityDao cityDao;
    private List<City> allCitys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cityBeans = new ArrayList<>();

        demoAdapter = new CityWeatherDetailAdapter(this);
        viewPager2.setAdapter(demoAdapter);
        indicator.setViewPager(viewPager2);
        demoAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        start_city_activity.setOnClickListener(this);
        start_test.setOnClickListener(this);
        fm = getSupportFragmentManager();

        Network.setDataStatusListener(this);

        //test = new String[]{"惠州", "广州", "拉萨", "吉林", "北京", "呼和浩特", "兰州", "哈尔滨", "乌鲁木齐", "上海"};//
        test = new String[]{"惠州"};

        cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "city_database").allowMainThreadQueries().build();
        cityDao = cityDatabase.getCityDao();

        allCitys = cityDao.getAllCityByPosition();

        currentItem = getIntent().getIntExtra("currentItem", 0);
        Log.d(TAG, "onCreate: currentItem " + currentItem);
        cityNames = (List<CityBean>) getIntent().getSerializableExtra("cityNames");
        if (cityNames != null && cityNames.size() > 0) {
            demoAdapter.removeAllFragment();
            Network.getWeatherData(cityNames.get(0).getCityName());
        } else {
            if (allCitys != null && allCitys.size() > 0) {
                Network.getWeatherData(allCitys.get(0).getCityName());
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* Data.setDataStatusListener(this);
        currentItem = data.getIntExtra("currentItem", 0);
        demoAdapter.removeAllFragment();
        if (resultCode == RESULT_OK && requestCode == 100) {
            cityNames = (List<CityBean>) data.getSerializableExtra("cityNames");
            for (int i = 0; i < cityNames.size(); i++) {
                Log.d(TAG, "onActivityResult: " + cityNames.get(i).getCityName());
                Data.getWeatherData(cityNames.get(i).getCityName());
            }
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_city_activity:
                Intent intent = new Intent(this, CityListActivity.class);
                intent.putExtra("cityNames", (Serializable) cityBeans);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
                finish();
                break;
            case R.id.start_test:
                viewPager2.setCurrentItem(currentItem, false);
                break;
        }
    }

    @Override
    public void getDataSuccess(ShowapiResBody showapiResBody) {
        Log.d(TAG, "getDataSuccess: " + showapiResBody.getCityinfo().getC3());
        CityWeatherDetailFragment fragment = new CityWeatherDetailFragment(showapiResBody);
        demoAdapter.addFragment(fragment);
        fm.beginTransaction();
        CityBean cityBean = new CityBean(showapiResBody.getCityinfo().getC3(), showapiResBody.getNow().getTemperature());
        cityBeans.add(cityBean);

        //不会跳转到当前的item
        if (cityNames != null) {
            Log.d(TAG, "getDataSuccess cityBeans.size:  " + cityBeans.size() + "  cityNames.size " + cityNames.size());
            if (cityBeans.size() == cityNames.size()) {
                viewPager2.setCurrentItem(currentItem, false);
            }
        }
    }

    @Override
    public void getDataFailed() {
        Log.d(TAG, "getDataFailed: ");
    }

    @Override
    public void next() {
        Log.d(TAG, "next: ");
        if (cityNames != null) {
            if (testLength < cityNames.size()) {
                if (testLength < cityNames.size()) {
                    Network.getWeatherData(cityNames.get(testLength).getCityName());
                    testLength++;
                }
            }
        } else {
            if (testLength < allCitys.size()) {
                Network.getWeatherData(allCitys.get(testLength).getCityName());
                testLength++;
            }
        }
    }
}