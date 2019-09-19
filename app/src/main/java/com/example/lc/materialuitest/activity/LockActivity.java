package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockActivity extends AppCompatActivity {

    @BindView(R.id.btn_lock)
    Button btnLock;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_lock})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_lock:

                break;
        }
    }

}
