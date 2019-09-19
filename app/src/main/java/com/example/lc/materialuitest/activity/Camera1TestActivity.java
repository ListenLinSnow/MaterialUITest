package com.example.lc.materialuitest.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.TimeFormatSelect;
import com.example.lc.materialuitest.util.BitmapUtil;
import com.example.lc.materialuitest.util.TimeUtil;
import com.example.lc.materialuitest.view.CameraSettingDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Camera1TestActivity extends AppCompatActivity {

    @BindView(R.id.rl_camera_1_test)
    RelativeLayout rlCamera;
    @BindView(R.id.sfv_camera_1_test)
    SurfaceView surfaceView;
    @BindView(R.id.tv_camera_1_test_water_mark)
    TextView tvWaterMark;
    @BindView(R.id.btn_camera_1_test_shoot)
    Button btnShoot;
    @BindView(R.id.btn_camera_1_test_setting)
    Button btnSetting;

    private TimerTask timerTask = null;                //定时任务，获取每秒的时间并显示
    private Timer timer = null;

    private SurfaceHolder surfaceHolder = null;
    private Camera camera = null;

    private String timeFormat = null;

    private LocationClient locationClient = null;
    private MyLocationListener myLocationListener = null;
    private String address = "";
    private double longitude = 0;
    private double latitude = 0;

    private static final int GET_TIME = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_1_test);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tvWaterMark.setGravity(Gravity.LEFT|Gravity.BOTTOM);
        rlCamera.setDrawingCacheEnabled(true);

        timeFormat = TimeFormatSelect.FORMAT_STANDARD.getDesc();

        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what = GET_TIME;
                handler.sendMessage(message);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.setFixedSize(1920, 1080);
        surfaceHolder.setKeepScreenOn(true);
        surfaceHolder.addCallback(callback);

        initLocation();
    }

    /**
     * 百度地图定位所需数据初始化
     */
    private void initLocation(){
        locationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener();
        locationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setIsNeedAltitude(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(true);
        option.setScanSpan(1000);
        option.setLocationNotify(true);
        option.setIsNeedLocationPoiList(true);
        option.setIgnoreKillProcess(false);
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);

        if(!locationClient.isStarted()){
            locationClient.start();
        }
    }

    /**
     * 拍摄按钮、设置按钮的点击事件
     * @param view
     */
    @OnClick({R.id.btn_camera_1_test_shoot, R.id.btn_camera_1_test_setting})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_camera_1_test_shoot:
                camera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        savePic(data);
                    }
                });
                break;
            case R.id.btn_camera_1_test_setting:
                showSettingDialog();
                break;
        }
    }

    /**
     * 水印属性设置dialog
     */
    private void showSettingDialog(){
        CameraSettingDialog dialog = new CameraSettingDialog(this);
        dialog.setOnColorSelectListener(new CameraSettingDialog.OnColorSelectListener() {
            @Override
            public void onColorSelect(int color) {
                tvWaterMark.setTextColor(color);
            }
        });
        dialog.setOnSizeSelectListener(new CameraSettingDialog.OnSizeSelectListener() {
            @Override
            public void onSizeSelect(float size) {
                tvWaterMark.setTextSize(size);
            }
        });
        dialog.setOnLocationSelectListener(new CameraSettingDialog.OnLocationSelectListener() {
            @Override
            public void onLocationSelect(int gravity) {
                tvWaterMark.setGravity(gravity);
            }
        });
        dialog.setOnTimeFormatSelectListener(new CameraSettingDialog.OnTimeFormatSelectListener() {
            @Override
            public void onTimeFormatSelect(String format) {
                timeFormat = format;
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        Display display = getWindowManager().getDefaultDisplay();
        params.height = (int)(display.getHeight() * 1);
        params.width = (int)(display.getWidth() * 1);
        params.alpha = 0.5f;
        window.setAttributes(params);
    }

    /**
     * surfaceHolder 的监听
     */
    SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            initCamera();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if(surfaceHolder.getSurface() == null){
                return;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }
    };

    /**
     * 初始化Camera
     */
    private void initCamera(){
        if(camera != null){
            releaseCamera();
        }
        //启动后置摄像头
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        if(camera == null){
            showToast("未获取到相机设备");
            return;
        }
        try {
            camera.setPreviewDisplay(surfaceHolder);
            //配置camera参数
            setCameraParams();
            camera.startPreview();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置Camera参数
     */
    private void setCameraParams(){
        if(camera != null){
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.setPreviewSize(1920, 1080);
            parameters.setPictureSize(1920,1080);
            //设置聚焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            //缩短recording启动时间
            parameters.setRecordingHint(true);
            //影像稳定能力
            if(parameters.isVideoSnapshotSupported()){
                parameters.setVideoStabilization(true);
            }
            //设置手机为竖直方向
            parameters.set("orientation", "portrait");
            parameters.setPreviewFrameRate(30);
            camera.setDisplayOrientation(90);
            camera.setParameters(parameters);
        }
    }

    /**
     * 停止Camera,释放资源
     */
    private void releaseCamera(){
        if(camera != null){
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    /**
     * 保存照片
     */
    private void savePic(byte[] data){
        String tempPath = Environment.getExternalStorageDirectory() + File.separator + "DCIM" + File.separator + "Camera" + File.separator + System.currentTimeMillis() + ".jpg";
        Bitmap srcBitmap = BitmapUtil.rotate(BitmapFactory.decodeByteArray(data, 0, data.length), 90);
        Bitmap flagBitmap = rlCamera.getDrawingCache();
        Bitmap bitmap = mergeBitmap(srcBitmap, flagBitmap);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(tempPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            showToast("保存成功");
        }
    }

    /*
     *合并Bitmap(覆盖式)
     */
    public Bitmap mergeBitmap(Bitmap backBitmap, Bitmap frontBitmap) {
        if (backBitmap == null || backBitmap.isRecycled()
                || frontBitmap == null || frontBitmap.isRecycled()) {
            return null;
        }
        Bitmap bitmap = backBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Rect baseRect  = new Rect(0, 0, backBitmap.getWidth(), backBitmap.getHeight());
        Rect frontRect = new Rect(0, 0, frontBitmap.getWidth(), frontBitmap.getHeight());
        canvas.drawBitmap(frontBitmap, frontRect, baseRect, null);
        return bitmap;
    }

    /**
     * 定义定位监听类
     */
    private class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            latitude = bdLocation.getLatitude();
            longitude = bdLocation.getLongitude();

            String locationDescribe = bdLocation.getLocationDescribe();
            Log.d("JsonList", "位置描述:" + locationDescribe);

            List<Poi> poiList = bdLocation.getPoiList();
            String info = "";
            for (int i=0;i<poiList.size();i++){
                info += poiList.get(i).getName() + "\n";
            }
            Log.d("JsonList", "周边poi:" + info);

            double altitude = bdLocation.getAltitude();
            Log.d("JsonList", "海拔:" + altitude);
            /*locationClient.startIndoorMode();
            String buildName = bdLocation.getBuildingName();
            String floor = bdLocation.getFloor();
            Log.d("JsonList", "室内定位:" + buildName + ";" + floor);*/

            String addr = bdLocation.getAddrStr();
            String country = bdLocation.getCountry();
            String province = bdLocation.getProvince();
            String city = bdLocation.getCity();
            String district = bdLocation.getDistrict();
            String street = bdLocation.getStreet();
            address = addr;
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_TIME:
                    setWatermarkContent();
                    break;
            }
        }
    };

    /**
     * 设置水印内容
     */
    private void setWatermarkContent(){
        StringBuilder info = new StringBuilder();
        String time = TimeUtil.getTimeByFormatWithoutTime(timeFormat);
        String location = "";
        if(longitude != 0 && latitude !=0){
            location = longitude + "E，" + latitude + "N";
        }

        info.append(time);
        appendContent(info, location);
        appendContent(info, address);

        tvWaterMark.setText(info);
    }

    private void appendContent(StringBuilder info, String content){
        if(!TextUtils.isEmpty(content)){
            info.append("\n" + content);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(locationClient.isStarted()){
            locationClient.stop();
        }
    }
}
