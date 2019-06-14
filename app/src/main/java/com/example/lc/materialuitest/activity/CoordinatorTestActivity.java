package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.RvAnimTestAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoordinatorTestActivity extends AppCompatActivity {

    @BindView(R.id.cl_coordinator_layout_test)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.rv_cl_test)
    RecyclerView rvCl;
    @BindView(R.id.fab_cl_test)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout_test);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        rvCl.setLayoutManager(new LinearLayoutManager(this));
        RvAnimTestAdapter adapter = new RvAnimTestAdapter(this);
        rvCl.setAdapter(adapter);
    }

    @OnClick({R.id.fab_cl_test})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fab_cl_test:
                Snackbar.make(coordinatorLayout, "Test", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

}
