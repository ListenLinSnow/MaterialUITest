package com.example.lc.materialuitest.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

public class ControlsDragActivity extends AppCompatActivity implements View.OnTouchListener {

    ViewGroup viewGroup = null;

    private int sy;
    private int sx;

    private SharedPreferences sharedPreferences = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init(){
        viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_controls_drag, null);
        //viewGroup = (ViewGroup) findViewById(R.id.rl_controls_drag);

        sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);
        final int left = sharedPreferences.getInt("left", -1);
        final int top = sharedPreferences.getInt("top", -1);
        final int bottom = sharedPreferences.getInt("bottom", -1);
        final int right = sharedPreferences.getInt("right", -1);
        Log.d("JsonList", left + ";" + top + ";" + bottom + ";" + right);

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView tv1 = new TextView(this);
        tv1.setText("测试文字1");
        tv1.setGravity(Gravity.CENTER);
        Drawable drawable1 = getDrawable(R.mipmap.location);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        tv1.setCompoundDrawables(drawable1, null, null, null);
        tv1.setLayoutParams(params1);
        tv1.setId(100);

        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.addRule(RelativeLayout.BELOW, 100);
        TextView tv2 = new TextView(this);
        tv2.setText("测试文字2");
        tv2.setGravity(Gravity.CENTER);
        Drawable drawable2 = getDrawable(R.mipmap.location);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        tv2.setCompoundDrawables(drawable2, null, null, null);
        tv2.setLayoutParams(params2);
        tv2.setId(200);

        viewGroup.addView(tv1);
        viewGroup.addView(tv2);
        setContentView(viewGroup);

        if (left != -1 && top != -1 && bottom != -1 && right != -1){
             tv1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv1.layout(left, top, right, bottom);
                }
            }, 1000);
        }

        tv1.setOnTouchListener(this);
        tv2.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:{
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                sx = (int) event.getRawX();
                sy = (int) event.getRawY();
            }
                break;
            case MotionEvent.ACTION_MOVE: {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v.getLayoutParams();
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();
                int dx = x - sx;
                int dy = y - sy;
                int l = v.getLeft();
                int t = v.getTop();
                int r = v.getRight();
                int b = v.getBottom();
                v.layout(l + dx, t + dy, r + dx, b + dy);
                sx = (int) event.getRawX();
                sy = (int) event.getRawY();
                /*Log.d("JsonList", "x:" + x + ";y:" + y + ";dx:" + dx + ";dy:" + dy + ";left:" + v.getLeft() + ";top:" + v.getTop() + ";right:" + v.getRight()
                            + ";bottom:" + v.getBottom() + ";sx:" + sx + ";sy:" + sy);*/
            }
                break;
            case MotionEvent.ACTION_UP: {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("left", v.getLeft());
                editor.putInt("right", v.getRight());
                editor.putInt("top", v.getTop());
                editor.putInt("bottom", v.getBottom());
                editor.commit();
            }
                break;
        }
        return true;
    }
}
