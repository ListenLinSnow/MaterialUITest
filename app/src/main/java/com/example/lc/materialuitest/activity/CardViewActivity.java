package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardViewActivity extends AppCompatActivity {

    @BindView(R.id.cv_card_view)
    CardView cardView;
    @BindView(R.id.cv2_card_view)
    CardView cardView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        ButterKnife.bind(this);

    }
}
