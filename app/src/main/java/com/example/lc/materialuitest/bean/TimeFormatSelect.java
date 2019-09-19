package com.example.lc.materialuitest.bean;

import android.support.annotation.StringDef;
import android.util.Log;

import com.example.lc.materialuitest.util.TimeUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public enum  TimeFormatSelect {

    FORMAT_STANDARD("yyyy-MM-dd HH:mm:ss"),
    FORMAT_NORMAL("yyyy年MM月dd日 HH:mm:ss"),
    FORMAT_12_HOUR_EN("yyyy-MM-dd hh:mm:ss"),
    FORMAT_12_HOUR_CH("yyyy年MM月dd日 hh:mm:ss");

    String desc;

    TimeFormatSelect(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByPosition(int position){
        int index = 0;
        for (TimeFormatSelect timeFormatSelect : values()){
            if(index == position){
                return timeFormatSelect.getDesc();
            }
            index ++;
        }
        return null;
    }

    /**
     * 获取当前时间下的所有显示格式
     * @return
     */
    public static List<String> getAllFormat(){
        long currentTime = System.currentTimeMillis();
        String time = null;

        List<String> timeList = new ArrayList<>();
        for (TimeFormatSelect timeFormatSelect : values()){
            time = TimeUtil.getTimeByFormat(currentTime, timeFormatSelect.getDesc());
            timeList.add(time);
        }
        return timeList;
    }
    
}
