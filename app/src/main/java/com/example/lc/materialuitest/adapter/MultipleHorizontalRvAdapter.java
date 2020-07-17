package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MultipleHorizontalRvAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<List<String>> dataList;

    public MultipleHorizontalRvAdapter(Context context, List<List<String>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MultipleHorizontalViewHolder(LayoutInflater.from(context).inflate(R.layout.item_multi_rv, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MultipleHorizontalViewHolder holder = (MultipleHorizontalViewHolder) viewHolder;

        List<String> singleList = dataList.get(i);

        holder.tvTitle.setText(singleList.get(0));

        List<String> tempList = new ArrayList<>();
        for (String data : singleList){
            tempList.add(data);
        }
        tempList.remove(0);
        SingleHorizontalRvAdapter adapter = new SingleHorizontalRvAdapter(context, tempList);
        holder.rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MultipleHorizontalViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_multi_rv_title)
        TextView tvTitle;
        @BindView(R.id.rv_item_multi_rv)
        RecyclerView rv;

        public MultipleHorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
