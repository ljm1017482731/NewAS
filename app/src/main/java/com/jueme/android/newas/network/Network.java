package com.jueme.android.newas.network;

import android.util.Log;

import com.jueme.android.newas.Config;
import com.jueme.android.newas.bean.JsonsRootBean;
import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.network.api.WeatherApi;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jueme on 2020/12/23
 **/
public class Network {
    private static final String TAG = "DataTest";
    private static DataStatusListener mDataStatusListener;

    public interface DataStatusListener {
        void getDataSuccess(ShowapiResBody showapiResBody);

        void getDataFailed();

        void next();
    }

    public static void setDataStatusListener(DataStatusListener dataStatusListener) {
        mDataStatusListener = dataStatusListener;
    }

    public static void getWeatherData(String cityName) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //打印retrofit日志
                Log.e("RetrofitLog", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()//okhttp设置部分，此处还可再设置网络参数
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_POSTER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build();
        WeatherApi request = retrofit.create(WeatherApi.class);
        Map<String, String> map = new HashMap<>();

        map.put("area", cityName);
        map.put("needMoreDay", "1");
        map.put("needHourData", "1");
        map.put("needAlarm", "1");

        Observable<JsonsRootBean> call = request.getWeatherData(Config.SHOWAPI_APPID, Config.SHOWAPI_SIGN, map);

        call.observeOn(AndroidSchedulers.mainThread()). //Schedulers.io()
                subscribe(new Observer<JsonsRootBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull JsonsRootBean jsonRootBean) {
                ShowapiResBody showapiResBody = jsonRootBean.getShowapiResBody();
                Log.d(TAG, "onResponse: getC3 " + showapiResBody.getCityinfo().getC3());
                if (showapiResBody != null) {
                    mDataStatusListener.getDataSuccess(showapiResBody);
                } else {
                    mDataStatusListener.getDataFailed();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
                mDataStatusListener.getDataFailed();
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
                mDataStatusListener.next();
            }
        });
    }

    //后面改为这个
    public static WeatherApi getWeatherApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_POSTER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        return weatherApi;
    }
}
