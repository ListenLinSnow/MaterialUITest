package com.example.lc.materialuitest.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.SingleTextAdapter;
import com.example.lc.materialuitest.util.CheckPermissionUtil;
import com.example.lc.materialuitest.view.CustomVerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SingleTextAdapter.OnInfoClickListener {

    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    List<String> infoList;
    List<Class> classList;

    SingleTextAdapter singleTextAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        init();
    }

    private void init(){
        //添加拍摄、存储、录音、定位权限
        List<String> permissionList = new ArrayList<>();
        CheckPermissionUtil.addPermissionsToList(permissionList, CheckPermissionUtil.PERMISSION_CAMERA);
        CheckPermissionUtil.addPermissionsToList(permissionList, CheckPermissionUtil.PERMISSION_MICROPHONE);
        CheckPermissionUtil.addPermissionsToList(permissionList, CheckPermissionUtil.PERMISSION_LOCATION);
        CheckPermissionUtil.addPermissionsToList(permissionList, CheckPermissionUtil.PERMISSION_STORAGE);
        CheckPermissionUtil.addPermissionsToList(permissionList, CheckPermissionUtil.PERMISSION_PHONE);
        CheckPermissionUtil.verifyPermissions(this, permissionList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new CustomVerticalDecoration(ContextCompat.getDrawable(this, R.drawable.item_divider)));

        initList();

        singleTextAdapter = new SingleTextAdapter(this, infoList);
        singleTextAdapter.setOnInfoClickListener(this);
        recyclerView.setAdapter(singleTextAdapter);
    }

    private void initList(){
        infoList = new ArrayList<>();
        classList = new ArrayList<>();

        infoList.add("BottomNavigationView");
        classList.add(BottomNavigationActivity.class);

        infoList.add("CardView");
        classList.add(CardViewActivity.class);

        infoList.add("Buttons");
        classList.add(ButtonsActivity.class);

        infoList.add("FloatingActionButton");
        classList.add(FloatingButtonActivity.class);

        infoList.add("ChipActivity");
        classList.add(ChipActivity.class);

        infoList.add("Dialog");
        classList.add(DialogActivity.class);

        infoList.add("ListView");
        classList.add(ListActivity.class);

        infoList.add("Picker");
        classList.add(PickerActivity.class);

        infoList.add("ProgressBar");
        classList.add(ProgressBarActivity.class);

        infoList.add("RecyclerView加载动画测试");
        classList.add(RecyclerViewAnimTestActivity.class);

        infoList.add("CoordinatorActivity测试");
        classList.add(CoordinatorTestActivity.class);

        infoList.add("二维码测试");
        classList.add(CodeTestActivity.class);

        infoList.add("拍照加水印测试");
        classList.add(Camera1TestActivity.class);

        infoList.add("拍视频水印测试");
        classList.add(RecordActivity.class);

        infoList.add("OpenGL开发测试");
        classList.add(OpenGLTestActivity.class);

        infoList.add("高德地图测试");
        classList.add(AMapTestActivity.class);

        infoList.add("绘画文字测试");
        classList.add(DrawTextActivity.class);

        infoList.add("文件URI测试");
        classList.add(FileURITestActivity.class);

        infoList.add("经典蓝牙开发测试");
        classList.add(ClassicBluetoothActivity.class);

        infoList.add("时间转long型");
        classList.add(TimeTranslateActivity.class);
    }

    /**
     * recyclerView的点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(MainActivity.this, classList.get(position)));
    }
    
}
