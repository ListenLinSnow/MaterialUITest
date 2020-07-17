package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MaterialTabActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout_material_tab)
    TabLayout tabLayout;

    private int currentIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_tab);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        tabLayout.addTab(tabLayout.newTab().setText("采样"));
        tabLayout.addTab(tabLayout.newTab().setText("接样"));
        tabLayout.addTab(tabLayout.newTab().setText("分析"));
        tabLayout.addTab(tabLayout.newTab().setText("报告编制"));
        tabLayout.addTab(tabLayout.newTab().setText("报告审核"));
        tabLayout.addTab(tabLayout.newTab().setText("报告签发"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (currentIndex > 0){
                    currentIndex--;
                    tabLayout.getTabAt(currentIndex).select();
                    Log.d("JsonList", "切换后的currentIndex:" + currentIndex);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (currentIndex < 5) {
                    currentIndex++;
                    tabLayout.getTabAt(currentIndex).select();
                    Log.d("JsonList", "切换后的currentIndex:" + currentIndex);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
