package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.SlideRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlideRecyclerActivity extends AppCompatActivity {

    @BindView(R.id.rv_slide_recycler)
    SlideRecyclerView slideRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_recycler);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

    }

}
