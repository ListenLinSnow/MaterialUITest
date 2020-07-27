package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.customTable.CustomTable;
import com.example.lc.materialuitest.view.customTable.XRecyclerView;
import com.rmondjone.locktableview.LockTableView;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockTableActivity extends AppCompatActivity {

    @BindView(R.id.ll_lock_table)
    LinearLayout llLockTable;

    String[] titles = {"名字", "性别", "生日", "最爱", "很爱", "喜欢", "一般", "讨厌", "个人爱好"};

    String[][] npcs = {{"珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"},
            {"玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"},
            {"格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"},
            {"多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"},
            {"艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"}};

    ArrayList<ArrayList<String>> dataList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_table);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        dataList = new ArrayList<>();

        ArrayList<String> tempList = new ArrayList<>();

        for (String title: titles){
            tempList.add(title);
        }
        dataList.add(tempList);

        for (int i = 0; i < 5; i++) {
            for (String[] npc : npcs) {
                tempList = new ArrayList<>();
                for (String info : npc) {
                    tempList.add(info);
                }
                dataList.add(tempList);
            }
        }

        final CustomTable customTable = new CustomTable(this, llLockTable, dataList);
        customTable.setLockFirstColumn(true)
                .setLockFirstColumn(true)
                .setMinColumnWidth(60)
                .setMinRowHeight(20)
                .setTextViewSize(16)
                .setCellPadding(15)
                .setFirstRowBackgroundColor(R.color.md_blue_200)
                .setNullableString("N/A")
                .setOnItemSelector(R.color.md_red_200)
                .show();
        /*LockTableView lockTableView = new LockTableView(this, llLockTable, dataList);
        lockTableView.setLockFristColumn(true)
                .setLockFristColumn(true)
                .setMinColumnWidth(60)
                .setMinRowHeight(20)
                .setTextViewSize(16)
                .setCellPadding(15)
                .setFristRowBackGroudColor(R.color.md_blue_200)
                .setNullableString("N/A")
                .setOnItemSeletor(R.color.md_red_200)
                .show();*/
    }

}
