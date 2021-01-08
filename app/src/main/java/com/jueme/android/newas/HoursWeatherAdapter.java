package com.jueme.android.newas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jueme.android.newas.bean.HourDataList;

import java.util.List;

/**
 * Created by Jueme on 2020/12/21
 **/
public class HoursWeatherAdapter extends RecyclerView.Adapter<HoursWeatherAdapter.RecyclerViewHolder> {
    private static final String TAG = "HoursWeatherAdapter";

    private List<HourDataList> hourDataLists;

    private Context mContext;

    public HoursWeatherAdapter(Context context,List<HourDataList> hourDataLists) {
        mContext = context;
        this.hourDataLists = hourDataLists;
    }

    public List<HourDataList> getHourDataLists() {
        return hourDataLists;
    }

    public void setHourDataLists(List<HourDataList> hourDataLists) {
        this.hourDataLists = hourDataLists;
    }


    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_weather_item,parent,false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.tv_time.setText(hourDataLists.get(position).getTemperature_time());
        Glide.with(mContext)
                .load(hourDataLists.get(position).getWeather_pic())
                .centerCrop()
                .placeholder(R.mipmap.weather_default)
                .into(holder.iv_weather_icon);

        holder.tv_temperature.setText(hourDataLists.get(position).getTemperature());
    }

    @Override
    public int getItemCount() {
        return hourDataLists.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView tv_time;
        TextView tv_temperature;
        ImageView iv_weather_icon;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
             tv_time = itemView.findViewById(R.id.tv_time);
             tv_temperature = itemView.findViewById(R.id.tv_temperature);
             iv_weather_icon = itemView.findViewById(R.id.iv_weather_icon);
        }
    }
}
