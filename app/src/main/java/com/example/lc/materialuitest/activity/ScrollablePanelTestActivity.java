package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.TestPanelAdapter;
import com.kelin.scrollablepanel.library.ScrollablePanel;
//import com.example.lc.materialuitest.adapter.TestPanelAdapter;
//import com.example.lc.materialuitest.view.ScrollablePanel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScrollablePanelTestActivity extends AppCompatActivity {

    @BindView(R.id.sp_scrollable_panel)
    ScrollablePanel scrollablePanel;

    String[] titles = {"名字", "性别", "生日", "最爱", "很爱", "喜欢", "一般", "讨厌", "个人爱好"};

    String[][] npcs = {{"珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"},
            {"玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"},
            {"格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"},
            {"多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"},
            {"艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"}};

    List<List<String>> npcList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollable_panel);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        npcList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();

        for (String title: titles){
            tempList.add(title);
        }
        npcList.add(tempList);

        for (int i = 0; i < 7; i++) {
            for (String[] npc : npcs) {
                tempList = new ArrayList<>();
                for (String info : npc) {
                    tempList.add(info);
                }
                npcList.add(tempList);
            }
        }

        TestPanelAdapter testPanelAdapter = new TestPanelAdapter(npcList, this);
        scrollablePanel.setPanelAdapter(testPanelAdapter);
    }

}
