package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleHorizontalRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> dataList;

    public SingleHorizontalRvAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SingleHorizontalRvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test_sp, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        SingleHorizontalRvViewHolder holder = (SingleHorizontalRvViewHolder) viewHolder;
        holder.tvInfo.setText(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class SingleHorizontalRvViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_sp_info)
        TextView tvInfo;

        public SingleHorizontalRvViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
