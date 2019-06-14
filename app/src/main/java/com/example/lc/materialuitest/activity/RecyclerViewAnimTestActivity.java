package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.RvAnimTestAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAnimTestActivity extends AppCompatActivity {

    @BindView(R.id.rv_recycler_view_anim_test)
    RecyclerView rvAnimTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_anim_test);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        rvAnimTest.setLayoutManager(new LinearLayoutManager(this));
        RvAnimTestAdapter adapter = new RvAnimTestAdapter(this);
        rvAnimTest.setAdapter(adapter);
    }

}
