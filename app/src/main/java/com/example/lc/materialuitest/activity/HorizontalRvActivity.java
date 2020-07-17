package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.MultipleHorizontalRvAdapter;
import com.example.lc.materialuitest.adapter.SingleHorizontalRvAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HorizontalRvActivity extends AppCompatActivity {

    @BindView(R.id.rv_horizontal_rv_single)
    RecyclerView recyclerViewSingle;
    @BindView(R.id.tv_horizontal_rv_multiple_title)
    TextView tvMultiTitle;
    @BindView(R.id.rv_horizontal_rv_multiple_title)
    RecyclerView rvTitle;
    @BindView(R.id.rv_horizontal_rv_multiple)
    RecyclerView recyclerViewMultiple;

    String[] titles = {"性别", "生日", "最爱", "很爱", "喜欢", "普通", "讨厌", "个人特点"};

    String[][] npcs = {{"珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"},
                        {"玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"},
                        {"格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"},
                        {"多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"},
                        {"艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"}};

    List<String> infoList = null;
    List<String> titleList = null;
    List<List<String>> npcList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_rv);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        infoList = new ArrayList<>();
        infoList.add("里克与卡莲");
        infoList.add("凯与珀布莉");
        infoList.add("克里夫与兰");
        infoList.add("格雷与玛丽");
        infoList.add("多特与艾丽");
        infoList.add("珍妮弗");
        infoList.add("布兰登");
        infoList.add("霍安");

        SingleHorizontalRvAdapter adapter = new SingleHorizontalRvAdapter(this, infoList);
        recyclerViewSingle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSingle.setAdapter(adapter);

        titleList = new ArrayList<>();
        for (String info: titles){
            titleList.add(info);
        }
        SingleHorizontalRvAdapter adapter1 = new SingleHorizontalRvAdapter(this, titleList);
        rvTitle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTitle.setAdapter(adapter1);

        npcList = new ArrayList<>();
        List<String> tempList = null;
        for (int i = 0; i < 4; i++) {
            for (String[] npc : npcs) {
                tempList = new ArrayList<>();
                for (String info : npc) {
                    tempList.add(info);
                }
                npcList.add(tempList);
            }
        }

        MultipleHorizontalRvAdapter mAdapter = new MultipleHorizontalRvAdapter(this, npcList);
        recyclerViewMultiple.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMultiple.setAdapter(mAdapter);
    }

}
