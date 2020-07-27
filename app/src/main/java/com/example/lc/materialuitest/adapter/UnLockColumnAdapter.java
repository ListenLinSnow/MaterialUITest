package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.util.DisplayUtil;
import com.example.lc.materialuitest.view.customTable.CustomTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnLockColumnAdapter extends RecyclerView.Adapter<UnLockColumnAdapter.UnLockViewHolder> {

    private Context context;
    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> tableDataList;
    /**
     * 第一行背景颜色
     */
    private int firstRowBackgroudColor;
    /**
     * 表格头部字体颜色
     */
    private int tableHeaderTextColor;
    /**
     * 表格内容字体颜色
     */
    private int tableContentTextColor;
    /**
     * 记录每列最大宽度
     */
    private ArrayList<Integer> columnMaxWidthList = new ArrayList<Integer>();
    /**
     * 记录每行最大高度
     */
    private ArrayList<Integer> rowMaxHeightList = new ArrayList<Integer>();
    /**
     * 单元格字体大小
     */
    private int textViewSize;
    /**
     * 是否锁定首行
     */
    private boolean lockFirstRow = true;
    /**
     * 是否锁定首列
     */
    private boolean lockFirstColumn = true;

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

    public UnLockColumnAdapter(Context context, ArrayList<ArrayList<String>> tableDataList) {
        this.context = context;
        this.tableDataList = tableDataList;
    }

    @Override
    public int getItemCount() {
        return tableDataList.size();
    }

    @NonNull
    @Override
    public UnLockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new UnLockViewHolder(LayoutInflater.from(context).inflate(R.layout.item_unlock_column, null));
    }

    @Override
    public void onBindViewHolder(UnLockViewHolder holder, final int position) {
        ArrayList<String> dataList = tableDataList.get(position);
        if (lockFirstColumn){
            //第一行是锁定的
            createRowView(holder.itemLL, dataList, false, rowMaxHeightList.get(position + 1));
        } else {
            if (position == 0){
                holder.itemLL.setBackgroundColor(ContextCompat.getColor(context, firstRowBackgroudColor));;
                createRowView(holder.itemLL, dataList, true, rowMaxHeightList.get(position));
            } else {
                createRowView(holder.itemLL, dataList, false, rowMaxHeightList.get(position));
            }
        }
        //添加事件
        if (onItemClickListener != null){
            holder.itemLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemSelectedListener != null){
                        onItemSelectedListener.onItemSelected(v, position);
                    }
                    if (lockFirstRow){
                        onItemClickListener.onItemClick(v, position + 1);
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
                    if (lockFirstRow){
                        onItemClickListener.onItemClick(v, position);
                    } else {
                        if (position != 0){
                            onItemClickListener.onItemClick(v, position);
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
                    return true;
                }
            });
        }
    }

    /**
     * 构造每行数据视图
     * @param linearLayout
     * @param dataList
     * @param isFirstRow
     * @param maxHeight
     */
    private void createRowView(LinearLayout linearLayout, List<String> dataList, boolean isFirstRow, int maxHeight){
        //设置LinearLayout
        //先清空，否则复用会造成重复绘制，使内容超出预期长度
        linearLayout.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            //构造单元格
            TextView textView = new TextView(context);
            if (isFirstRow) {
                textView.setTextColor(ContextCompat.getColor(context, tableHeaderTextColor));
            } else {
                textView.setTextColor(ContextCompat.getColor(context, tableContentTextColor));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewSize);
            textView.setGravity(Gravity.CENTER);
            textView.setText(dataList.get(i));
            //设置布局
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
            textViewParams.height = DisplayUtil.dip2px(context, maxHeight);
            if (lockFirstColumn){
                textViewParams.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(i + 1));
            } else {
                textViewParams.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(i));
            }
            textView.setLayoutParams(textViewParams);
            linearLayout.addView(textView);
            //画分隔线
            if (dataList.size() - 1 != i){
                View spiltView = new View(context);
                ViewGroup.LayoutParams spiltViewParams = new ViewGroup.LayoutParams(DisplayUtil.dip2px(context, 1),
                        ViewGroup.LayoutParams.MATCH_PARENT);
                spiltView.setLayoutParams(spiltViewParams);
                if (isFirstRow){
                    spiltView.setBackgroundColor(ContextCompat.getColor(context, R.color.md_white));
                } else {
                    spiltView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_gray));
                }
                linearLayout.addView(spiltView);
            }
        }
    }

    class UnLockViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_item_unlock_column)
        LinearLayout itemLL;

        public UnLockViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setFirstRowBackgroudColor(int firstRowBackgroudColor) {
        this.firstRowBackgroudColor = firstRowBackgroudColor;
    }

    public void setTableHeaderTextColor(int tableHeaderTextColor) {
        this.tableHeaderTextColor = tableHeaderTextColor;
    }

    public void setTableContentTextColor(int tableContentTextColor) {
        this.tableContentTextColor = tableContentTextColor;
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

    public void setLockFirstRow(boolean lockFirstRow) {
        this.lockFirstRow = lockFirstRow;
    }

    public void setLockFirstColumn(boolean lockFirstColumn) {
        this.lockFirstColumn = lockFirstColumn;
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
