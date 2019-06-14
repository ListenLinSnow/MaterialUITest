package com.example.lc.materialuitest.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvAnimTestAdapter extends RecyclerView.Adapter {

    private Context context;

    private int lastPosition = -1;

    public RvAnimTestAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RvAnimTestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RvAnimTestViewHolder holder = (RvAnimTestViewHolder) viewHolder;
        holder.tvInfo.setText("测试" + i);

        //设置加载过的位置不再有动画（但我不喜欢这种）
        /*int adapterPosition = holder.getAdapterPosition();
        if(adapterPosition > lastPosition) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            Animator animator1 = ObjectAnimator.ofFloat(holder.llItem, "alpha", 0f, 1f);
            Animator animator2 = ObjectAnimator.ofFloat(holder.llItem, "translationX", width, 0f);
            animator1.setDuration(1000).start();
            animator2.setDuration(1000).start();
            lastPosition = adapterPosition;
        }else {
            ViewCompat.setAlpha(holder.itemView, 1);
        }*/
        //WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        //int width = windowManager.getDefaultDisplay().getWidth();
        //Animator animator1 = ObjectAnimator.ofFloat(holder.llItem, "alpha", 0f, 1f);
        //Animator animator2 = ObjectAnimator.ofFloat(holder.llItem, "translationX", width, 0f);
        //animator1.setDuration(1000).start();
        //animator2.setDuration(500).start();
        Animator animator3 = ObjectAnimator.ofFloat(holder.llItem, "scaleX", 0f, 1.2f, 1f);
        Animator animator4 = ObjectAnimator.ofFloat(holder.llItem, "scaleY", 0f, 1.2f, 1f);
        animator3.setDuration(500).start();
        animator4.setDuration(500).start();
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class RvAnimTestViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_item_single_text)
        LinearLayout llItem;
        @BindView(R.id.tv_item_single_text)
        TextView tvInfo;

        public RvAnimTestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
