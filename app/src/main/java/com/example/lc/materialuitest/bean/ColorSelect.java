package com.example.lc.materialuitest.bean;

import com.example.lc.materialuitest.R;

import java.util.ArrayList;
import java.util.List;

public enum ColorSelect {

    WHITE(R.color.md_white, "白色"),
    BLACK(R.color.md_black, "黑色"),
    RED(R.color.md_red_500, "红色"),
    ORANGE(R.color.md_orange_500, "橙色"),
    YELLOW(R.color.md_yellow_500, "黄色"),
    GREEN(R.color.md_green_500, "绿色"),
    BLUE(R.color.md_blue_500, "蓝色"),
    CYAN(R.color.md_cyan_500, "青色"),
    PURPLE(R.color.md_purple_500, "紫色");

    int color;
    String desc;

    ColorSelect(int color, String desc) {
        this.color = color;
        this.desc = desc;
    }

    public int getColor() {
        return color;
    }

    public String getDesc() {
        return desc;
    }

    public static int getColorByDesc(String desc){
        for (ColorSelect colorSelect : values()){
            if(colorSelect.desc.equals(desc)){
                return colorSelect.color;
            }
        }
        return -1;
    }

    /**
     * 判断颜色是否相符
     * @param color
     * @param colorSelect
     * @return
     */
    public static boolean existColor(int color, ColorSelect colorSelect){
        if(color == colorSelect.color){
            return true;
        }else {
            return false;
        }
    }

    public static List<String> getAllColor(){
        List<String> colorList = new ArrayList<>();
        for (ColorSelect colorSelect : values()){
            colorList.add(colorSelect.desc);
        }
        return colorList;
    }

}
