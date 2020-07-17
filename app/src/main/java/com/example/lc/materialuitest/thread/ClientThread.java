package com.example.lc.materialuitest.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ClientThread extends Thread {

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice device;

    Handler uiHandler;

    BluetoothSocket socket;
    OutputStream out;
    InputStream in;

    public ClientThread(BluetoothAdapter bluetoothAdapter, BluetoothDevice device,
                        Handler handler) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.device = device;
        this.uiHandler = handler;
        BluetoothSocket tmp = null;
        try {
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-1231-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket = tmp;
    }

    @Override
    public void run() {

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        try {
            socket.connect();
            Log.d("JsonList", "连接成功");
            uiHandler.sendEmptyMessage(4);
            out = socket.getOutputStream();
            in = socket.getInputStream();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int len;
                    String content;
                    try {
                        while ((len=in.read(buffer)) != -1) {
                            content=new String(buffer, 0, len);
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = content;
                            uiHandler.sendMessage(message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            Log.d("JsonList", "连接失败");
            e.printStackTrace();
        }
    }


    public void write(String data){
        try {
            out.write(data.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
