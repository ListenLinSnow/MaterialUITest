package com.example.lc.materialuitest;

import android.app.Application;
import android.os.Looper;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        QbSdk.initX5Environment(getApplicationContext(), null);
    }

}
