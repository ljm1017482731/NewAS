package com.jueme.android.newas;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jueme.android.newas.bean.F;
import com.jueme.android.newas.bean.ShowapiResBody;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Jueme on 2020/12/16
 **/
public class CityWeatherDetailFragment extends Fragment {
    private static final String TAG = "CWDFragment";
    @BindView(R.id.now_weather)
    TextView now_weather;

    @BindView(R.id.city_name)
    TextView city_name;

    @BindView(R.id.tv_week_today)
    TextView tv_week_today;

    @BindView(R.id.tv_high_low_temperature)
    TextView tv_high_low_temperature;

    @BindView(R.id.rv_weather_oneHour)
    WeatherOneHourRecyclerView rv_weather_oneHour;

    @BindView(R.id.rv_weather_week)
    RecyclerView rv_weather_week;


    private String cityName;
    private String nowWeather;

    private ShowapiResBody showapiResBody;

    public CityWeatherDetailFragment(ShowapiResBody showapiResBody) {
        this.showapiResBody = showapiResBody;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.weather_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        city_name.setText(showapiResBody.getCityinfo().getC3());
        now_weather.setText(showapiResBody.getNow().getWeather());
        //星期
        String week = Util.getWeek(showapiResBody.getF1().getWeekday());
        tv_week_today.setText(String.format(getResources().getString(R.string.week_today), week));
        //高低温
        String dayAirTemperature = showapiResBody.getF1().getDayAirTemperature();
        String nightAirTemperature = showapiResBody.getF1().getNightAirTemperature();
        tv_high_low_temperature.setText(String.format(getResources().getString(R.string.high_low_temperature), dayAirTemperature, nightAirTemperature));
        //24小时天气详情
        HoursWeatherAdapter hoursWeatherAdapter = new HoursWeatherAdapter(getContext(), showapiResBody.getHourDataList());
        LinearLayoutManager mRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerViewLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_weather_oneHour.setLayoutManager(mRecyclerViewLayoutManager);
        rv_weather_oneHour.setAdapter(hoursWeatherAdapter);

        //一个星期的天气,当天的天气不需要再显式
        OneWeekWeatherAdapter oneWeekWeatherAdapter = new OneWeekWeatherAdapter(getContext(), showapiResBody);
        LinearLayoutManager weather_week_LayoutManager = new LinearLayoutManager(getContext());
        rv_weather_week.setLayoutManager(weather_week_LayoutManager);
        rv_weather_week.setAdapter(oneWeekWeatherAdapter);
        return view;
    }
}
