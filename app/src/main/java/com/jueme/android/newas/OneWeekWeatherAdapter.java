package com.jueme.android.newas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jueme.android.newas.bean.F;
import com.jueme.android.newas.bean.ShowapiResBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Jueme on 2020/12/21
 **/
public class OneWeekWeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "OneWeekWeatherAdapter";

    private List<F> fList;

    private List<HashMap<String, String>> hashMapList;

    private Context mContext;

    final private int WEATHER_ITEM_VIEW = 1;
    final private int WEATHER_TODAY_VIEW = 2;
    final private int WEATHER_DETAIL_VIEW = 3;

    final private String FIRST_TITLE = "first_title";
    final private String FIRST_VALUE = "first_value";
    final private String SECOND_TITLE = "second_title";
    final private String SECOND_VALUE = "second_value";

    private String weatherToday = "";

    public OneWeekWeatherAdapter(Context context, ShowapiResBody showapiResBody) {
        mContext = context;
        fList = new ArrayList<>();
        hashMapList = new ArrayList<>();
        F f2 = showapiResBody.getF2();
        F f3 = showapiResBody.getF3();
        F f4 = showapiResBody.getF4();
        F f5 = showapiResBody.getF5();
        F f6 = showapiResBody.getF6();
        F f7 = showapiResBody.getF7();
        fList.add(f2);
        fList.add(f3);
        fList.add(f4);
        fList.add(f5);
        fList.add(f6);
        fList.add(f7);

        F f1 = showapiResBody.getF1();

        HashMap<String, String> hashMap = new HashMap<>();
        weatherToday = String.format(mContext.getResources().getString(R.string.weather_summary),f1.getDayWeather(),f1.getDayAirTemperature(),f1.getNightWeather(),f1.getNightAirTemperature());
        hashMap.put(FIRST_VALUE, weatherToday);
        hashMapList.add(hashMap);

        String sunBeginEnd[] = f1.getSunBeginEnd().split("\\|");
        hashMap = new HashMap<>();
        //日出
        hashMap.put(FIRST_TITLE, "日出");
        hashMap.put(FIRST_VALUE, sunBeginEnd[0]);
        //日落
        hashMap.put(SECOND_TITLE, "日落");
        hashMap.put(SECOND_VALUE, sunBeginEnd[1]);
        hashMapList.add(hashMap);

        hashMap = new HashMap<>();
        //降雨概率
        hashMap.put(FIRST_TITLE, "降雨概率");
        hashMap.put(FIRST_VALUE, f1.getJiangshui());
        //湿度
        hashMap.put(SECOND_TITLE, "湿度");
        hashMap.put(SECOND_VALUE, showapiResBody.getNow().getSd());
        hashMapList.add(hashMap);

        hashMap = new HashMap<>();
        //风
        hashMap.put(FIRST_TITLE, "风");
        hashMap.put(FIRST_VALUE, showapiResBody.getNow().getWindDirection() + f1.getDayWindDirection());
        //体感温度
        hashMap.put(SECOND_TITLE, "体感温度");
        hashMap.put(SECOND_VALUE, showapiResBody.getNow().getTemperature());
        hashMapList.add(hashMap);

        hashMap = new HashMap<>();
        //降水量
        hashMap.put(FIRST_TITLE, "降水量");
        hashMap.put(FIRST_VALUE, showapiResBody.getNow().getRain());
        //气压
        hashMap.put(SECOND_TITLE, "气压");
        hashMap.put(SECOND_VALUE, f1.getAirPress());
        hashMapList.add(hashMap);

        hashMap = new HashMap<>();
        //能见度
        hashMap.put(FIRST_TITLE, "能见度");
        hashMap.put(FIRST_VALUE, "8.1公里");
        //紫外线指数
        hashMap.put(SECOND_TITLE, "紫外线指数");
        hashMap.put(SECOND_VALUE, f1.getZiwaixian());
        hashMapList.add(hashMap);
    }

    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType: " + position);
        if (position < 6) {
            return WEATHER_ITEM_VIEW;
        } else if (position == 6) {
            return WEATHER_TODAY_VIEW;
        } else if (position > 6 && position < 12) {
            return WEATHER_DETAIL_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        if (viewType == WEATHER_ITEM_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_week_weather_item, parent, false);
            WeatherItemViewHolder viewHolder = new WeatherItemViewHolder(view);
            return viewHolder;
        } else if (viewType == WEATHER_TODAY_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_today_item, parent, false);
            WeatherTodayViewHolder viewHolder = new WeatherTodayViewHolder(view);
            return viewHolder;
        } else if (viewType == WEATHER_DETAIL_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_detail_item, parent, false);
            WeatherDetailViewHolder viewHolder = new WeatherDetailViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        //根据holder的类型不同，显示不同的数据
        if (holder instanceof WeatherItemViewHolder) {
            ((WeatherItemViewHolder) holder).tv_week.setText(String.format(mContext.getResources().getString(R.string.week), Util.getWeek(fList.get(position).getWeekday())));
            Glide.with(mContext)
                    .load(fList.get(position).getDayWeatherPic())
                    .centerCrop()
                    .placeholder(R.mipmap.weather_default)
                    .into(((WeatherItemViewHolder) holder).iv_weather_icon);
            ((WeatherItemViewHolder) holder).tv_temperature.setText(String.format(mContext.getResources().getString(R.string.high_low_temperature), fList.get(position).getDayAirTemperature(), fList.get(position).getNightAirTemperature()));
        } else if (holder instanceof WeatherTodayViewHolder) {
            ((WeatherTodayViewHolder) holder).tv_weather_today.setText(hashMapList.get(0).get(FIRST_VALUE));
        } else if (holder instanceof WeatherDetailViewHolder) {
            ((WeatherDetailViewHolder) holder).tv_first_title.setText(hashMapList.get(position-fList.size()).get(FIRST_TITLE));
            ((WeatherDetailViewHolder) holder).tv_first_value.setText(hashMapList.get(position-fList.size()).get(FIRST_VALUE));
            ((WeatherDetailViewHolder) holder).tv_second_title.setText(hashMapList.get(position-fList.size()).get(SECOND_TITLE));
            ((WeatherDetailViewHolder) holder).tv_second_value.setText(hashMapList.get(position-fList.size()).get(SECOND_VALUE));
        }
    }

    @Override
    public int getItemCount() {
        return fList.size() +hashMapList.size();
    }

    class WeatherItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_week)
        TextView tv_week;
        @BindView(R.id.tv_temperature)
        TextView tv_temperature;
        @BindView(R.id.iv_weather_icon)
        ImageView iv_weather_icon;

        public WeatherItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class WeatherTodayViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_weather_today)
        TextView tv_weather_today;

        public WeatherTodayViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class WeatherDetailViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_first_title)
        TextView tv_first_title;
        @BindView(R.id.tv_first_value)
        TextView tv_first_value;
        @BindView(R.id.tv_second_title)
        TextView tv_second_title;
        @BindView(R.id.tv_second_value)
        TextView tv_second_value;

        public WeatherDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
