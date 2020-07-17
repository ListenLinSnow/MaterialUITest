package com.example.lc.materialuitest.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.BluetoothDeviceAdapter;
import com.example.lc.materialuitest.service.MyBluetoothService;
import com.example.lc.materialuitest.thread.ClientThread;
import com.example.lc.materialuitest.thread.ServerThread;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClassicBluetoothActivity extends AppCompatActivity implements BluetoothDeviceAdapter.OnItemClickListener {

    @BindView(R.id.tb_classic_bluetooth)
    Toolbar toolbar;
    @BindView(R.id.tv_bluetooth_info)
    TextView tvInfo;
    @BindView(R.id.rv_bluetooth)
    RecyclerView rvBluetooth;
    @BindView(R.id.et_bluetooth_content)
    EditText etContent;
    @BindView(R.id.btn_bluetooth_send)
    Button btnSend;

    private BluetoothAdapter bluetoothAdapter;

    private List<BluetoothDevice> deviceList = null;
    private BluetoothDeviceAdapter adapter = null;

    private ServerThread serverThread = null;
    private ClientThread clientThread = null;

    private BluetoothReceiver receiver = null;

    private static final int OPEN_BLUETOOTH = 1;

    private static final int CONNECT_TO_SERVER = 1;

    private static final String UUID = "00001101-1231-1000-8000-00805F9B34FB";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_bluetooth);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        init();
    }

    private void init(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            showToast("该设备不支持蓝牙");
        }else {
            tvInfo.setText("当前设备名称:" + bluetoothAdapter.getName() + ";当前设备地址:" + getMacAddr());
        }

        deviceList = new ArrayList<>();
        adapter = new BluetoothDeviceAdapter(this, deviceList);
        adapter.setOnItemClickListener(this);
        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        rvBluetooth.setItemAnimator(new DefaultItemAnimator());
        rvBluetooth.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvBluetooth.setAdapter(adapter);

        receiver = new BluetoothReceiver();

        registerReceiver(receiver, getFilter());

        if (bluetoothAdapter.isEnabled()){
            getBondedDevices();
            if (serverThread != null){
                serverThread.cancel();
            }
            serverThread = new ServerThread(bluetoothAdapter, handler);
            new Thread(serverThread).start();
        }
    }

    public String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
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
        adapter.notifyDataSetChanged();
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
                //蓝牙设备可见
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                enableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(enableIntent);
                break;
            case R.id.action_find_devices:
                //搜索其他设备
                deviceList.clear();
                if (bluetoothAdapter.disable()){
                    bluetoothAdapter.enable();
                }
                if (bluetoothAdapter.isDiscovering()){
                    bluetoothAdapter.cancelDiscovery();
                }
                bluetoothAdapter.startDiscovery();
                break;
            case R.id.action_device_disconnect:
                //断开蓝牙连接
                if (bluetoothAdapter.enable()) {
                    bluetoothAdapter.disable();
                }
                deviceList.clear();
                adapter.notifyDataSetChanged();
                showToast("蓝牙已关闭");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        if (serverThread != null){
            serverThread.cancel();
            serverThread = null;
        }
        BluetoothDevice device = deviceList.get(position);
        clientThread = new ClientThread(bluetoothAdapter, device, handler);
        new Thread(clientThread).start();

        Log.d("JsonList", "连接设备:" + device.getName());
    }

    @OnClick({R.id.btn_bluetooth_send})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_bluetooth_send:
                writeData(etContent.getText().toString());
                break;
        }
    }

    private void writeData(String dataSend){
        if (serverThread != null){
            serverThread.write(dataSend);
        } else if (clientThread != null){
            clientThread.write(dataSend);
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
                    if (!deviceList.contains(device)) {
                        deviceList.add(device);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:

                    break;
            }
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    showToast("发送内容:" + (String) msg.obj);
                    break;
                case 3:
                    showToast("接收内容:" + (String) msg.obj);
                    break;
                case 4:
                    etContent.setText("成功连接。。。");
                    break;
                case 5:
                    etContent.setText("成功连接。。。");
                    break;
            }
        }
    };

}
