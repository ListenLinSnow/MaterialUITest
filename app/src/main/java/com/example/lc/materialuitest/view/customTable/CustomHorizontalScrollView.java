package com.example.lc.materialuitest.view.customTable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 自定义水平滚动视图，主要解决ScrollView在API23以下没有滚动监听事件的问题
 */

public class CustomHorizontalScrollView extends HorizontalScrollView {

    //触摸前的点
    private float x1;

    //手势抬起之后的点
    private float x2;

    private OnScrollChangeListener onScrollChangeListener;

    public CustomHorizontalScrollView(Context context) {
        super(context);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = ev.getX();
                if (computeHorizontalScrollOffset() == 0 && x1 - x2 < 0){
                    //滑到最左边
                    if (onScrollChangeListener != null){
                        onScrollChangeListener.onScrollFarLeft(this);
                    }
                } else if (computeHorizontalScrollRange() - computeHorizontalScrollOffset()
                            <= computeHorizontalScrollExtent() && x1 - x2 > 0){
                    //滑到最右边
                    if (onScrollChangeListener != null){
                        onScrollChangeListener.onScrollFarRight(this);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public interface OnScrollChangeListener {
        /**
         * 滚动监听
         * @param scrollView
         * @param x
         * @param y
         */
        void onScrollChanged(HorizontalScrollView scrollView, int x, int y);

        /**
         * 滑动到最左侧
         * @param scrollView
         */
        void onScrollFarLeft(HorizontalScrollView scrollView);

        /**
         * 滑动到最右侧
         * @param scrollView
         */
        void onScrollFarRight(HorizontalScrollView scrollView);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener){
        this.onScrollChangeListener = onScrollChangeListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //回调
        if (onScrollChangeListener != null){
            onScrollChangeListener.onScrollChanged(this, l, t);
        }
    }
}
