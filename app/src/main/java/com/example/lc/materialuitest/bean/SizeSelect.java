package com.example.lc.materialuitest.bean;

import com.example.lc.materialuitest.R;

import java.util.ArrayList;
import java.util.List;

public enum SizeSelect {

    LITTLE(R.dimen.tv_six, "微小"),
    SMALL(R.dimen.tv_ten, "小"),
    NORMAL(R.dimen.tv_fourteen, "中等"),
    BIG(R.dimen.tv_eighteen, "大"),
    HUGE(R.dimen.tv_twenty_two, "巨大");

    int size;
    String desc;

    SizeSelect(int size, String desc) {
        this.size = size;
        this.desc = desc;
    }

    public int getSize() {
        return size;
    }

    public String getDesc() {
        return desc;
    }

    public static int getSizeByDesc(String desc){
        for (SizeSelect sizeSelect : values()){
            if(desc.equals(sizeSelect.desc)){
                return sizeSelect.size;
            }
        }
        return -1;
    }

    public static List<String> getAllSize(){
        List<String> sizeList = new ArrayList<>();
        for (SizeSelect sizeSelect : values()){
            sizeList.add(sizeSelect.desc);
        }
        return sizeList;
    }

}
