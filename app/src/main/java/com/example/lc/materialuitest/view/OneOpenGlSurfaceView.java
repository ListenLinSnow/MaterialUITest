package com.example.lc.materialuitest.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OneOpenGlSurfaceView extends GLSurfaceView {

    private final OneOpenGlRenderer renderer;

    public OneOpenGlSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        renderer = new OneOpenGlRenderer();
        setRenderer(renderer);
    }
}
