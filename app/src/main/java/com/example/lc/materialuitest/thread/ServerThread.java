package com.example.lc.materialuitest.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ServerThread extends Thread {

    BluetoothAdapter bluetoothAdapter;
    BluetoothServerSocket serverSocket =null;
    BluetoothSocket socket = null;
    Handler uiHandler;

    OutputStream out;
    InputStream in;

    boolean acceptFlag = true;

    public ServerThread(BluetoothAdapter bluetoothAdapter, Handler handler) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.uiHandler = handler;
        BluetoothServerSocket tmp = null;
        try {
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("QingMi", UUID.fromString("00001101-1231-1000-8000-00805F9B34FB"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        serverSocket = tmp;
    }

    @Override
    public void run() {
        try {
            while (acceptFlag) {
                socket = serverSocket.accept();
                // 阻塞，直到有客户端连接
                if (socket != null) {
                    uiHandler.sendEmptyMessage(5);

                    out = socket.getOutputStream();
                    in = socket.getInputStream();

                    // 读取服务器 socket 数据
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] buffer = new byte[1024];
                            int len;
                            String content;
                            try {
                                while ((len = in.read(buffer)) != -1) {
                                    content = new String(buffer, 0, len);

                                    Message message = Message.obtain();
                                    message.what = 3;
                                    message.obj = content;
                                    uiHandler.sendMessage(message);
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void write(String data){
        try {
            out.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        try {
            acceptFlag = false;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
