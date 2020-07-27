package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.util.DisplayUtil;
import com.example.lc.materialuitest.view.customTable.CustomTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LockColumnAdapter extends RecyclerView.Adapter<LockColumnAdapter.LockColumnViewHolder> {

    private Context context;
    /**
     * 第一列数据
     */
    private ArrayList<String> lockColumnDataList;
    /**
     * 第一行背景颜色
     */
    private int firstRowBackgroundColor;
    /**
     * 表格头部字体颜色
     */
    private int tableHeaderTextColor;
    /**
     * 表格内容字体颜色
     */
    private int tableContentTextColor;
    /**
     * 是否锁定首行
     */
    private boolean isLockFirstRow = true;
    /**
     * 记录每列最大宽度
     */
    private ArrayList<Integer> columnMaxWidthList = new ArrayList<>();
    /**
     * 记录每行最大高度
     */
    private ArrayList<Integer> rowMaxHeightList = new ArrayList<>();
    /**
     * 单元格字体大小
     */
    private int textViewSize;
    /**
     * 单元格内边距
     */
    private int cellPadding;
    /**
     * Item点击事件
     */
    private CustomTable.OnItemClickListener onItemClickListener;
    /**
     * Item长按事件
     */
    private CustomTable.OnItemLongClickListener onItemLongClickListener;
    /**
     * Item项被选中监听(处理被选中的效果)
     */
    private CustomTableAdapter.OnItemSelectedListener onItemSelectedListener;

    public LockColumnAdapter(Context context, ArrayList<String> lockColumnDataList) {
        this.context = context;
        this.lockColumnDataList = lockColumnDataList;
    }

    class LockColumnViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_lock_column_text)
        TextView textView;
        @BindView(R.id.ll_item_lock_column)
        LinearLayout itemLL;

        public LockColumnViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @NonNull
    @Override
    public LockColumnViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LockColumnViewHolder(LayoutInflater.from(context).inflate(R.layout.item_lock_column, null));
    }

    @Override
    public void onBindViewHolder(@NonNull LockColumnViewHolder holder, final int position) {
        //设置布局
        holder.textView.setText(lockColumnDataList.get(position));
        holder.textView.setTextSize(textViewSize);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.textView.getLayoutParams();
        layoutParams.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(0));
        if (isLockFirstRow){
            layoutParams.height = DisplayUtil.dip2px(context, rowMaxHeightList.get(position + 1));
        } else {
            layoutParams.height = DisplayUtil.dip2px(context, rowMaxHeightList.get(position));
        }
        layoutParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
        holder.textView.setLayoutParams(layoutParams);
        //设置颜色
        if (!isLockFirstRow){
            if (position == 0){
                holder.itemLL.setBackgroundColor(ContextCompat.getColor(context, firstRowBackgroundColor));
                holder.textView.setTextColor(ContextCompat.getColor(context, tableHeaderTextColor));
            } else {
                holder.textView.setTextColor(ContextCompat.getColor(context, tableContentTextColor));
            }
        } else {
            holder.textView.setTextColor(ContextCompat.getColor(context, tableContentTextColor));
        }
        //添加时间
        if (onItemClickListener != null){
            holder.itemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener != null){
                        onItemSelectedListener.onItemSelected(v, position);
                    }
                    if (isLockFirstRow){
                        onItemClickListener.onItemClick(v, position);
                    } else {
                        if (position != 0){
                            onItemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
        if (onItemLongClickListener != null){
            holder.itemLL.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemSelectedListener != null){
                        onItemSelectedListener.onItemSelected(v, position);
                    }
                    if (isLockFirstRow){
                        onItemLongClickListener.onItemLongClick(v, position);
                    } else {
                        if (position != 0){
                            onItemLongClickListener.onItemLongClick(v, position);
                        }
                    }
                    return true;
                }
            });
        }
        //如果没有设置点击事件和长按事件
        if (onItemClickListener == null && onItemLongClickListener == null){
            holder.itemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener != null){
                        onItemSelectedListener.onItemSelected(v, position);
                    }
                }
            });
            holder.itemLL.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemSelectedListener != null){
                        onItemSelectedListener.onItemSelected(v, position);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lockColumnDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setFirstRowBackgroundColor(int firstRowBackgroundColor) {
        this.firstRowBackgroundColor = firstRowBackgroundColor;
    }

    public void setTableHeaderTextColor(int tableHeaderTextColor) {
        this.tableHeaderTextColor = tableHeaderTextColor;
    }

    public void setTableContentTextColor(int tableContentTextColor) {
        this.tableContentTextColor = tableContentTextColor;
    }

    public void setLockFirstRow(boolean lockFirstRow) {
        isLockFirstRow = lockFirstRow;
    }

    public void setColumnMaxWidthList(ArrayList<Integer> columnMaxWidthList) {
        this.columnMaxWidthList = columnMaxWidthList;
    }

    public void setRowMaxHeightList(ArrayList<Integer> rowMaxHeightList) {
        this.rowMaxHeightList = rowMaxHeightList;
    }

    public void setTextViewSize(int textViewSize) {
        this.textViewSize = textViewSize;
    }

    public void setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
    }

    public void setOnItemClickListener(CustomTable.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(CustomTable.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemSelectedListener(CustomTableAdapter.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
