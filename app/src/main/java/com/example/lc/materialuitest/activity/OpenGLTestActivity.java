package com.example.lc.materialuitest.activity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lc.materialuitest.view.MyRenderer;

public class OpenGLTestActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init(){
        glSurfaceView = new MyGLSurfaceView(this);
        setContentView(glSurfaceView);
    }

    class MyGLSurfaceView extends GLSurfaceView{

        private MyRenderer myRenderer;

        public MyGLSurfaceView(Context context){
            super(context);

            setEGLContextClientVersion(2);

            myRenderer = new MyRenderer();

            //设置Renderer用于绘图
            setRenderer(myRenderer);

            //只有在绘制数据改变时才绘制view, 可以防止GLSurfaceView帧重绘
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }

    }

}
