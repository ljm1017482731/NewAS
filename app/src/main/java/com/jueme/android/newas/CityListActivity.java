package com.jueme.android.newas;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.jueme.android.newas.bean.ShowapiResBody;
import com.jueme.android.newas.database.City;
import com.jueme.android.newas.database.CityDao;
import com.jueme.android.newas.database.CityDatabase;
import com.jueme.android.newas.network.Network;
import com.jueme.android.newas.view.CityBean;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListActivity extends AppCompatActivity implements Network.DataStatusListener {
    private static final String TAG = "CityListActivity";
    @BindView(R.id.city_recyclerView)
    RecyclerView city_recyclerView;

    private CityListAdapter2 cityListAdapter;

    private ArrayList<CityBean> list;

    private FragmentManager fm;

    String[] cityNames = new String[]{"惠州", "广州", "拉萨", "吉林", "上海", "乌鲁木齐", "黑龙江", "北京", "呼和浩特", "兰州"};
    private CityDatabase cityDatabase;
    private CityDao cityDao;
    private RecyclerView.ViewHolder mViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_choose);
        ButterKnife.bind(this);

        Network.setDataStatusListener(this);

        fm = getSupportFragmentManager();

        list = (ArrayList<CityBean>) getIntent().getSerializableExtra("cityNames");
        //list = new ArrayList<>();


      /*  CityBean cityBean = new CityBean("深圳","23");
        list.add(cityBean);
        cityBean = new CityBean("北京","-3");
        list.add(cityBean);
        cityBean = new CityBean("北京","-3");
        list.add(cityBean);
        list.add(cityBean);
        cityBean = new CityBean("北京","-3");
        list.add(cityBean);*/

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        city_recyclerView.setLayoutManager(layoutManager);
        //cityListAdapter = new CityListAdapter(getApplicationContext(),list);
        cityListAdapter = new CityListAdapter2(getApplicationContext(), list);

        cityListAdapter.setOnItemClickListener(new CityListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View v, int position) {
                Log.d(TAG, "onItemClick: " + position+" list "+list.size());
                if (position == list.size()) {
                    Intent intent = new Intent(CityListActivity.this, CityListSelectActivity.class);
                    startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
                } else {
                    Intent intent = new Intent(CityListActivity.this, MainActivity.class);
                    intent.putExtra("currentItem", position);
                    intent.putExtra("cityNames", list);
                    startActivity(intent);
                    finish();
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

        cityDatabase = Room.databaseBuilder(this, CityDatabase.class, "city_database").allowMainThreadQueries().build();
        cityDao = cityDatabase.getCityDao();

    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            Log.d("SimpleCallback", "getMovementFlags: ");
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.LEFT;//0
            mViewHolder = viewHolder;

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
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                //将数据库中的两个数据
                //cityDao.updateByPosition(cityDao.getCityByPosition(fromPosition).getId(),toPosition);
                //cityDao.updateByPosition(cityDao.getCityByPosition(toPosition).getId(),fromPosition);
                String fromCityName = list.get(fromPosition).getCityName();
                int cityFrom =cityDao.getCityByName(fromCityName).getPosition();

                String toCityName = list.get(toPosition).getCityName();
                int cityTo =cityDao.getCityByName(toCityName).getPosition();

                Log.d(TAG, "updateByName: fromCityName "+fromCityName+" cityFrom "+cityFrom);
                cityDao.updateByName(fromCityName,cityTo);

                Log.d(TAG, "updateByName: toCityName "+toCityName+" cityTo "+cityTo);
                cityDao.updateByName(toCityName,cityFrom);

                cityListAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //暂不处理  这个主要是做左右拖动的回调
            Log.d("SimpleCallback", "onSwiped: " + viewHolder.getAdapterPosition()+" getCityName "+list.get(viewHolder.getAdapterPosition()).getCityName());
            cityDao.deleteCityByName(list.get(viewHolder.getAdapterPosition()).getCityName());
            list.remove(viewHolder.getAdapterPosition());
            cityListAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            cityListAdapter.notifyDataSetChanged();
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
            if(!recyclerView.isComputingLayout()){
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

                //mResultTv.setText("城市： " + cityInfoBean.getName()+"  "+cityInfoBean.getId());
                Network.getWeatherData(cityInfoBean.getName());
                Log.d(TAG, "onActivityResult: "+cityDao.getCityCount());
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
            Log.d(TAG, "onKeyDown: ");
            Intent intent = new Intent();
            intent.putExtra("test", 100);
            setResult(1, intent);
            finish();
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(TAG, "finish: ");
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override
    public void getDataSuccess(ShowapiResBody showapiResBody) {
        CityBean cityBean = new CityBean(showapiResBody.getCityinfo().getC3(), showapiResBody.getNow().getTemperature());
        list.add(cityBean);
        //请求成功后保存数据
        City city = new City(cityBean.getCityName(),cityDao.getMaxNum()+1);
        cityDao.insertCitys(city);

        cityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getDataFailed() {

    }

    @Override
    public void next() {

    }
}