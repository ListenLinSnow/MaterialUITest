package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickPicActivity extends AppCompatActivity {

    @BindView(R.id.btn_pick_pic)
    Button btnPic;
    @BindView(R.id.rv_pick_pic)
    RecyclerView rvPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_pic);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

    }

    @OnClick({R.id.btn_pick_pic})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_pick_pic:

                break;
        }
    }



}
