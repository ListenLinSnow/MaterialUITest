package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButtonsActivity extends AppCompatActivity {

    @BindView(R.id.spinner_buttons)
    AppCompatSpinner spinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

    }

}
