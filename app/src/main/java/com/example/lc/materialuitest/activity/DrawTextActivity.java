package com.example.lc.materialuitest.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.CanvasView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DrawTextActivity extends AppCompatActivity {

    @BindView(R.id.et_draw_text)
    EditText etContent;
    @BindView(R.id.btn_draw_text_start)
    Button btnStart;
    @BindView(R.id.btn_draw_text_stop)
    Button btnStop;
    @BindView(R.id.cv_draw_text)
    CanvasView canvasView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_text);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_draw_text_start, R.id.btn_draw_text_stop})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_draw_text_start:
                canvasView.setPath(getPath());
                canvasView.start();
                break;
            case R.id.btn_draw_text_stop:
                canvasView.stop();
                break;
        }
    }

    private Path getPath() {
        Path textPath = new Path();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize(120);
        paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
        String s = etContent.getText().toString().length() <= 0 ? "cece" : etContent.getText().toString();
        paint.getTextPath(s, 0, s.length(), 0, 200, textPath);
        textPath.close();
        return textPath;
    }

}
