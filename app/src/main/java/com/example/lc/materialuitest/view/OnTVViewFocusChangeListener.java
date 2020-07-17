package com.example.lc.materialuitest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.lc.materialuitest.R;

/**
 * Created by lc on 2020/3/23.
 * TV 上的控件聚焦展示效果
 */

public class OnTVViewFocusChangeListener implements View.OnFocusChangeListener {

    Context context;

    public OnTVViewFocusChangeListener(Context context) {
        this.context = context;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            /*if (v instanceof EditText || v instanceof LinearLayout) {
                v.setBackground(context.getResources().getDrawable(R.drawable.shape_selector_stroke));
            }*/
            ViewCompat.animate(v).scaleX(1.1f).scaleY(1.1f).start();
        } else {
            ViewCompat.animate(v).scaleX(1f).scaleY(1f).start();
            /*if (v instanceof EditText || v instanceof LinearLayout) {
                v.setBackground(null);
            }*/
        }
    }

}
