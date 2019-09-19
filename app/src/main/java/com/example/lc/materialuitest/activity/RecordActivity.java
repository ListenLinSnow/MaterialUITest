package com.example.lc.materialuitest.activity;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Size;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.CameraGLSurfaceView;
import com.example.lc.materialuitest.view.CameraUtil;
import com.example.lc.materialuitest.view.PermisstionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordActivity extends AppCompatActivity implements CameraGLSurfaceView.CameraGLSurfaceViewCallback {

    @BindView(R.id.sfv_record)
    CameraGLSurfaceView sfvRecord;

    private Size previewSize;

    private final static int CAMERA_REQUEST_CODE = 1;
    private final static int STORE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        previewSize = null;
        sfvRecord.setCallback(this);
        if(previewSize != null) {
            CameraUtil.switchCamera(this, sfvRecord.getSurfaceTexture(), previewSize.getWidth(), previewSize.getHeight());
            sfvRecord.setBackCamera(CameraUtil.isBackCamera());
        }
    }

    @Override
    public void onSurfaceViewCreate(SurfaceTexture texture) {

    }

    @Override
    public void onSurfaceViewChange(int width, int height) {
        previewSize = new Size(width, height);
        startPreview();
    }

    private void startPreview(){
        if(requestCameraPermission() && previewSize != null){
            if(CameraUtil.getCamera() == null){
                CameraUtil.openCamera();
                CameraUtil.setDisplay(sfvRecord.getSurfaceTexture());
            }
            CameraUtil.startPreview(this, sfvRecord.getWidth(), sfvRecord.getHeight());
        }
    }

    private void releasePreview(){
        CameraUtil.releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPreview();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePreview();
    }

    private boolean requestCameraPermission() {
        return PermisstionUtil.checkPermissionsAndRequest(this, PermisstionUtil.CAMERA, CAMERA_REQUEST_CODE, "请求相机权限被拒绝");
    }

}
