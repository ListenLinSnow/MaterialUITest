package com.example.lc.materialuitest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.MyApplication;
import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_buttons)
    Toolbar toolbar;
    @BindView(R.id.btn_buttons_test)
    Button btnTest;
    @BindView(R.id.spinner_buttons)
    AppCompatSpinner spinner;
    @BindView(R.id.ll_buttons_test)
    LinearLayout llTest;
    @BindView(R.id.tv_buttons_test)
    TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        llTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
