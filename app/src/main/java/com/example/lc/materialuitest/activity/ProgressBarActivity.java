package com.example.lc.materialuitest.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

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
    @BindView(R.id.sb_progress_bar)
    SeekBar seekBar;
    @BindView(R.id.et_progress_bar)
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editText.setText(progress + "");
                Toast.makeText(ProgressBarActivity.this, progress + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int value = Integer.valueOf(s.toString());
                seekBar.setProgress(value);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
