package com.jueme.android.newas;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jueme.android.newas.view.BaseRecyclerViewAdapter;
import com.jueme.android.newas.view.CityBean;
import com.jueme.android.newas.view.RecyclerViewHolder;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jueme on 2020/12/18
 **/
public class CityListAdapter extends BaseRecyclerViewAdapter<CityBean> {

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position) throws SQLException;
    }

    private OnDeleteClickLister mDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public CityListAdapter(Context context, ArrayList<CityBean> data) {
        super(context, data, R.layout.city_item);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, CityBean bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        try {
                            mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        ((TextView) holder.getView(R.id.city_name)).setText(bean.getCityName());
        ((TextView) holder.getView(R.id.temperature)).setText(bean.getTemperature());

        Log.d("InventoryAdapter", "onBindData: city_name " + bean.getCityName() + " getTemperature " + bean.getTemperature());
    }
}
