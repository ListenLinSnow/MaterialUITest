package com.example.lc.materialuitest.view.customTable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.SelectedRvAdapter;
import com.example.lc.materialuitest.util.ProgressStyle;

import androidx.annotation.Nullable;

public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwitcher simpleViewSwitcher;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NO_MORE = 2;
    private TextView textView;
    private String loadingHint;
    private String noMoreHint;
    private String loadingDoneHint;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    public LoadingMoreFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView(){
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        simpleViewSwitcher = new SimpleViewSwitcher(getContext());
        simpleViewSwitcher.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        simpleViewSwitcher.setView(progressView);

        addView(simpleViewSwitcher);
        textView = new TextView(getContext());
        textView.setText("正在加载...");
        loadingHint = "正在加载...";
        noMoreHint = "数据已全部加载结束";
        loadingDoneHint = "加载完成";
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int)getResources().getDimension(R.dimen.ten), 0, 0, 0);

        textView.setLayoutParams(layoutParams);
        addView(textView);
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress){
            simpleViewSwitcher.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
        } else {
            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            simpleViewSwitcher.setView(progressView);
        }
    }

    public void setState(int state){
        switch (state){
            case STATE_LOADING:
                simpleViewSwitcher.setVisibility(VISIBLE);
                textView.setText(loadingHint);
                this.setVisibility(VISIBLE);
                break;
            case STATE_COMPLETE:
                textView.setText(loadingDoneHint);
                this.setVisibility(GONE);
                break;
            case STATE_NO_MORE:
                textView.setText(noMoreHint);
                simpleViewSwitcher.setVisibility(GONE);
                this.setVisibility(VISIBLE);
                break;
        }
    }

    public void setLoadingHint(String loadingHint) {
        this.loadingHint = loadingHint;
    }

    public void setNoMoreHint(String noMoreHint) {
        this.noMoreHint = noMoreHint;
    }

    public void setLoadingDoneHint(String loadingDoneHint) {
        this.loadingDoneHint = loadingDoneHint;
    }
}
