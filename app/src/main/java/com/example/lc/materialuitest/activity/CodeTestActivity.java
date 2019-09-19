package com.example.lc.materialuitest.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.NormalInputDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeTestActivity extends AppCompatActivity {

    @BindView(R.id.btn_code_qr)
    Button QRCodeBtn;
    @BindView(R.id.btn_code_bar)
    Button BarCodeBtn;
    @BindView(R.id.iv_code_test)
    ImageView ivCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_test);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_code_bar, R.id.btn_code_qr})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_code_bar:

                break;
            case R.id.btn_code_qr:
                showDialog();
                break;
        }
    }

    private void showDialog(){
        NormalInputDialog dialog = new NormalInputDialog(this);
        dialog.setOnSureBtnClickListener(new NormalInputDialog.OnSureBtnClickListener() {
            @Override
            public void onSureBtnClick(NormalInputDialog dialog, String message) {
                Snackbar.make(QRCodeBtn, message, Snackbar.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.setOnCancelBtnClickListener(new NormalInputDialog.OnCancelBtnClickListener() {
            @Override
            public void onCancelBtnClick(NormalInputDialog dialog) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
