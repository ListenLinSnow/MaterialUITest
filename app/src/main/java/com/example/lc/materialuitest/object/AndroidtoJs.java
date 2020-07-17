package com.example.lc.materialuitest.object;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class AndroidtoJs extends Object {

    @JavascriptInterface
    public void hello(String msg){
        System.out.println("js调用了Android的方法");
    }

}
