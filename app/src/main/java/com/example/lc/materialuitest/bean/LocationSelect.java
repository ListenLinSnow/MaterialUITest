package com.example.lc.materialuitest.bean;

import android.view.Gravity;

import java.util.ArrayList;
import java.util.List;

public enum LocationSelect {

    LEFT_BOTTOM(Gravity.LEFT | Gravity.BOTTOM, "左下"),
    LEFT_TOP(Gravity.LEFT | Gravity.TOP, "左上"),
    RIGHT_TOP(Gravity.RIGHT | Gravity.TOP, "右上"),
    RIGHT_BOTTOM(Gravity.RIGHT | Gravity.BOTTOM, "右下"),
    CENTER(Gravity.CENTER, "中心"),
    CENTER_TOP(Gravity.CENTER | Gravity.TOP, "中上"),
    CENTER_BOTTOM(Gravity.CENTER | Gravity.BOTTOM, "中下");

    int location;
    String desc;

    LocationSelect(int location, String desc) {
        this.location = location;
        this.desc = desc;
    }

    public int getLocation() {
        return location;
    }

    public String getDesc() {
        return desc;
    }

    public static int getLocationByDesc(String desc){
        for (LocationSelect locationSelect : values()) {
            if (desc.equals(locationSelect.desc)){
                return locationSelect.location;
            }
        }
        return -1;
    }

    public static List<String> getAllLocation(){
        List<String> locationList = new ArrayList<>();
        for (LocationSelect locationSelect : values()){
            locationList.add(locationSelect.getDesc());
        }
        return locationList;
    }

}
