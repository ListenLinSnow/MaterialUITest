package com.example.lc.materialuitest.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;

import com.example.lc.materialuitest.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressBarActivity extends AppCompatActivity {

    @BindView(R.id.pb_progress_bar_determinate)
    ProgressBar pbDeterminate;
    @BindView(R.id.pb_progress_bar_indeterminate)
    ProgressBar pbIndaterminate;
    @BindView(R.id.pb_progress_bar_buffer)
    ProgressBar pbBuffer;
    @BindView(R.id.pb_progress_bar_determinate_and_indeterminate)
    ProgressBar pbMixing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

    }

}
