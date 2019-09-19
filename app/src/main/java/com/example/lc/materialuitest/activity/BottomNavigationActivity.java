package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.fragment.FirstFragment;
import com.example.lc.materialuitest.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.frame_bottom_navigation)
    FrameLayout frameLayout;
    @BindView(R.id.bnv_bottom_navigation)
    BottomNavigationView bottomNavigationView;

    int lastIndex = 0;

    FirstFragment firstFragment = null;
    SecondFragment secondFragment = null;

    List<Fragment> fragmentList = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(firstFragment);
        fragmentList.add(secondFragment);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_bottom_navigation, firstFragment)
                .show(firstFragment)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_read:
                if (lastIndex != 0){
                    switchFragment(lastIndex, 0);
                    lastIndex = 0;
                }
                return true;
            case R.id.action_code:
                if (lastIndex != 1){
                    switchFragment(lastIndex, 1);
                    lastIndex = 1;
                }
                return true;
            case R.id.action_game:

                return true;
            case R.id.action_write:

                return true;
        }
        return false;
    }

    private void switchFragment(int lastIndex, int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragmentList.get(lastIndex));
        if (!fragmentList.get(index).isAdded()){
            transaction.add(R.id.frame_bottom_navigation, fragmentList.get(index));
        }
        transaction.show(fragmentList.get(index)).commitAllowingStateLoss();
    }

}
