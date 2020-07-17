package com.example.lc.materialuitest.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.lc.materialuitest.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AMapTestActivity extends AppCompatActivity {

    @BindView(R.id.btn_amap_test)
    Button btnAmap;
    @BindView(R.id.mapview_amap_test)
    MapView mapView;

    AMap aMap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap_test);
        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        aMap = mapView.getMap();
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.showMyLocation(true);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);

        btnAmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
                    @Override
                    public void onMapScreenShot(Bitmap bitmap) {

                    }

                    @Override
                    public void onMapScreenShot(Bitmap bitmap, int status) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                        FileOutputStream fos = null;
                        try {
                            String folderPath = Environment.getExternalStorageDirectory() + File.separator + "Material";
                            if (!new File(folderPath).exists()){
                                new File(folderPath).mkdir();
                            }
                            String path = folderPath + File.separator + format.format(new Date()) + ".jpg";
                            fos = new FileOutputStream(path);
                            boolean isSave = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();
                            if (isSave){
                                Toast.makeText(AMapTestActivity.this, "截屏成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AMapTestActivity.this, "截屏失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                View view = LayoutInflater.from(AMapTestActivity.this).inflate(R.layout.view_marker, null);
                Bitmap bitmap = convertViewToBitmap(view);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.draggable(true);
                markerOptions.position(latLng);
                markerOptions.title("测试");
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                aMap.addMarker(markerOptions);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public Bitmap convertViewToBitmap(View view){
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        /*view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);*/
        return bitmap;
    }

}
