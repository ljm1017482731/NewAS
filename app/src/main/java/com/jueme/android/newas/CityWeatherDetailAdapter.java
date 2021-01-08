package com.jueme.android.newas;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

/**
 * Created by Jueme on 2020/12/16
 **/
public class CityWeatherDetailAdapter extends FragmentStateAdapter {
    private static final String TAG = "DemoFragmentAdapter";
    ArrayList<Fragment> fragmentArrayList;
    public CityWeatherDetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentArrayList = new ArrayList<>();
    }

    public CityWeatherDetailAdapter(@NonNull Fragment fragment) {
        super(fragment);
        fragmentArrayList = new ArrayList<>();
    }

    public CityWeatherDetailAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragmentArrayList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(TAG, "createFragment: ");
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+fragmentArrayList.size());
        return fragmentArrayList.size();
    }

    public void addFragment(CityWeatherDetailFragment fragment){
        fragmentArrayList.add(fragment);
        notifyDataSetChanged();
    }

    public void removeFragment(){
        if(fragmentArrayList.size()>0){
            fragmentArrayList.remove(fragmentArrayList.size()-1);
            notifyDataSetChanged();
        }
    }

    public void removeAllFragment(){
        fragmentArrayList.clear();
        notifyDataSetChanged();
    }
}
