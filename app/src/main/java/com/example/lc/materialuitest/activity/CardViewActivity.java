package com.example.lc.materialuitest.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lc.materialuitest.R;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewActivity extends AppCompatActivity {

    @BindView(R.id.cv_card_view)
    CardView cardView;
    @BindView(R.id.cv2_card_view)
    CardView cardView2;
    @BindView(R.id.iv3_card_view)
    ImageView upDownIv;
    @BindView(R.id.ll_card_view)
    LinearLayout contentLl;

    int totalHeight = 0;
    int contentHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float density = metrics.density;
        Log.d("test", "width:" + width + "\nheight:" + height + "\ndensity:" + density);

        cardView.post(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        });

        upDownIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentLl.getVisibility() == View.GONE){
                    contentLl.setVisibility(View.VISIBLE);
                    upDownIv.setImageResource(R.mipmap.arrow_up);

                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(contentLl, "alpha", 0f, 1f);
                    alphaAnimator.setInterpolator(new AccelerateInterpolator());
                    alphaAnimator.setDuration(500);
                    alphaAnimator.start();

                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                            int height = totalHeight - contentHeight + (int)(value * contentHeight);
                            params.height = height;
                            cardView.setLayoutParams(params);
                        }
                    });
                    valueAnimator.start();
                }else {
                    upDownIv.setImageResource(R.mipmap.arrow_down);

                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(contentLl, "alpha", 1f, 0f);
                    alphaAnimator.setInterpolator(new AccelerateInterpolator());
                    alphaAnimator.setDuration(500);
                    alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            contentLl.setAlpha(value);
                            if(value == 0.0){
                                contentLl.setVisibility(View.GONE);
                            }
                        }
                    });
                    alphaAnimator.start();
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f);
                    valueAnimator.setInterpolator(new AccelerateInterpolator());
                    valueAnimator.setDuration(500);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (float) animation.getAnimatedValue();
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) cardView.getLayoutParams();
                            int height = totalHeight - contentHeight + (int)(value * contentHeight);
                            params.height = height;
                            cardView.setLayoutParams(params);
                        }
                    });
                    valueAnimator.start();
                }
            }
        });
    }

    private Map getWidthAndHeight(View view){
        int height = view.getMeasuredHeight();
        int width = view.getMeasuredWidth();
        Log.d("test", "width:" + width + "\nheight:" + height);
        Map map = new ArrayMap();
        map.put("width", width);
        map.put("height", height);
        return map;
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    contentHeight = contentLl.getHeight();
                    Log.d("test", "contentHeight:" + contentHeight);
                    contentLl.setVisibility(View.GONE);
                    totalHeight = (int) getWidthAndHeight(cardView).get("height");
                    Log.d("test", "totalHeight:" + totalHeight);
                    break;
                default:
                    break;
            }
        }
    };



}
