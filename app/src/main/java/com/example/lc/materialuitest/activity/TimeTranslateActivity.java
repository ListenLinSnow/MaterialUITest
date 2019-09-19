package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.util.TimeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeTranslateActivity extends AppCompatActivity {

    @BindView(R.id.et_time_translate_date)
    EditText etDate;
    @BindView(R.id.btn_time_translate)
    Button btnTime;
    @BindView(R.id.et_time_translate)
    EditText etTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_translate);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_time_translate})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_time_translate:
                long time = TimeUtil.stringToLong(etDate.getText().toString(), "yyyyMMdd HHmmss");
                etTime.setText(String.valueOf(time));
                break;
        }
    }

}
