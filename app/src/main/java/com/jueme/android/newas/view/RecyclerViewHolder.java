package com.jueme.android.newas.view;


import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView ViewHolder基类
 * <p>
 * 使用 {@link #mViews} 对ItemView的子view进行存储，同时使用 {@link #getView(int)} 方法进行ItemView
 * 中的子View的获取。获取方式是：如果mViews中存在则直接使用，不存在则从ItemView中find。
 * <p>
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;

    RecyclerViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
    }

    /**
     * 获取需要的View，如果已经存在引用则直接获取，如果不存在则重新加载并保存
     *
     * @param viewId id
     * @return 需要的View
     */
    public View getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }
}
