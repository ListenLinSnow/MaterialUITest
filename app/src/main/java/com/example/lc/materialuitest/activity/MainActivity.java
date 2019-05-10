package com.example.lc.materialuitest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.SingleTextAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SingleTextAdapter.OnInfoClickListener {

    @BindView(R.id.rv_main)
    RecyclerView recyclerView;

    List<String> infoList;
    List<Class> classList;

    SingleTextAdapter singleTextAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        initList();

        singleTextAdapter = new SingleTextAdapter(this, infoList);
        singleTextAdapter.setOnInfoClickListener(this);
        recyclerView.setAdapter(singleTextAdapter);
    }

    private void initList(){
        infoList = new ArrayList<>();
        classList = new ArrayList<>();

        infoList.add("BottomNavigationView");
        classList.add(BottomNavigationActivity.class);

        infoList.add("CardView");
        classList.add(CardViewActivity.class);

        infoList.add("Buttons");
        classList.add(ButtonsActivity.class);

        infoList.add("FloatingActionButton");
        classList.add(FloatingButtonActivity.class);
    }

    /**
     * recyclerView的点击事件
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        startActivity(new Intent(MainActivity.this, classList.get(position)));
    }
    
}
