package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleTextAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> list;

    private OnInfoClickListener onInfoClickListener;

    public SingleTextAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SingleInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_single_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        SingleInfoViewHolder holder = (SingleInfoViewHolder) viewHolder;

        holder.tvInfo.setText(list.get(i));

        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onInfoClickListener != null) {
                    onInfoClickListener.onItemClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingleInfoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ll_item_single_text)
        LinearLayout llItem;
        @BindView(R.id.tv_item_single_text)
        TextView tvInfo;

        public SingleInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnInfoClickListener{
        void onItemClick(int position);
    }

    public void setOnInfoClickListener(OnInfoClickListener onInfoClickListener) {
        this.onInfoClickListener = onInfoClickListener;
    }

}
