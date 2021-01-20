package com.jueme.android.newas;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.network.Network;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListActivity extends AppCompatActivity implements Network.DataStatusListener {
    private static final String TAG = "CityListActivity";
    @BindView(R.id.city_recyclerView)
    RecyclerView city_recyclerView;

    private CityListAdapter2 cityListAdapter;

    private List<ShowapiResBody> bodies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        ButterKnife.bind(this);

        Network.setDataStatusListener(this);
        bodies = Util.readJsonData(getApplicationContext());
        if (bodies == null) {
            bodies = new ArrayList<>();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        city_recyclerView.setLayoutManager(layoutManager);
        cityListAdapter = new CityListAdapter2(getApplicationContext(), bodies);

        cityListAdapter.setOnItemClickListener(new CityListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int type, int position) {
                Log.d(TAG, "onItemClick: position " + position + " type " + type);
                if (type == 1) {
                    Intent intent = new Intent(CityListActivity.this, MainActivity.class);
                    intent.putExtra("currentItem", position);
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (type == 2) {
                    Intent intent = new Intent(CityListActivity.this, CityListSelectActivity.class);
                    startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
                }
            }
        });

        View mFooterView = LayoutInflater.from(this).inflate(R.layout.city_item_footer, city_recyclerView, false);

        cityListAdapter.addFootView(mFooterView);
        city_recyclerView.setAdapter(cityListAdapter);

        //创建item helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //绑定到recyclerView上面
        itemTouchHelper.attachToRecyclerView(city_recyclerView);

    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d("SimpleCallback", "getMovementFlags: ");
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            //只允许向左滑动
            final int swipeFlags = ItemTouchHelper.LEFT;//ItemTouchHelper.LEFT

            if (viewHolder instanceof CityListAdapter2.FootViewHolder) {
                return 0;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Log.d("SimpleCallback", "onMove: ");
            if (target instanceof CityListAdapter2.FootViewHolder) {
                return false;
            } else {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(bodies, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(bodies, i, i - 1);
                    }
                }
                cityListAdapter.notifyItemMoved(fromPosition, toPosition);
                Util.saveJsonData(getApplicationContext(), bodies);
                return true;
            }
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //左右拖动的回调
            bodies.remove(viewHolder.getAdapterPosition());
            cityListAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            cityListAdapter.notifyDataSetChanged();
            Util.saveJsonData(getApplicationContext(), bodies);
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            Log.d("SimpleCallback", "isItemViewSwipeEnabled: ");
            return true;
        }

        @Override
        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
            //需要手动重写的方法,返回true
            //当前item可以被拖动到目标位置后,直接落到target上,后面的item也接着落
            Log.d("SimpleCallback", "canDropOver: ");
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            //是否开启长按拖动
            //返回true  可以实现长按拖动排序和拖动动画
            Log.d("SimpleCallback", "isLongPressDragEnabled: ");
            return true;
        }

        /**
         * 长按选中Item的时候开始调用
         *
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            Log.d("SimpleCallback", "onSelectedChanged: ");
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 手指松开的时候还原
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            Log.d("SimpleCallback", "clearView: ");
            viewHolder.itemView.setBackgroundColor(0);
            //更新数据，要不然position不会更新
            if (!recyclerView.isComputingLayout()) {
                cityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            Log.d("SimpleCallback", "onChildDraw: ");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();

                CityInfoBean cityInfoBean = (CityInfoBean) bundle.getParcelable("cityinfo");

                if (null == cityInfoBean) {
                    return;
                }
                for (int i = 0; i < bodies.size(); i++) {
                    if(cityInfoBean.getName().contains(bodies.get(i).getCityinfo().getC3())){
                        Toast.makeText(CityListActivity.this,"已经添加了该城市",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Network.getWeatherData(cityInfoBean.getName());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(CityListActivity.this, MainActivity.class);
            intent.putExtra("currentItem", 0);
            setResult(RESULT_OK, intent);
            finish();
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: ");
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override
    public void getDataSuccess(ShowapiResBody showapiResBody) {
        bodies.add(showapiResBody);
        Util.saveJsonData(getApplicationContext(), bodies);
        cityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailed() {

    }

    @Override
    public void next() {

    }
}