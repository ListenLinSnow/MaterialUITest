package com.example.lc.materialuitest.view.customTable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.lc.materialuitest.util.ProgressStyle;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerView extends RecyclerView {

    private boolean isLoadingData = false;
    private boolean isNoMore = false;
    private int refreshProgressStyle = ProgressStyle.SysProgress;
    private int loadingMoreProgressStyle = ProgressStyle.SysProgress;
    private ArrayList<View> headerViewList = new ArrayList<>();
    private WrapAdapter wrapAdapter;
    private float lastY = -1;
    private static final float DRAG_RATE = 3;
    private LoadingListener loadingListener;
    private ArrowRefreshHeader refreshHeader;
    private boolean pullRefreshEnabled = true;
    private boolean loadingMoreEnabled = true;
    //下面的itemViewType是保留值(ReservedItemViewType),如果用户的adapter与它们重复将会强制抛出异常。
    //为了简化,检测到重复时对用户的提示为itemViewType必须小于10000
    private static final int TYPE_REFRESH_HEADER = 10000;
    private static final int TYPE_FOOTER = 10001;
    private static final int HEADER_INIT_INDEX = 10002;
    //每个header必须有不同的type,不然滚动的时候会有顺序变化
    private static List<Integer> headerTypeList = new ArrayList<>();
    private int pageCount = 0;
    //adapter没有数据时候的显示,类似于listView的emptyView
    private View emptyView;
    private View footView;
    private final AdapterDataObserver dataObserver = new DataObserver();
    private AppBarStateChangeListener.State appbarState = AppBarStateChangeListener.State.EXPANDED;

    public XRecyclerView(@NonNull Context context) {
        super(context);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        if (pullRefreshEnabled){
            refreshHeader = new ArrowRefreshHeader(getContext());
            refreshHeader.setProgressStyle(refreshProgressStyle);
        }
        LoadingMoreFooter footer = new LoadingMoreFooter(getContext());
        footer.setProgressStyle(loadingMoreProgressStyle);
        footView = footer;
        footView.setVisibility(GONE);
    }

    public void setFootViewText(String loading, String noMore){
        if (footView instanceof LoadingMoreFooter){
            ((LoadingMoreFooter) footView).setLoadingHint(loading);
            ((LoadingMoreFooter) footView).setNoMoreHint(noMore);
        }
    }

    public void addHeaderView(View view){
        headerTypeList.add(HEADER_INIT_INDEX + headerViewList.size());
        headerViewList.add(view);
        if (wrapAdapter != null){
            wrapAdapter.notifyDataSetChanged();
        }
    }

    public void loadMoreComplete(){
        isLoadingData = false;
        if (footView instanceof LoadingMoreFooter){
            ((LoadingMoreFooter) footView).setState(LoadingMoreFooter.STATE_COMPLETE);
        } else {
            footView.setVisibility(GONE);
        }
    }

    public void setNoMore(boolean noMore){
        isLoadingData = false;
        isNoMore = noMore;
        if (footView instanceof LoadingMoreFooter){
            ((LoadingMoreFooter) footView).setState(isNoMore ? LoadingMoreFooter.STATE_NO_MORE : LoadingMoreFooter.STATE_COMPLETE);
        } else {
            footView.setVisibility(GONE);
        }
    }

    public void refresh(){
        if (pullRefreshEnabled && loadingListener != null){
            refreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
            loadingListener.onRefresh();
        }
    }

    public void reset(){
        setNoMore(false);
        loadMoreComplete();
        refreshComplete();
    }

    public void refreshComplete() {
        refreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void setRefreshHeader(ArrowRefreshHeader refreshHeader) {
        this.refreshHeader = refreshHeader;
    }

    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        this.pullRefreshEnabled = pullRefreshEnabled;
    }

    public void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        this.loadingMoreEnabled = loadingMoreEnabled;
        if (!loadingMoreEnabled){
            if (footView instanceof LoadingMoreFooter){
                ((LoadingMoreFooter) footView).setState(LoadingMoreFooter.STATE_COMPLETE);
            }
        }
    }

    public void setRefreshProgressStyle(int refreshProgressStyle) {
        this.refreshProgressStyle = refreshProgressStyle;
    }

    public void setLoadingMoreProgressStyle(int loadingMoreProgressStyle) {
        this.loadingMoreProgressStyle = loadingMoreProgressStyle;
    }

    public void setArrowImageView(int resId){
        if (refreshHeader != null){
            refreshHeader.setArrowImageView(resId);
        }
    }

    public void setEmptyView(View emptyView){
        this.emptyView = emptyView;
        dataObserver.onChanged();
    }

    public View getEmptyView() {
        return emptyView;
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        wrapAdapter = new WrapAdapter(adapter);
        super.setAdapter(wrapAdapter);
        adapter.registerAdapterDataObserver(dataObserver);
        dataObserver.onChanged();
    }

    @Nullable
    @Override
    public Adapter getAdapter() {
        if (wrapAdapter != null){
            return wrapAdapter.getOriginalAdapter();
        } else {
            return null;
        }
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        if (wrapAdapter != null){
            if (layout instanceof GridLayoutManager){
                final GridLayoutManager gridLayoutManager = ((GridLayoutManager) layout);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (wrapAdapter.isHeader(position) || wrapAdapter.isFooter(position) || wrapAdapter.isRefreshHeader(position))
                                ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        if (screenState == RecyclerView.SCROLL_STATE_IDLE && loadingListener != null && !isLoadingData && loadingMoreEnabled){
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager){
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager){
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1
                    && !isNoMore
                    && refreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING
                    //只有当更多底部视图显示在页面上才开始进行加载更多数据
                    && lastVisibleItemPosition >= 2) {
                isLoadingData = true;
                if (footView instanceof LoadingMoreFooter){
                    ((LoadingMoreFooter) footView).setState(LoadingMoreFooter.STATE_LOADING);
                } else {
                    footView.setVisibility(VISIBLE);
                }
                loadingListener.onLoadMore();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (lastY == -1){
            lastY = e.getRawY();
        }
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastY = e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = e.getRawY() - lastY;
                lastY = e.getRawY();
                if (isOnTop() && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    refreshHeader.onMove(deltaY / DRAG_RATE);
                    if (refreshHeader.getVisibleHeight() > 0 && refreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING){
                        return false;
                    }
                }
                break;
            default:
                lastY = -1;
                if (isOnTop() && pullRefreshEnabled && appbarState == AppBarStateChangeListener.State.EXPANDED) {
                    if (refreshHeader.releaseAction()) {
                        if (loadingListener != null){
                            loadingListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private int findMax(int[] lastPositions){
        int max = lastPositions[0];
        for (int value : lastPositions){
            if (value > max){
                max = value;
            }
        }
        return max;
    }

    private boolean isOnTop(){
        if (refreshHeader.getParent() != null){
            return true;
        } else {
            return false;
        }
    }

    private class WrapAdapter extends Adapter<ViewHolder> {

        private Adapter adapter;

        public WrapAdapter(Adapter adapter) {
            this.adapter = adapter;
        }

        public Adapter getOriginalAdapter(){
            return this.adapter;
        }

        public boolean isHeader(int position){
            return position >= 1 && position < headerViewList.size() + 1;
        }

        public boolean isFooter(int position){
            if (loadingMoreEnabled){
                return position == getItemCount() - 1;
            } else {
                return false;
            }
        }

        public boolean isRefreshHeader(int position){
            return position == 0;
        }

        public int getHeaderListCount(){
            return headerViewList.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            if (viewType == TYPE_REFRESH_HEADER) {
                return new SimpleViewHolder(refreshHeader);
            } else if (isHeaderType(viewType)) {
                return new SimpleViewHolder(getHeaderViewByType(viewType));
            } else if (viewType == TYPE_FOOTER){
                return new SimpleViewHolder(footView);
            }
            return adapter.onCreateViewHolder(viewGroup, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            if (isHeader(position) || isRefreshHeader(position)){
                return;
            }
            int adjPosition = position - (getHeaderListCount() + 1);
            int adapterCount;
            if (adapter != null){
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount){
                    adapter.onBindViewHolder(viewHolder, adjPosition);
                }
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
            if (isHeader(position) || isRefreshHeader(position)){
                return;
            }
            int adjPosition = position - (getHeaderListCount() + 1);
            int adapterCount;
            if (adapter != null){
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount){
                    if (payloads.isEmpty()){
                        adapter.onBindViewHolder(holder, adjPosition);
                    } else {
                        adapter.onBindViewHolder(holder, adjPosition, payloads);
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            if (loadingMoreEnabled){
                if (adapter != null){
                    return getHeaderListCount() + adapter.getItemCount() + 2;
                } else {
                    return getHeaderListCount() + 2;
                }
            } else {
                if (adapter != null){
                    return getHeaderListCount() + adapter.getItemCount() + 1;
                } else {
                    return getHeaderListCount() + 1;
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            int adjPosition = position - (getHeaderListCount() + 1);
            if (isRefreshHeader(position)){
                return TYPE_REFRESH_HEADER;
            }
            if (isHeader(position)){
                position = position - 1;
                return headerTypeList.get(position);
            }
            if (isFooter(position)){
                return TYPE_FOOTER;
            }
            int adapterCount;
            if (adapter != null){
                adapterCount = adapter.getItemCount();
                if (adjPosition < adapterCount){
                    int type = adapter.getItemViewType(adjPosition);
                    if (isReservedItemViewType(type)){
                        throw new IllegalStateException("XRecyclerView require itemViewType in adapter should be less than 10000");
                    }
                    return type;
                }
            }
            return 0;
        }

        @Override
        public long getItemId(int position) {
            if (adapter != null && position >= getHeaderListCount() + 1){
                int adjPosition = position - (getHeaderListCount() + 1);
                if (adjPosition < adapter.getItemCount()){
                    return adapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            LayoutManager manager = recyclerView.getLayoutManager();
            if (manager instanceof GridLayoutManager){
                final GridLayoutManager gridManager = ((GridLayoutManager) manager);
                gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return (isHeader(position) || isFooter(position) || isRefreshHeader(position))
                                ? gridManager.getSpanCount() : 1;
                    }
                });
            }
        }

        @Override
        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeader(holder.getLayoutPosition()) || isRefreshHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))){
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
            adapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
            adapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void onViewRecycled(@NonNull ViewHolder holder) {
            adapter.onViewRecycled(holder);
        }

        @Override
        public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
            return adapter.onFailedToRecycleView(holder);
        }

        @Override
        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver observer) {
            adapter.unregisterAdapterDataObserver(observer);
        }

        @Override
        public void registerAdapterDataObserver(@NonNull AdapterDataObserver observer) {
            adapter.registerAdapterDataObserver(observer);
        }

        class SimpleViewHolder extends ViewHolder {
            public SimpleViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }

    /**
     * 根据header的itemType判断是哪个header
     * @param itemType
     * @return
     */
    private View getHeaderViewByType(int itemType){
        if (!isHeaderType(itemType)){
            return null;
        }
        return headerViewList.get(itemType - HEADER_INIT_INDEX);
    }

    /**
     * 判断一个type是否为headerType
     * @param itemViewType
     * @return
     */
    private boolean isHeaderType(int itemViewType){
        return headerViewList.size() > 0 && headerTypeList.contains(itemViewType);
    }

    /**
     * 判断是否是XRecyclerView保留的itemViewType
     * @param itemViewType
     * @return
     */
    private boolean isReservedItemViewType(int itemViewType){
        if (itemViewType == TYPE_REFRESH_HEADER || itemViewType == TYPE_FOOTER || headerViewList.contains(itemViewType)){
            return true;
        } else {
            return false;
        }
    }

    public void setLoadingListener(LoadingListener listener){
        loadingListener = listener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //解决和CollapsingToolbarLayout冲突的问题
        AppBarLayout appBarLayout = null;
        ViewParent parent = getParent();
        while (parent != null){
            if (parent instanceof CoordinatorLayout){
                break;
            }
            parent = parent.getParent();
        }
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            for (int i = childCount - 1; i >= 0; i--){
                View child = coordinatorLayout.getChildAt(i);
                if (child instanceof AppBarLayout){
                    appBarLayout = (AppBarLayout) child;
                    break;
                }
            }
            if (appBarLayout != null){
                appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
                    @Override
                    public void onStateChanged(AppBarLayout appBarLayout, State state) {
                        appbarState = state;
                    }
                });
            }
        }
    }

    public interface LoadingListener {

        void onRefresh();

        void onLoadMore();

    }

    private class DataObserver extends AdapterDataObserver {

        @Override
        public void onChanged() {
            if (wrapAdapter != null){
                wrapAdapter.notifyDataSetChanged();
            }
            if (wrapAdapter != null && emptyView != null){
                int emptyCount = 1 + wrapAdapter.getHeaderListCount();
                if (loadingMoreEnabled){
                    emptyCount++;
                }
                if (wrapAdapter.getItemCount() == emptyCount){
                    emptyView.setVisibility(VISIBLE);
                    XRecyclerView.this.setVisibility(GONE);
                } else {
                    emptyView.setVisibility(GONE);
                    XRecyclerView.this.setVisibility(VISIBLE);
                }
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            wrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            wrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            wrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            wrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            wrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }

    }

    public class DividerItemDecoration extends ItemDecoration {

        private Drawable divider;
        private int orientation;

        public DividerItemDecoration(Drawable divider) {
            this.divider = divider;
        }

        @Override
        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state) {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                drawHorizontalDividers(c, parent);
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                drawVerticalDividers(c, parent);
            }
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
            super.getItemOffsets(outRect, view, parent, state);

            if (parent.getChildAdapterPosition(view) <= wrapAdapter.getHeaderListCount() + 1){
                return;
            }
            orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = divider.getIntrinsicWidth();
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                outRect.top = divider.getIntrinsicHeight();
            }
        }

        private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
            int parentTop = parent.getPaddingTop();
            int parentBottom = parent.getHeight() - parent.getPaddingBottom();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++){
                View child = parent.getChildAt(i);

                LayoutParams params = (LayoutParams) child.getLayoutParams();

                int parentLeft = child.getRight() + params.rightMargin;
                int parentRight = parentLeft + divider.getIntrinsicWidth();

                divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                divider.draw(canvas);
            }
        }

        private void drawVerticalDividers(Canvas canvas, RecyclerView parent){
            int parentLeft = parent.getPaddingLeft();
            int parentRight = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                View child = parent.getChildAt(i);

                LayoutParams params = (LayoutParams) child.getLayoutParams();

                int parentTop = child.getBottom() + params.bottomMargin;
                int parentBottom = parentTop + divider.getIntrinsicHeight();

                divider.setBounds(parentLeft, parentTop, parentRight, parentBottom);
                divider.draw(canvas);
            }
        }
    }

    private int scrollDyCounter = 0;

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
        if (position == 0){
            scrollDyCounter = 0;
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (scrollAlphaChangeListener == null){
            return;
        }
        int height = scrollAlphaChangeListener.setLimitHeight();
        scrollDyCounter = scrollDyCounter + dy;
        if (scrollDyCounter <= 0){
            scrollAlphaChangeListener.onAlphaChange(0);
        } else if (scrollDyCounter <= height && scrollDyCounter > 0){
            float scale = (float) scrollDyCounter / height;
            float alpha = (255 * scale);
            scrollAlphaChangeListener.onAlphaChange((int) alpha);
        } else {
            scrollAlphaChangeListener.onAlphaChange(255);
        }
    }

    private ScrollAlphaChangeListener scrollAlphaChangeListener;

    public void setScrollAlphaChangeListener(ScrollAlphaChangeListener scrollAlphaChangeListener) {
        this.scrollAlphaChangeListener = scrollAlphaChangeListener;
    }

    public interface ScrollAlphaChangeListener {
        void onAlphaChange(int alpha);
        int setLimitHeight();
    }

}
