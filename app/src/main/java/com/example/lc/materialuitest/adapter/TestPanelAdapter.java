package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.BalanceFormItem;
import com.kelin.scrollablepanel.library.PanelAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestPanelAdapter extends PanelAdapter {

    private List<List<String>> itemList;
    private Context context;

    public TestPanelAdapter(List<List<String>> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public int getRowCount() {
        return itemList.size();
    }

    @Override
    public int getColumnCount() {
        return itemList.get(0).size();
    }

    @Override
    public int getItemViewType(int row, int column) {
        return super.getItemViewType(row, column);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        TestViewHolder viewHolder = (TestViewHolder) holder;

        viewHolder.tvInfo.setText(itemList.get(row).get(column));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new TestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test_scrollable_panel, parent, false));
        return new TestViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test_sp, parent, false));
    }

    class TestViewHolder extends RecyclerView.ViewHolder{

        /*@BindView(R.id.tv_item_test_sp_sample_code)
        TextView tvSampleCode;
        @BindView(R.id.tv_item_test_sp_container_code)
        TextView tvContainerCode;
        @BindView(R.id.tv_item_test_sp_sample_volume)
        TextView tvVolume;
        @BindView(R.id.tv_item_test_sp_average_weight1)
        TextView tvWeight1;
        @BindView(R.id.tv_item_test_sp_average_weight2)
        TextView tvWeight2;
        @BindView(R.id.tv_item_test_sp_sample_weight)
        TextView tvWeight;
        @BindView(R.id.tv_item_test_sp_sample_density)
        TextView tvDensity;
        @BindView(R.id.tv_item_test_sp_remark)
        TextView tvRemark;*/
        @BindView(R.id.tv_item_sp_info)
        TextView tvInfo;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
