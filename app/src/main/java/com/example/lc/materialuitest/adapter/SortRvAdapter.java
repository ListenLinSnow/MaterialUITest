package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.SingleItem;
import com.example.lc.materialuitest.callback.ItemTouchHelperCallback;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SortRvAdapter extends RecyclerView.Adapter implements ItemTouchHelperCallback.ItemTouchHelperAdapter {

    private Context context;
    private List<SingleItem> singleItemList;

    public SortRvAdapter(Context context, List<SingleItem> singleItemList) {
        this.context = context;
        this.singleItemList = singleItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SortRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_sort, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SortRvViewHolder holder = (SortRvViewHolder) viewHolder;

        SingleItem singleItem = singleItemList.get(i);
        holder.imageView.setImageResource(singleItem.getImgId());
        holder.textView.setText(singleItem.getText());
    }

    @Override
    public int getItemCount() {
        return singleItemList.size();
    }

    @Override
    public void onMove(int srcPosition, int targetPosition) {
        Collections.swap(singleItemList, srcPosition, targetPosition);
        notifyItemMoved(srcPosition, targetPosition);
    }

    @Override
    public void onSwipe(int position) {
        singleItemList.remove(position);
        notifyItemRemoved(position);
    }

    public class SortRvViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_item_single_sort)
        ImageView imageView;
        @BindView(R.id.tv_item_single_sort)
        TextView textView;

        public SortRvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
