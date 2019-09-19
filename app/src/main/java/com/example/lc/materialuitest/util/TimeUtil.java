package com.example.lc.materialuitest.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 参数中携带当前时间
     * @param currentTime
     * @param timeFormat
     * @return
     */
    public static String getTimeByFormat(long currentTime, String timeFormat){
        Date date = new Date(currentTime);
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        String time = format.format(date);

        Calendar calendar = null;
        if(timeFormat.contains("hh")){
            calendar = Calendar.getInstance();
            int apm = calendar.get(Calendar.AM_PM);
            if(apm == 0){
                //包含“年”字,说明是中文
                if(time.contains("年")) {
                    time = time.replace(" ", " 上午 ");
                }else{
                    time = time.replace(" ", " am ");
                }
            }else {
                if(time.contains("年")) {
                    time = time.replace(" ", " 下午 ");
                }else {
                    time = time.replace(" ", " pm ");
                }
            }
        }
        return time;
    }

    /**
     * 参数中不携带当前时间
     * @param timeFormat
     * @return
     */
    public static String getTimeByFormatWithoutTime(String timeFormat){
        String time = getTimeByFormat(System.currentTimeMillis(), timeFormat);
        return time;
    }

    public static long stringToLong(String strTime, String formatType) {
        try {
            Date date = stringToDate(strTime, formatType); // String类型转成date类型
            if (date == null) {
                return 0;
            } else {
                long currentTime = dateToLong(date); // date类型转成long类型
                return currentTime;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }

}
