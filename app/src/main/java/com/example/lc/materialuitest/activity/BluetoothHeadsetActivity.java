package com.example.lc.materialuitest.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.BluetoothDeviceAdapter;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BluetoothHeadsetActivity extends AppCompatActivity implements BluetoothDeviceAdapter.OnItemClickListener {

    @BindView(R.id.tb_bh)
    Toolbar toolbar;
    @BindView(R.id.tv_bh_info)
    TextView tvInfo;
    @BindView(R.id.rv_bluetooth_headset)
    RecyclerView rvBh;
    @BindView(R.id.btn_bh_file)
    Button btnFile;

    private List<BluetoothDevice> deviceList = null;
    private BluetoothDeviceAdapter bluetoothDeviceAdapter = null;

    private BluetoothAdapter bluetoothAdapter = null;

    private BluetoothReceiver receiver = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_headset);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            showToast("该设备不支持蓝牙");
        } else {
            tvInfo.setText("当前设备名称:" + bluetoothAdapter.getName() + ";当前设备地址:" + getMacAddr());
        }

        setSupportActionBar(toolbar);
        rvBh.setLayoutManager(new LinearLayoutManager(this));
        rvBh.setItemAnimator(new DefaultItemAnimator());
        rvBh.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        deviceList = new ArrayList<>();
        bluetoothDeviceAdapter = new BluetoothDeviceAdapter(this, deviceList);
        bluetoothDeviceAdapter.setOnItemClickListener(this);
        rvBh.setAdapter(bluetoothDeviceAdapter);

        receiver = new BluetoothReceiver();
        registerReceiver(receiver, getFilter());

        bluetoothAdapter.enable();

        if (bluetoothAdapter.isEnabled()){
            getBondedDevices();
        }
    }

    private String getMacAddr(){
        try {
            List<NetworkInterface> list = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface networkInterface : list){
                if (!networkInterface.getName().equalsIgnoreCase("wlan0"))
                    continue;

                byte[] macBytes = networkInterface.getHardwareAddress();
                if (macBytes == null){
                    return "";
                }

                StringBuilder res = new StringBuilder();
                for (byte b : macBytes){
                    res.append(String.format("%02X:", b));
                }

                if (res.length() > 0){
                    res.deleteCharAt(res.length() - 1);
                }
                return res.toString();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 蓝牙广播过滤器
     * @return
     */
    private IntentFilter getFilter(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        return filter;
    }

    private void getBondedDevices(){
        deviceList.clear();
        Set<BluetoothDevice> tmp = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : tmp){
            if (!deviceList.contains(device)){
                deviceList.add(device);
            }
        }
        bluetoothDeviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position) {

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

                break;
            case R.id.action_find_devices:
                if (bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                break;
            case R.id.action_device_disconnect:
                if (bluetoothAdapter.isEnabled()){
                    bluetoothAdapter.disable();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBluetooth(){
        if (bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private class BluetoothReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            BluetoothDevice device = null;
            switch (intent.getAction()){
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    showToast("开始搜索");
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    showToast("搜索结束");
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (!deviceList.contains(device)){
                        deviceList.add(device);
                    }
                    bluetoothDeviceAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

}
