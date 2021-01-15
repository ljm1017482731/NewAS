package com.jueme.android.newas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.view.CityBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jueme on 2021/1/6
 **/
public class CityListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final String TAG = "CityListAdapter2";

    private OnItemClickListener mListener;

    private List<ShowapiResBody> showapiResBodies;

    private View mFootView;

    private Context mContext;

    final private int ITEM_VIEW = 1;
    final private int FOOT_VIEW = 2;

    public CityListAdapter2(Context context, List<ShowapiResBody> showapiResBodies) {
        mContext = context;
        this.showapiResBodies = showapiResBodies;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mListener = onItemClickListener;
    }

    public void addFootView(View footView) {
        mFootView = footView;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(this, v, (int) v.getTag());
        }
    }

    /**
     * item点击监听器
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param adapter  The Adapter where the click happened.
         * @param v        The view that was clicked.
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View v, int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == showapiResBodies.size()) {
            return FOOT_VIEW;
        } else {
            return ITEM_VIEW;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == FOOT_VIEW) {
            //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_footer, parent, false);
            //FootViewHolder viewHolder = new FootViewHolder(view);
            FootViewHolder viewHolder = new FootViewHolder(mFootView);
            return viewHolder;
        } else if (viewType == ITEM_VIEW) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
            RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
            return viewHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FootViewHolder) {

        } else if (holder instanceof RecyclerViewHolder) {
            Log.d(TAG, "onBindViewHolder: " + showapiResBodies.get(position).getCityinfo().getC3());
            ((RecyclerViewHolder) holder).city_name.setText(showapiResBodies.get(position).getCityinfo().getC3());
            ((RecyclerViewHolder) holder).temperature.setText(showapiResBodies.get(position).getNow().getTemperature());
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        if (mFootView != null) {
            return showapiResBodies.size() + 1;
        } else {
            return showapiResBodies.size();
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.city_name)
        TextView city_name;
        @BindView(R.id.temperature)
        TextView temperature;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(CityListAdapter2.this);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(CityListAdapter2.this);
        }
    }
}
