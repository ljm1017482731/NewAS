package com.jueme.android.newas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jueme.android.newas.bean.ShowapiResBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jueme on 2020/12/21
 **/
public class Util {

    private static final String TAG = "Util";

    public static String getWeek(int num) {
        String week = "";
        switch (num) {
            case 1:
                week = "一";
                break;
            case 2:
                week = "二";
                break;
            case 3:
                week = "三";
                break;
            case 4:
                week = "四";
                break;
            case 5:
                week = "五";
                break;
            case 6:
                week = "六";
                break;
            case 7:
                week = "天";
                break;
        }
        return week;
    }

    /**
     * 将天气的json数据保存
     *
     * @param content
     */
    public static void saveJsonData(Context context,Object object) {
        String filename = "weatherData.json";
        try {
            OutputStream out = context.openFileOutput(filename,Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String s = gson.toJson(object);
            out.write(s.getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地读取json
     */
    public static List<ShowapiResBody> readJsonData(Context context) {
        StringBuilder sb = new StringBuilder();
        try {
            File file = new File("data/user/0/com.jueme.android.newas/files/weatherData.json");
            if(!file.exists()){
                return null;
            }
            InputStream in =  context.openFileInput("weatherData.json");
            InputStreamReader reader = new InputStreamReader(in,"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "readJsonData: "+e.toString());
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<ShowapiResBody> bodies = gson.fromJson(sb.toString(),new TypeToken<List<ShowapiResBody>>(){}.getType());
        return bodies;
    }

    public static String getTime(){
        long time=System.currentTimeMillis();
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
        Date d=new Date(time);
        String t=format.format(d);
        return t;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isNetworkOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("ping -c 3 www.baidu.com");
            int exitValue = ipProcess.waitFor();
            Log.i("Avalible", "Process:"+exitValue);
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
