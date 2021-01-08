package com.jueme.android.newas.network.api;

import com.jueme.android.newas.bean.JsonsRootBean;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface WeatherApi {
    @FormUrlEncoded
    @POST("/9-5")
    Call<ResponseBody> getCall1(@Query("showapi_appid") String showapi_appid, @Query("showapi_sign") String showapi_sign, @FieldMap Map<String, String> fieldMap);

    @FormUrlEncoded
    @POST("/9-2")
    Observable<JsonsRootBean> getWeatherData(@Query("showapi_appid") String showapi_appid, @Query("showapi_sign") String showapi_sign, @FieldMap Map<String, String> fieldMap);

}
