package com.jueme.android.newas;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jueme on 2020/12/19
 **/
public class SlideRecyclerView extends RecyclerView {

    private static final String TAG = "SlideRecyclerView";

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private static final int TYPE_HEADER = -101;
    private static final int TYPE_FOOTER = -102;
    private static final int TYPE_LIST_ITEM = -103;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrapAdapter;

    private static final int INVALID_POSITION = -1; // 触摸到的点不在子View范围内
    private static final int INVALID_CHILD_WIDTH = -1;  // 子ItemView不含两个子View
    private static final int SNAP_VELOCITY = 600;   // 最小滑动速度

    private VelocityTracker mVelocityTracker;   // 速度追踪器
    private int mTouchSlop; // 认为是滑动的最小距离（一般由系统提供）
    private Rect mTouchFrame;   // 子View所在的矩形范围
    private Scroller mScroller;
    private float mLastX;   // 滑动过程中记录上次触碰点X
    private float mFirstX, mFirstY; // 首次触碰范围
    private boolean mIsSlide;   // 是否滑动子View
    private ViewGroup mFlingView;   // 触碰的子View
    private int mPosition;  // 触碰的view的位置
    private int mMenuViewWidth;    // 菜单按钮宽度

    public SlideRecyclerView(Context context) {
        this(context, null);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
    }

    public void addHeaderView(View view) {
        mHeaderViews.clear();
        mHeaderViews.add(view);
    }

    public void addFooterView(View view) {
        mFooterViews.clear();
        mFooterViews.add(view);
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFooterViews, adapter);
        super.setAdapter(mWrapAdapter);
        mAdapter.registerAdapterDataObserver(mDataObserver);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        obtainVelocity(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {  // 如果动画还没停止，则立即终止动画
                    mScroller.abortAnimation();
                }
                mFirstX = mLastX = x;
                mFirstY = y;
                mPosition = pointToPosition(x, y);  // 获取触碰点所在的position
                if (mPosition != INVALID_POSITION) {
                    View view = mFlingView;
                    // 获取触碰点所在的view
                    mFlingView = (ViewGroup) getChildAt(mPosition - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition());
                    // 这里判断一下如果之前触碰的view已经打开，而当前碰到的view不是那个view则立即关闭之前的view，此处并不需要担动画没完成冲突，因为之前已经abortAnimation
                    if (view != null && mFlingView != view && view.getScrollX() != 0) {
                        view.scrollTo(0, 0);
                    }
                    // 这里进行了强制的要求，RecyclerView的子ViewGroup必须要有2个子view,这样菜单按钮才会有值，
                    // 需要注意的是:如果不定制RecyclerView的子View，则要求子View必须要有固定的width。
                    // 比如使用LinearLayout作为根布局，而content部分width已经是match_parent，此时如果菜单view用的是wrap_content，menu的宽度就会为0。
                    if (mFlingView.getChildCount() == 2) {
                        mMenuViewWidth = mFlingView.getChildAt(1).getWidth();
                    } else {
                        mMenuViewWidth = INVALID_CHILD_WIDTH;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.computeCurrentVelocity(1000);
                // 此处有俩判断，满足其一则认为是侧滑：
                // 1.如果x方向速度大于y方向速度，且大于最小速度限制；
                // 2.如果x方向的侧滑距离大于y方向滑动距离，且x方向达到最小滑动距离；
                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();
                if (Math.abs(xVelocity) > SNAP_VELOCITY && Math.abs(xVelocity) > Math.abs(yVelocity)
                        || Math.abs(x - mFirstX) >= mTouchSlop
                        && Math.abs(x - mFirstX) > Math.abs(y - mFirstY)) {
                    mIsSlide = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseVelocity();
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mIsSlide && mPosition != INVALID_POSITION) {
            float x = e.getX();
            obtainVelocity(e);
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:   // 因为没有拦截，所以不会被调用到
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 随手指滑动
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        float dx = mLastX - x;
                        if (mFlingView.getScrollX() + dx <= mMenuViewWidth
                                && mFlingView.getScrollX() + dx > 0) {
                            mFlingView.scrollBy((int) dx, 0);
                        }
                        mLastX = x;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (mMenuViewWidth != INVALID_CHILD_WIDTH) {
                        int scrollX = mFlingView.getScrollX();
                        mVelocityTracker.computeCurrentVelocity(1000);
                        // 此处有两个原因决定是否打开菜单：
                        // 1.菜单被拉出宽度大于菜单宽度一半；
                        // 2.横向滑动速度大于最小滑动速度；
                        // 注意：之所以要小于负值，是因为向左滑则速度为负值
                        if (mVelocityTracker.getXVelocity() < -SNAP_VELOCITY) {    // 向左侧滑达到侧滑最低速度，则打开
                            mScroller.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
                        } else if (mVelocityTracker.getXVelocity() >= SNAP_VELOCITY) {  // 向右侧滑达到侧滑最低速度，则关闭
                            mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        } else if (scrollX >= mMenuViewWidth / 2) { // 如果超过删除按钮一半，则打开
                            mScroller.startScroll(scrollX, 0, mMenuViewWidth - scrollX, 0, Math.abs(mMenuViewWidth - scrollX));
                        } else {    // 其他情况则关闭
                            mScroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        }
                        invalidate();
                    }
                    mMenuViewWidth = INVALID_CHILD_WIDTH;
                    mIsSlide = false;
                    mPosition = INVALID_POSITION;
                    releaseVelocity();  // 这里之所以会调用，是因为如果前面拦截了，就不会执行ACTION_UP,需要在这里释放追踪
                    break;
            }
            return true;
        } else {
            // 此处防止RecyclerView正常滑动时，还有菜单未关闭
            closeMenu();
            // Velocity，这里的释放是防止RecyclerView正常拦截了，但是在onTouchEvent中却没有被释放；
            // 有三种情况：1.onInterceptTouchEvent并未拦截，在onInterceptTouchEvent方法中，DOWN和UP一对获取和释放；
            // 2.onInterceptTouchEvent拦截，DOWN获取，但事件不是被侧滑处理，需要在这里进行释放；
            // 3.onInterceptTouchEvent拦截，DOWN获取，事件被侧滑处理，则在onTouchEvent的UP中释放。
            releaseVelocity();
        }
        return super.onTouchEvent(e);
    }

    private void releaseVelocity() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void obtainVelocity(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    public int pointToPosition(int x, int y) {
        int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Rect frame = mTouchFrame;
        if (frame == null) {
            mTouchFrame = new Rect();
            frame = mTouchFrame;
        }

        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return firstPosition + i;
                }
            }
        }
        return INVALID_POSITION;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mFlingView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 将显示子菜单的子view关闭
     * 这里本身是要自己来实现的，但是由于不定制item，因此不好监听器点击事件，因此需要调用者手动的关闭
     */
    public void closeMenu() {
        if (mFlingView != null && mFlingView.getScrollX() != 0) {
            mFlingView.scrollTo(0, 0);
        }
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }
    };

    private class WrapAdapter extends RecyclerView.Adapter<ViewHolder> {

        private Adapter mAdapter;
        private List<View> mHeaderViews;
        private List<View> mFooterViews;

        public WrapAdapter(List<View> headerViews, List<View> footerViews, Adapter adapter) {
            this.mAdapter = adapter;
            this.mHeaderViews = headerViews;
            this.mFooterViews = footerViews;
        }

        public int getHeaderCount() {
            return this.mHeaderViews.size();
        }

        public int getFooterCount() {
            return this.mFooterViews.size();
        }

        public boolean isHeader(int position) {
            return position >= 0 && position < this.mHeaderViews.size();
        }

        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - this.mFooterViews.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new CustomViewHolder(this.mHeaderViews.get(0));
            } else if (viewType == TYPE_FOOTER) {
                return new CustomViewHolder(this.mFooterViews.get(0));
            } else {
                return this.mAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (isHeader(position)) return;
            if (isFooter(position)) return;
            int rePosition = position - getHeaderCount();
            int itemCount = this.mAdapter.getItemCount();
            if (this.mAdapter != null) {
                if (rePosition < itemCount) {
                    Log.v("czm", "rePosition/itemCount=" + rePosition + "/" + itemCount);
                    this.mAdapter.onBindViewHolder(holder, rePosition);
                    return;
                }
            }
        }

        @Override
        public long getItemId(int position) {
            if (this.mAdapter != null && position >= getHeaderCount()) {
                int rePosition = position - getHeaderCount();
                int itemCount = this.mAdapter.getItemCount();
                if (rePosition < itemCount) {
                    return this.mAdapter.getItemId(rePosition);
                }
            }
            return -1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isHeader(position)) {
                return TYPE_HEADER;
            }
            if (isFooter(position)) {
                return TYPE_FOOTER;
            }
            int rePosition = position - getHeaderCount();
            int itemCount = this.mAdapter.getItemCount();
            if (rePosition < itemCount) {
                return this.mAdapter.getItemViewType(position - getHeaderCount());
            }
            return TYPE_LIST_ITEM;
        }

        @Override
        public int getItemCount() {
            if (this.mAdapter != null) {
                return getHeaderCount() + getFooterCount() + this.mAdapter.getItemCount();
            } else {
                return getHeaderCount() + getFooterCount();
            }
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (this.mAdapter != null) {
                this.mAdapter.registerAdapterDataObserver(observer);
            }
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (this.mAdapter != null) {
                this.mAdapter.unregisterAdapterDataObserver(observer);
            }
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            if (holder.getItemViewType() == TYPE_HEADER) {
                super.onViewDetachedFromWindow(holder);
            } else if (holder.getItemViewType() == TYPE_FOOTER) {
                super.onViewDetachedFromWindow(holder);
            } else {
                this.mAdapter.onViewDetachedFromWindow(holder);
            }
        }

        private class CustomViewHolder extends ViewHolder {

            public CustomViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
