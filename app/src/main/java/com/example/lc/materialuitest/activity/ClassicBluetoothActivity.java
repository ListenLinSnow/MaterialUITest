package com.example.lc.materialuitest.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.BluetoothDeviceAdapter;
import com.example.lc.materialuitest.service.MyBluetoothService;
import com.example.lc.materialuitest.thread.AcceptThread;
import com.example.lc.materialuitest.thread.ConnectThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassicBluetoothActivity extends AppCompatActivity implements BluetoothDeviceAdapter.OnItemClickListener {

    @BindView(R.id.tb_classic_bluetooth)
    Toolbar toolbar;
    @BindView(R.id.rv_bluetooth)
    RecyclerView rvBluetooth;

    AcceptThread acceptThread = null;
    ConnectThread connectThread = null;
    MyBluetoothService myBluetoothService = null;

    List<BluetoothDevice> deviceList = null;

    BluetoothAdapter bluetoothAdapter = null;
    BluetoothDeviceAdapter adapter = null;

    IntentFilter startFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
    IntentFilter finishedFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
    IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

    private static final int OPEN_BLUETOOTH = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_bluetooth);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        init();
    }

    private void init(){
        deviceList = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        rvBluetooth.setItemAnimator(new DefaultItemAnimator());
        adapter = new BluetoothDeviceAdapter(this, deviceList);
        adapter.setOnItemClickListener(this);
        rvBluetooth.setAdapter(adapter);

        openBluetooth();
        findLocalDevices();

        if (bluetoothAdapter.isEnabled()) {
            if (acceptThread != null){
                acceptThread.cancel();
            }
            acceptThread = new AcceptThread(this, bluetoothAdapter);
            new Thread(acceptThread).start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_classic_bluetooth_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bluetooth_visible:
                setDeviceVisible();
                break;
            case R.id.action_find_services:
                findLocalDevices();
                scanBluetooth();
                registerReceiver(receiver, startFilter);
                registerReceiver(receiver, finishedFilter);
                registerReceiver(receiver, foundFilter);
                break;
            case R.id.action_device_disconnect:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case OPEN_BLUETOOTH:
                if (resultCode == RESULT_OK){
                    findLocalDevices();
                }else if (requestCode == RESULT_CANCELED){
                    showToast("蓝牙开启失败");
                }
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        if (acceptThread != null){
            acceptThread.cancel();
            acceptThread = null;
        }

        connectThread = new ConnectThread(this, bluetoothAdapter, deviceList.get(position));
        new Thread(connectThread).start();
    }

    /**
     * 查找已配对过的设备集
     */
    private void findLocalDevices(){
        deviceList.clear();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        deviceList.addAll(pairedDevices);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设备是否支持蓝牙
     * @return
     */
    public boolean isSupportBluetooth(){
        return bluetoothAdapter != null;
    }

    /**
     * 蓝牙是否打开
     * @return
     */
    public boolean isBluetoothEnable(){
        return isSupportBluetooth() && bluetoothAdapter.isEnabled();
    }

    /**
     * 异步自动打开蓝牙（无弹窗提示）
     */
    public void openBluetoothAsync(){
        if (isSupportBluetooth()){
            bluetoothAdapter.enable();
        }
    }

    /**
     * 打开蓝牙的完整判断逻辑
     */
    private void openBluetooth(){
        if (isSupportBluetooth()){
            if (!isBluetoothEnable()){
                openBluetoothSync(this, OPEN_BLUETOOTH);
            } else {
                showToast("蓝牙已打开");
            }
        } else {
            showToast("设备不支持蓝牙");
        }
    }

    /**
     * 同步打开蓝牙（有弹窗提示）
     * @param activity
     * @param requestCode
     */
    public void openBluetoothSync(Activity activity, int requestCode){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 设置设备可见性(此处设置为300秒)
     */
    public void setDeviceVisible(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(intent);
    }

    /**
     * 扫描蓝牙
     * @return
     */
    public boolean scanBluetooth(){
        if (!isBluetoothEnable()){
            return false;
        }
        if (bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
        //此方法为异步操作，一般搜索12秒
        return bluetoothAdapter.startDiscovery();
    }

    /**
     * 取消扫描蓝牙
     * @return
     */
    public boolean cancelScanBlue(){
        if (isSupportBluetooth()){
            return bluetoothAdapter.cancelDiscovery();
        }
        return true;
    }

    private void showToast(String msg){
        Toast.makeText(ClassicBluetoothActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        acceptThread.cancel();
        connectThread.cancel();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                showToast("开始搜索");
            }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                showToast("搜索结束");
            }else if (BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (!deviceList.contains(device)) {
                    deviceList.add(device);
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

}
