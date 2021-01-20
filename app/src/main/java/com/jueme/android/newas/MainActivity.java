package com.jueme.android.newas;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.network.Network;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator3;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Network.DataStatusListener {
    private static final String TAG = "MainActivityTest";

    @BindView(R.id.viewPager)
    ViewPager2 viewPager2;

    @BindView(R.id.start_city_activity)
    ImageView start_city_activity;

    @BindView(R.id.start_test)
    ImageView start_test;

    @BindView(R.id.indicator)
    CircleIndicator3 indicator;

    private CityWeatherDetailAdapter cityWeatherDetailAdapter;
    private FragmentManager fm;

    private List<ShowapiResBody> bodyList;

    private int currentItem;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cityWeatherDetailAdapter = new CityWeatherDetailAdapter(this);
        viewPager2.setAdapter(cityWeatherDetailAdapter);
        indicator.setViewPager(viewPager2);
        cityWeatherDetailAdapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());

        start_city_activity.setOnClickListener(this);
        start_test.setOnClickListener(this);
        fm = getSupportFragmentManager();

        Network.setDataStatusListener(this);
        bodyList = Util.readJsonData(getApplicationContext());

        if (bodyList != null) {
            String oldTime = bodyList.get(0).getF1().getDay();
            //判断当前是否有网络，没有网络直接加载本地的数据
            if (Util.isNetworkConnected(getApplicationContext()) && Util.isNetworkOnline()) {
                //如果时间戳一样就说明数据不需要重新加载，否则就要重新获取数据
                if (Util.getTime().equals(oldTime)) {
                    if (bodyList != null) {
                        for (int i = 0; i < bodyList.size(); i++) {
                            CityWeatherDetailFragment fragment = new CityWeatherDetailFragment(bodyList.get(i));
                            cityWeatherDetailAdapter.addFragment(fragment);
                        }
                        fm.beginTransaction();
                    }
                } else {
                    Network.getWeatherData(bodyList.get(0).getCityinfo().getC3());
                }
            } else {
                if (bodyList != null) {
                    for (int i = 0; i < bodyList.size(); i++) {
                        CityWeatherDetailFragment fragment = new CityWeatherDetailFragment(bodyList.get(i));
                        cityWeatherDetailAdapter.addFragment(fragment);
                    }
                    fm.beginTransaction();
                }
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
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        cityWeatherDetailAdapter.removeAllFragment(fm);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode " + requestCode + " resultCode " + resultCode);
        if (resultCode == RESULT_OK && requestCode == 100) {
            currentItem = data.getIntExtra("currentItem", 0);
            List<ShowapiResBody> jsonData = Util.readJsonData(getApplicationContext());
            if (jsonData != null) {
                for (int i = 0; i < jsonData.size(); i++) {
                    Log.d(TAG, "onActivityResult: " + jsonData.get(i).getCityinfo().getC3());
                    CityWeatherDetailFragment fragment = new CityWeatherDetailFragment(jsonData.get(i));
                    cityWeatherDetailAdapter.addFragment(fragment);
                    if (i == currentItem) {
                        viewPager2.setCurrentItem(i, false);
                    }
                }
                fm.beginTransaction();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_city_activity:
                Intent intent = new Intent(this, CityListActivity.class);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
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
        cityWeatherDetailAdapter.addFragment(fragment);
        fm.beginTransaction();
        bodyList.set(count-1,showapiResBody);
    }

    @Override
    public void getDataFailed() {
        Log.d(TAG, "getDataFailed: ");
    }

    @Override
    public void next() {
        Log.d(TAG, "next: ");
        if (bodyList != null) {
            if (count < bodyList.size()) {
                Network.getWeatherData(bodyList.get(count).getCityinfo().getC3());
                count++;
            } else {
                //加载完毕后将新的数据保存
                Util.saveJsonData(getApplicationContext(), bodyList);
            }
        }
    }
}