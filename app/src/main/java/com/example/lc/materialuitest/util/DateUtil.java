package com.example.lc.materialuitest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static long dateToStamp(String time) throws ParseException{
        SimpleDateFormat sdr = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA);
        Date date = sdr.parse(time);
        return date.getTime();
    }

    public static String stampToDate(long lt){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT, Locale.CHINA);
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        return res;
    }

}
