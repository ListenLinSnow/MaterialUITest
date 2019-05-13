package com.example.lc.materialuitest.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.lc.materialuitest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_dialog_test)
    Button testBtn;
    @BindView(R.id.btn_dialog_test2)
    Button test2Btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        testBtn.setOnClickListener(this);
        test2Btn.setOnClickListener(this);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_dialog_test:
                showAlertDialog();
                break;
            case R.id.btn_dialog_test2:
                showDialog();
                break;
        }
    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DialogActivity.this);
        builder.setTitle("警告");
        builder.setMessage("你确定要删除xxx.jpg吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showDialog(){
        View view = LayoutInflater.from(DialogActivity.this).inflate(R.layout.dialog_test, null);
        final Dialog dialog = new Dialog(DialogActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Button agreeBtn = view.findViewById(R.id.btn_dialog_agree);
        Button disagreeBtn = view.findViewById(R.id.btn_dialog_disagree);

        agreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        disagreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
