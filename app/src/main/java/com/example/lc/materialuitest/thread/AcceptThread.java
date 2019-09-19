package com.example.lc.materialuitest.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.util.UUID;

public class AcceptThread extends Thread {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothServerSocket serverSocket;

    public AcceptThread(Context context, BluetoothAdapter bluetoothAdapter){
        this.bluetoothAdapter = bluetoothAdapter;

        BluetoothServerSocket tmpSocket = null;
        try {
            tmpSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(bluetoothAdapter.getName(), UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        }catch (Exception e){
            e.printStackTrace();
        }
        serverSocket = tmpSocket;
    }

    @Override
    public void run() {
        BluetoothSocket socket = null;
        while (true){
            try {
                socket = serverSocket.accept();
            }catch (Exception e){
                e.printStackTrace();
            }

            if (socket != null){

                BluetoothDevice remoteDevice = socket.getRemoteDevice();
                Log.d("JsonList", "已连接设备:" + remoteDevice.getName() + ";" + remoteDevice.getAddress());

                try {
                    serverSocket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void cancel(){
        try {
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
