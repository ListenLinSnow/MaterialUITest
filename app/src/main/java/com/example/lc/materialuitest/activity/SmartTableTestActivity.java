package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.listener.OnColumnClickListener;
import com.example.lc.materialuitest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SmartTableTestActivity extends AppCompatActivity {

    @BindView(R.id.st_smart_table)
    com.bin.david.form.core.SmartTable smartTable;

    @SmartTable(name = "重聚矿石镇 NPC")
    public class Npc {
        @SmartColumn(id = 1, name = "名字", fixed = true)
        private String name;
        @SmartColumn(id = 2, name = "性别")
        private String sex;
        @SmartColumn(id = 3, name = "生日")
        private String birth;
        @SmartColumn(id = 4, name = "最爱")
        private String lovest;
        @SmartColumn(id = 5, name = "很爱")
        private String lover;
        @SmartColumn(id = 6, name = "喜欢")
        private String love;
        @SmartColumn(id = 7, name = "一般")
        private String normal;
        @SmartColumn(id = 8, name = "讨厌")
        private String dislike;
        @SmartColumn(id = 9, name = "个人爱好")
        private String person;

        public Npc(String name, String sex, String birth, String lovest, String lover, String love, String normal, String dislike, String person) {
            this.name = name;
            this.sex = sex;
            this.birth = birth;
            this.lovest = lovest;
            this.lover = lover;
            this.love = love;
            this.normal = normal;
            this.dislike = dislike;
            this.person = person;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_table);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        /*Column<String> nameColumn = new Column<String>("名称", "name");
        Column<String> oriPriceColumn = new Column<String>("原价", "oriPrice");
        Column<String> nowPriceColumn = new Column<String>("现价", "nowPrice");
        Column<String> discountColumn = new Column<String>("折扣", "discount");
        Column<String> saleDateColumn = new Column<String>("上市日期", "saleDate");

        int size = DensityUtils.dp2px(this, 16);
        Column<Boolean> isLowestColumn = new Column<Boolean>("达到史低价格", "isLowest", new ImageResDrawFormat<Boolean>(size, size) {
            @Override
            protected Context getContext() {
                return SmartTableTestActivity.this;
            }

            @Override
            protected int getResourceID(Boolean isLowest, String value, int position) {
                if (isLowest){
                    return R.mipmap.pick_right;
                }
                return 0;
            }
        });*/

        List<Npc> gameList = new ArrayList<>();
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        gameList.add(new Npc("珀布莉", "女", "夏3", "煎鸡蛋", "草莓", "凤梨", "胡萝卜", "芜菁", "珀布莉最喜欢蛋类料理。如果有了平底锅的话，推荐用鸡蛋和油做「煎鸡蛋」。"));
        gameList.add(new Npc("玛丽", "女", "冬20", "蔬菜汁", "竹笋", "番茄", "芜菁", "炒饭", "玛丽最喜欢的东西是「蔬菜汁」和「休闲茶」。"));
        gameList.add(new Npc("格雷", "男", "冬6", "烤玉米", "超强体力药", "番茄", "黄瓜", "芜菁", "毕竟是锻造屋的见习工，格雷是很喜欢矿石的。"));
        gameList.add(new Npc("多特", "男", "秋19", "牛奶S", "蜂蜜", "芜菁", "黄瓜", "蛋糕", "多特最喜欢的东西是牛奶。不管是什么哪种牛奶效果都一样，所以送牛奶S是个不错的选择。"));
        gameList.add(new Npc("艾丽", "女", "春16", "赏月丸子", "牛奶S", "蓝莓", "芜菁", "青椒", "周三是医院的休息日也是艾丽的休息日。所以艾丽相关的事件大多在周三发生。"));
        smartTable.setData(gameList);
        smartTable.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {

            }
        });


        //设置背景色
        /*smartTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.row % 2 == 0) {
                    return getResources().getColor(R.color.md_blue_100);
                } else {
                    return 0;
                }
            }
        });
        //设置固定列
        nameColumn.setFixed(true);
        //设置缩放
        smartTable.setZoom(true);

        Column priceColumn = new Column("价格", oriPriceColumn, nowPriceColumn);
        TableData<Npc> tableData = new TableData<Npc>("Steam游戏打折情报", gameList, nameColumn, priceColumn, discountColumn, saleDateColumn, isLowestColumn);
        smartTable.setTableData(tableData);*/
    }

}
