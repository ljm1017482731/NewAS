package com.jueme.android.newas

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by Jueme on 2021/01/15
 **/
class MainAdapter(val activity: MainActivity) : FragmentStateAdapter(activity) {

    private val fid1 = 111.toLong()
    private val fid2 = 222.toLong()
    private val fid3 = 333.toLong()
    private val fid4 = 444.toLong()

    private val ids: ArrayList<Long>
        get() = arrayListOf(fid1, fid2, fid3, fid4)

    private val createdIds = hashSetOf<Long>()

    override fun getItemCount(): Int {
        return  4
    }

    override fun getItemId(position: Int): Long {
        return ids[position]
    }

    override fun containsItem(itemId: Long): Boolean {
        return createdIds.contains(itemId)
    }

    override fun createFragment(position: Int): Fragment {
        val id = ids[position]
        createdIds.add(id)
        return when (id) {
            fid4 -> CityWeatherDetailFragment(null)
            fid3 -> CityWeatherDetailFragment(null)
            fid2 -> CityWeatherDetailFragment(null)
            else -> CityWeatherDetailFragment(null)
        }
    }
}