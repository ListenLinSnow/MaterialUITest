package com.example.lc.materialuitest.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.util.UUID;

public class ConnectThread extends Thread {

    private final BluetoothSocket socket;
    private final BluetoothAdapter bluetoothAdapter;
    private final BluetoothDevice device;

    public ConnectThread(Context context, BluetoothAdapter bluetoothAdapter, BluetoothDevice device) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.device = device;

        BluetoothSocket tmpSocket = null;
        try {
            tmpSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        }catch (Exception e){
            e.printStackTrace();
        }
        socket = tmpSocket;
    }

    @Override
    public void run() {
        bluetoothAdapter.cancelDiscovery();

        if (bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }

        try {
            socket.connect();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void cancel(){
        try {
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
