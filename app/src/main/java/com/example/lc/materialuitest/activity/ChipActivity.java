package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChipActivity extends AppCompatActivity {

    @BindView(R.id.chip_chip_single)
    Chip singleChip;
    @BindView(R.id.chip_chip_filter)
    Chip filterChip;
    @BindView(R.id.chip_chip_entry_chip)
    Chip entryChip;
    @BindView(R.id.chip_chip_choice_chip)
    Chip choiceChip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        filterChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String res = "";
                if(isChecked){
                    res = "被选中了";
                }else {
                    res = "未被选中";
                }
                Toast.makeText(ChipActivity.this, res, Toast.LENGTH_SHORT).show();
            }
        });
        entryChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String res = "";
                if(isChecked){
                    res = "被选中了";
                }else {
                    res = "未被选中";
                }
                Toast.makeText(ChipActivity.this, res, Toast.LENGTH_SHORT).show();
            }
        });
        choiceChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String res = "";
                if(isChecked){
                    res = "被选中了";
                }else {
                    res = "未被选中";
                }
                Toast.makeText(ChipActivity.this, res, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
