package com.jueme.android.newas;

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
}
