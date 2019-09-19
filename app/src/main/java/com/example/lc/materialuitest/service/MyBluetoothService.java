package com.example.lc.materialuitest.service;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.InputStream;
import java.io.OutputStream;

public class MyBluetoothService {

    private Handler handler;

    private interface MessageConstants{
        int MESSAGE_READ = 0;
        int MESSAGE_WRITE = 1;
        int MESSAGE_TOAST = 2;
    }

    private class ConnectedThread extends Thread{

        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;
        private byte[] buffer;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                tmpOut = socket.getOutputStream();
            }catch (Exception e){
                e.printStackTrace();
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        @Override
        public void run() {
            buffer = new byte[1024];

            int numBytes;

            while (true){
                try {
                    numBytes = inputStream.read(buffer);
                    Message readMsg = handler.obtainMessage(MessageConstants.MESSAGE_READ, numBytes, -1, buffer);
                    readMsg.sendToTarget();
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
                Message writtenMsg = handler.obtainMessage(MessageConstants.MESSAGE_WRITE, -1, -1, buffer);
                writtenMsg.sendToTarget();
            }catch (Exception e){
                e.printStackTrace();

                Message writeErrorMsg = handler.obtainMessage(MessageConstants.MESSAGE_TOAST);
                Bundle bundle = new Bundle();
                bundle.putString("toast", "Couldn't send data to the other device");
                writeErrorMsg.setData(bundle);
                handler.sendMessage(writeErrorMsg);
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

}
