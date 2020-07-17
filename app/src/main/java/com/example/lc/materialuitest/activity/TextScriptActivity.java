package com.example.lc.materialuitest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TextScriptActivity extends AppCompatActivity {

    @BindView(R.id.tv_text_script_1)
    TextView tvScript1;
    @BindView(R.id.tv_text_script_2)
    TextView tvScript2;
    @BindView(R.id.tv_text_script_3)
    TextView tvScript3;
    @BindView(R.id.tv_text_script_4)
    TextView tvScript4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_script);
        ButterKnife.bind(this);

        init();
    }

    private void init(){
        SpannableString ss = new SpannableString("RM123.456");
        SuperscriptSpan superscriptSpan = new SuperscriptSpan();
        ss.setSpan(superscriptSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(18, true);
        ss.setSpan(sizeSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        tvScript1.setText(ss);

        ss = new SpannableString("RM123.456");
        sizeSpan = new AbsoluteSizeSpan(18, true);
        ss.setSpan(sizeSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        superscriptSpan = new SuperscriptSpan();
        ss.setSpan(superscriptSpan, 0, 2, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);

        tvScript2.setText(ss);

        tvScript3.setText(TextUtil.getSubScriptText("log", "2"));

        tvScript4.setText(TextUtil.getSuperScriptText("2", "8"));
    }

}
