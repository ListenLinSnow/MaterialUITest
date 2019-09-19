package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.OneOpenGlRenderer;
import com.example.lc.materialuitest.view.OneOpenGlSurfaceView;

import butterknife.ButterKnife;

public class OpenGLTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneOpenGlSurfaceView glSurfaceView = new OneOpenGlSurfaceView(this);
        //setContentView(R.layout.activity_open_gl_test);
        setContentView(glSurfaceView);
        ButterKnife.bind(this);

        init();
    }

    private void init(){

    }

}
