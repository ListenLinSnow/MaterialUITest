package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalScrollViewActivity extends AppCompatActivity {

    @BindView(R.id.hsv_hsv)
    HorizontalScrollView hsv;
    @BindView(R.id.ll_hsv)
    LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        for (int i = 0; i < 10; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout myLinear = new LinearLayout(this);
            layoutParams.setMargins(5, 0, 5, 20);
            myLinear.setOrientation(LinearLayout.VERTICAL);
            myLinear.setTag(i * 10 + i);
            ll.addView(myLinear, layoutParams);

            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.code);
            myLinear.addView(imageView, layoutParams1);

            LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setText(i * 10 + i + "");
            textView.setGravity(Gravity.CENTER);
            myLinear.addView(textView, tvLp);

            myLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HorizontalScrollViewActivity.this, v.getTag().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
