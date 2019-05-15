package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.SingleItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwitchRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SingleItem> singleItemList;

    public SwitchRvAdapter(Context context, List<SingleItem> singleItemList) {
        this.context = context;
        this.singleItemList = singleItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SwitchRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_switch, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SwitchRvViewHolder holder = (SwitchRvViewHolder) viewHolder;

        SingleItem singleItem = singleItemList.get(i);
        holder.imageView.setImageResource(singleItem.getImgId());
        holder.textView.setText(singleItem.getText());
    }

    @Override
    public int getItemCount() {
        return singleItemList.size();
    }

    public class SwitchRvViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_item_single_switch)
        ImageView imageView;
        @BindView(R.id.tv_item_single_switch)
        TextView textView;
        @BindView(R.id.switch_item_single_switch)
        Switch aSwitch;

        public SwitchRvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
