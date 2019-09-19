package com.example.lc.materialuitest.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class SlideRecyclerView extends RecyclerView {

    private static final int INVALID_POSITION = -1;         //触摸到的点不在子View范围内
    private static final int INVALID_CHILD_WIDTH = -1;      //子ItemView不含两个View
    private static final int SNAP_VALOCITY = 600;           //最小滑动速度

    private VelocityTracker velocityTracker;                //速度追踪器
    private int touchSlop;                                  //认为是滑动的最小距离
    private Rect touchFrame;                                //子view所在的矩形范围
    private Scroller scroller;
    private float lastX, firstX, firstY;                    //滑动过程中记录上次触碰点X，及首次触碰范围
    private boolean isSlide;                                //是否滑动子View
    private ViewGroup flingView;                            //触碰的子view
    private int position;                                   //触碰的view的位置
    private int menuViewWidth;                              //菜单按钮高度

    public SlideRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        scroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        obtainVelocity(e);
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                firstX = lastX - x;
                firstY = y;
                position = pointToPosition(x, y);
                if(position != INVALID_POSITION){
                    View view = flingView;
                    //获取触碰点所在的view
                    flingView = (ViewGroup) getChildAt(position - ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition());
                    //这里判断一下如果之前的view已经打开，而当前碰到的view不是那个view，则立即关闭之前的view
                    if(view != null && flingView != null && view.getScrollX() != 0){
                        view.scrollTo(0 ,0);
                    }
                    //这里进行了强制的要求，RecyclerView的子ViewGroup必须要用两个view，这样菜单按钮才有值
                    if(flingView.getChildCount() == 2){
                        menuViewWidth = flingView.getChildAt(1).getWidth();
                    }else {
                        menuViewWidth = INVALID_CHILD_WIDTH;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                if (Math.abs(xVelocity) > SNAP_VALOCITY && Math.abs(xVelocity) > Math.abs(yVelocity)
                        || Math.abs(x - firstX) >= touchSlop && Math.abs(x - firstX) > Math.abs(y - firstY)){
                    isSlide = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                releaseVelocity();
                break;
        }
        return super.onInterceptTouchEvent(e);
    }

    private void obtainVelocity(MotionEvent event){
        if(velocityTracker == null){
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void releaseVelocity(){
        if(velocityTracker != null){
            velocityTracker.clear();
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isSlide && position != INVALID_POSITION){
            float x = e.getX();
            obtainVelocity(e);
            switch (e.getAction()){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(menuViewWidth != INVALID_CHILD_WIDTH){
                        float dx = lastX - x;
                        if (flingView.getScrollX() + dx <= menuViewWidth && flingView.getScrollX() + dx > 0){
                            flingView.scrollBy((int) dx, 0);
                        }
                        lastX = x;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if(menuViewWidth != INVALID_CHILD_WIDTH){
                        int scrollX = flingView.getScrollX();
                        velocityTracker.computeCurrentVelocity(1000);
                        if (velocityTracker.getXVelocity() < -SNAP_VALOCITY){
                            scroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        }else if(scrollX >= menuViewWidth / 2){
                            scroller.startScroll(scrollX, 0, menuViewWidth - scrollX, 0, Math.abs(menuViewWidth - scrollX));
                        }else {
                            scroller.startScroll(scrollX, 0, -scrollX, 0, Math.abs(scrollX));
                        }
                        invalidate();
                    }
                    menuViewWidth = INVALID_CHILD_WIDTH;
                    isSlide = false;
                    position = INVALID_POSITION;
                    releaseVelocity();
                    break;
            }
            return true;
        }else {
            if(flingView != null && flingView.getScrollX() != 0){
                flingView.scrollTo(0, 0);
            }
            releaseVelocity();
        }
        return super.onTouchEvent(e);
    }

    private int pointToPosition(int x, int y){
        int firstPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        Rect frame = touchFrame;
        if(frame == null){
            touchFrame = new Rect();
            frame = touchFrame;
        }

        int count = getChildCount();
        for (int i = count - 1; i >= 0; i--){
            View child = getChildAt(i);
            if(child.getVisibility() == VISIBLE){
                child.getHitRect(frame);
                if(frame.contains(x, y)){
                    return firstPosition + 1;
                }
            }
        }
        return INVALID_POSITION;
    }


}
