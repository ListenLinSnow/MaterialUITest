package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AMapTestActivity extends AppCompatActivity {

    @BindView(R.id.tv_amap_info)
    TextView tvInfo;

    private AMapLocationClient locationClient = null;
    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            double latitude = aMapLocation.getLatitude();
            double longitude = aMapLocation.getLongitude();
            Log.d("JsonList", "经纬度：" + latitude + ";" + longitude);

            double altitude = aMapLocation.getAltitude();
            Log.d("JsonList", "海拔：" + altitude);

            String floor = aMapLocation.getFloor();
            Log.d("JsonList", "楼层：" + floor);

            String address = aMapLocation.getAddress();
            Log.d("JsonList", "地址：" + address);

            String aoiName = aMapLocation.getAoiName();
            Log.d("JsonList", "aoiName：" + aoiName);

            String poiName = aMapLocation.getPoiName();
            Log.d("JsonList", "poiName：" + poiName);

            String buildingID = aMapLocation.getBuildingId();
            Log.d("JsonList", "buildingId：" + buildingID);

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap_test);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        locationClient = new AMapLocationClient(this);

        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(false);
        option.setInterval(1000);
        option.setNeedAddress(true);
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport);
        if(locationClient != null){
            locationClient.setLocationOption(option);
            locationClient.setLocationListener(locationListener);
            locationClient.startLocation();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stopLocation();
    }
}
