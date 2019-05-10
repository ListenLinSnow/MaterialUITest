package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomNavigationActivity extends AppCompatActivity {

    @BindView(R.id.tv_bottom_navigation)
    TextView textView;
    @BindView(R.id.bnv_bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                textView.setText(menuItem.getTitle().toString());
                return true;
            }
        });
        bottomNavigationView.findViewById(R.id.action_read).performClick();
    }

}
