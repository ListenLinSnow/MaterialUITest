package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.SelectedRvAdapter;
import com.example.lc.materialuitest.adapter.SortRvAdapter;
import com.example.lc.materialuitest.adapter.SwitchRvAdapter;
import com.example.lc.materialuitest.bean.SingleItem;
import com.example.lc.materialuitest.callback.ItemTouchHelperCallback;
import com.example.lc.materialuitest.view.CustomVerticalDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.rv_list_selected)
    RecyclerView rvSelected;
    @BindView(R.id.rv_list_switch)
    RecyclerView rvSwitch;
    @BindView(R.id.rv_list_sort)
    RecyclerView rvSort;
    @BindView(R.id.rv_list_slide)
    RecyclerView rvSlide;

    private SelectedRvAdapter selectedRvAdapter;
    private List<SingleItem> selectedList;

    private SwitchRvAdapter switchRvAdapter;
    private List<SingleItem> switchList;
    
    private SortRvAdapter sortRvAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        selectedList = new ArrayList<>();
        selectedList.add(new SingleItem(R.mipmap.game, "游戏"));
        selectedList.add(new SingleItem(R.mipmap.read, "阅读"));
        selectedList.add(new SingleItem(R.mipmap.code, "代码"));

        rvSelected.setLayoutManager(new LinearLayoutManager(this));
        rvSelected.setItemAnimator(new DefaultItemAnimator());
        rvSelected.addItemDecoration(new CustomVerticalDecoration(ContextCompat.getDrawable(this, R.drawable.item_divider)));
        selectedRvAdapter = new SelectedRvAdapter(this, selectedList);
        rvSelected.setAdapter(selectedRvAdapter);

        switchList = new ArrayList<>();
        switchList.add(new SingleItem(R.mipmap.wifi, "WiFi"));
        switchList.add(new SingleItem(R.mipmap.bluetooth, "蓝牙"));
        switchList.add(new SingleItem(R.mipmap.nfc, "NFC"));

        rvSwitch.setLayoutManager(new LinearLayoutManager(this));
        rvSwitch.setItemAnimator(new DefaultItemAnimator());
        rvSwitch.addItemDecoration(new CustomVerticalDecoration(ContextCompat.getDrawable(this, R.drawable.item_divider)));
        switchRvAdapter = new SwitchRvAdapter(this, switchList);
        rvSwitch.setAdapter(switchRvAdapter);

        rvSort.setLayoutManager(new LinearLayoutManager(this));
        rvSort.setItemAnimator(new DefaultItemAnimator());
        rvSort.addItemDecoration(new CustomVerticalDecoration(ContextCompat.getDrawable(this, R.drawable.item_divider)));
        sortRvAdapter = new SortRvAdapter(this, selectedList);
        rvSort.setAdapter(sortRvAdapter);

        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(sortRvAdapter);
        itemTouchHelperCallback.setDragEnable(true);
        itemTouchHelperCallback.setSwipeEnable(true);
        ItemTouchHelper helper = new ItemTouchHelper(itemTouchHelperCallback);
        helper.attachToRecyclerView(rvSort);

        rvSlide.setLayoutManager(new LinearLayoutManager(this));
        rvSlide.setItemAnimator(new DefaultItemAnimator());
        rvSlide.addItemDecoration(new CustomVerticalDecoration(ContextCompat.getDrawable(this, R.drawable.item_divider)));



    }

}
