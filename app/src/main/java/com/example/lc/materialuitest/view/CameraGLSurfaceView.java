package com.example.lc.materialuitest.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public class CameraGLSurfaceView extends GLSurfaceView implements CameraGLSurfaceRenderer.CameraGLSurfaceRendererCallback {

    private CameraGLSurfaceRenderer renderer;
    private CameraGLSurfaceViewCallback callback;

    public CameraGLSurfaceView(Context context) {
        super(context, null);
    }

    public CameraGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setEGLContextClientVersion(2);
        renderer = new CameraGLSurfaceRenderer(context, this);
        setRenderer(renderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public SurfaceTexture getSurfaceTexture(){
        assert (renderer.getSurfaceTexture() == null);
        return renderer.getSurfaceTexture();
    }

    @Override
    public void onRequestRenderer() {
        requestRender();
    }

    @Override
    public void onCreate(SurfaceTexture surfaceTexture) {
        if(callback != null){
            callback.onSurfaceViewCreate(surfaceTexture);
        }
    }

    @Override
    public void onChange(int width, int height) {
        if(callback != null){
            callback.onSurfaceViewChange(width, height);
        }
    }

    @Override
    public void onDraw() {

    }

    public void setCallback(CameraGLSurfaceViewCallback callback) {
        this.callback = callback;
    }

    public void setBackCamera(boolean isBackCamera){
        this.renderer.setBackCamera(isBackCamera);
    }

    public interface CameraGLSurfaceViewCallback{
        void onSurfaceViewCreate(SurfaceTexture texture);
        void onSurfaceViewChange(int width, int height);
    }

}
