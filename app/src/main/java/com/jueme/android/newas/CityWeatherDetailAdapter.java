package com.jueme.android.newas;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jueme on 2020/12/16
 **/
public class CityWeatherDetailAdapter extends FragmentStateAdapter {
    private static final String TAG = "CWDAdapter";
    ArrayList<Fragment> fragmentArrayList;

  /*  private val ids: ArrayList<Long>
    get() = arrayListOf(fid1, fid2, fid3, fid4)

    private val createdIds = hashSetOf<Long>()*/

    ArrayList<Long> ids = new ArrayList<>();

    private List<Integer> mFragmentHashCodes = new ArrayList<>();

    HashSet<Integer> longHashSet = new HashSet<>();

    public CityWeatherDetailAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragmentArrayList = new ArrayList<>();
        ids.add((long) 111);
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
      /*  if(!mFragmentHashCodes.contains(fragmentArrayList.get(position).hashCode())){
            mFragmentHashCodes.add(position,fragmentArrayList.get(position).hashCode());
        }
        Log.d(TAG, "createFragment: "+mFragmentHashCodes.size());*/
        longHashSet.add(fragmentArrayList.get(position).hashCode());
        Log.d(TAG, "createFragment: "+longHashSet.size()+" hashCode "+fragmentArrayList.get(position).hashCode());
        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        //Log.d(TAG, "getItemCount: "+fragmentArrayList.size());
        return fragmentArrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return fragmentArrayList.get(position).hashCode();
    }

    @Override
    public boolean containsItem(long itemId) {
        //Log.d(TAG, "containsItem: "+itemId+" contains test "+longHashSet.contains(fragmentArrayList.get(0).hashCode()));
        return longHashSet.contains(itemId);
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

    public void removeAllFragment(FragmentManager fm){
     /*   FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        if(fragments != null && fragments.size() >0){
            for (int i = 0; i < fragments.size(); i++) {
                ft.remove(fragments.get(i));
            }
        }
        ft.commit();*/
        fragmentArrayList.clear();
        notifyDataSetChanged();
    }
}
