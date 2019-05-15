package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.SingleItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SingleItem> itemList;

    public SelectedRvAdapter(Context context, List<SingleItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelectedRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_selected, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SelectedRvViewHolder holder = (SelectedRvViewHolder) viewHolder;

        SingleItem singleItem = itemList.get(i);
        holder.textView.setText(singleItem.getText());
        holder.imageView.setImageResource(singleItem.getImgId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class SelectedRvViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cb_item_single_selected)
        CheckBox checkBox;
        @BindView(R.id.tv_item_single_selected)
        TextView textView;
        @BindView(R.id.iv_item_single_selected)
        ImageView imageView;

        public SelectedRvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
