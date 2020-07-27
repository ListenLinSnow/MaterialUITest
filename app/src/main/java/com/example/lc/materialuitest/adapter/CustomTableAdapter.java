package com.example.lc.materialuitest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.view.customTable.CustomHorizontalScrollView;
import com.example.lc.materialuitest.view.customTable.CustomTable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomTableAdapter extends RecyclerView.Adapter<CustomTableAdapter.CustomTableViewHolder> {

    private Context context;
    /**
     * 第一列数据
     */
    private ArrayList<String> lockColumnDataList;
    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> tableDataList;
    /**
     * 第一列是否被锁定
     */
    private boolean isLockFirstColumn;
    /**
     * 第一行是否被锁定
     */
    private boolean isLockFirstRow;
    /**
     * 记录每列最大宽度
     */
    private ArrayList<Integer> columnMaxWidthList = new ArrayList<>();
    /**
     * 记录每列最大高度
     */
    private ArrayList<Integer> rowMaxHeightList = new ArrayList<>();
    /**
     * 单元格字体大小
     */
    private int textViewSize;
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
     * 单元格内边距
     */
    private int cellPadding;
    /**
     * 表格横向滚动监听事件
     */
    private CustomTable.OnTableViewListener onTableViewListener;
    /**
     * 表格横向滚动到边界监听事件
     */
    private CustomTable.OnTableViewRangeListener onTableViewRangeListener;
    /**
     * Item点击事件
     */
    private CustomTable.OnItemClickListener onItemClickListener;
    /**
     * Item选中样式
     */
    private CustomTable.OnItemLongClickListener onItemLongClickListener;
    /**
     * Item选中样式
     */
    private int onItemSelector;
    /**
     * 表格视图加载完成监听事件
     */
    private OnTableViewCreatedListener onTableViewCreatedListener;
    /**
     * 锁定视图Adapter
     */
    private LockColumnAdapter lockAdapter;
    /**
     * 构造方法
     */
    private UnLockColumnAdapter unlockAdapter;

    /**
     * 构造方法
     * @param context
     * @param lockColumnDataList
     * @param tableDataList
     * @param isLockFirstColumn
     * @param isLockFirstRow
     */
    public CustomTableAdapter(Context context, ArrayList<String> lockColumnDataList, ArrayList<ArrayList<String>> tableDataList, boolean isLockFirstColumn, boolean isLockFirstRow) {
        this.context = context;
        this.lockColumnDataList = lockColumnDataList;
        this.tableDataList = tableDataList;
        this.isLockFirstColumn = isLockFirstColumn;
        this.isLockFirstRow = isLockFirstRow;
    }

    @NonNull
    @Override
    public CustomTableViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        CustomTableViewHolder viewHolder = new CustomTableViewHolder(LayoutInflater.from(context).inflate(R.layout.item_custom_content_view, null));
        if (onTableViewCreatedListener != null){
            onTableViewCreatedListener.onTableViewCreatedCompleted(viewHolder.scrollView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomTableViewHolder holder, int position) {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (isLockFirstColumn){
            //构造锁定视图
            holder.lockRecyclerView.setVisibility(View.VISIBLE);
            if (lockAdapter == null){
                lockAdapter = new LockColumnAdapter(context, lockColumnDataList);
                lockAdapter.setCellPadding(cellPadding);
                lockAdapter.setColumnMaxWidthList(columnMaxWidthList);
                lockAdapter.setRowMaxHeightList(rowMaxHeightList);
                lockAdapter.setTextViewSize(textViewSize);
                lockAdapter.setLockFirstRow(isLockFirstRow);
                lockAdapter.setFirstRowBackgroundColor(firstRowBackgroundColor);
                lockAdapter.setTableHeaderTextColor(tableHeaderTextColor);
                lockAdapter.setTableContentTextColor(tableContentTextColor);
                lockAdapter.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(View view, int position) {
                        RecyclerView.LayoutManager lockLayoutManager = holder.lockRecyclerView.getLayoutManager();
                        int itemCount = lockLayoutManager.getItemCount();
                        View item = lockLayoutManager.getChildAt(position);
                        item.setBackgroundColor(ContextCompat.getColor(context, onItemSelector));
                        for (int i = 0; i < itemCount; i++){
                            if (i != position){
                                lockLayoutManager.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                        RecyclerView.LayoutManager unlockLayoutManager = holder.mainRecyclerView.getLayoutManager();
                        int itemUnlockCount = unlockLayoutManager.getItemCount();
                        View unlockItem = unlockLayoutManager.getChildAt(position);
                        unlockItem.setBackgroundColor(ContextCompat.getColor(context, onItemSelector));
                        for (int i = 0; i < itemUnlockCount; i++){
                            if (i != position){
                                unlockLayoutManager.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                    }
                });
                if (onItemClickListener != null){
                    lockAdapter.setOnItemClickListener(onItemClickListener);
                }
                if (onItemLongClickListener != null){
                    lockAdapter.setOnItemLongClickListener(onItemLongClickListener);
                }
                holder.lockRecyclerView.setLayoutManager(layoutManager);
                holder.lockRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
                holder.lockRecyclerView.setAdapter(lockAdapter);
            } else {
                lockAdapter.notifyDataSetChanged();
            }
        } else {
            holder.lockRecyclerView.setVisibility(View.GONE);
        }
        //构造主表格视图
        if (unlockAdapter == null){
            unlockAdapter = new UnLockColumnAdapter(context, tableDataList);
            unlockAdapter.setCellPadding(cellPadding);
            unlockAdapter.setColumnMaxWidthList(columnMaxWidthList);
            unlockAdapter.setRowMaxHeightList(rowMaxHeightList);
            unlockAdapter.setTextViewSize(textViewSize);
            unlockAdapter.setLockFirstColumn(isLockFirstColumn);
            unlockAdapter.setLockFirstRow(isLockFirstRow);
            unlockAdapter.setFirstRowBackgroudColor(firstRowBackgroundColor);
            unlockAdapter.setTableHeaderTextColor(tableHeaderTextColor);
            unlockAdapter.setTableContentTextColor(tableContentTextColor);
            unlockAdapter.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position) {
                    if (isLockFirstColumn){
                        RecyclerView.LayoutManager lockLayoutManager = holder.lockRecyclerView.getLayoutManager();
                        int itemCount = lockLayoutManager.getItemCount();
                        View item = lockLayoutManager.getChildAt(position);
                        item.setBackgroundColor(ContextCompat.getColor(context, onItemSelector));
                        for (int i = 0; i < itemCount; i++){
                            if (i != position){
                                lockLayoutManager.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            }
                        }
                    }
                    RecyclerView.LayoutManager unlockLayoutManager = holder.mainRecyclerView.getLayoutManager();
                    int itemUnlockCount = unlockLayoutManager.getItemCount();
                    View unlockItem = unlockLayoutManager.getChildAt(position);
                    unlockItem.setBackgroundColor(ContextCompat.getColor(context, onItemSelector));
                    for (int i = 0; i < itemUnlockCount; i++){
                        if (i != position){
                            unlockLayoutManager.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                }
            });
            if (onItemClickListener != null){
                unlockAdapter.setOnItemClickListener(onItemClickListener);
            }
            if (onItemLongClickListener != null){
                unlockAdapter.setOnItemLongClickListener(onItemLongClickListener);
            }
            LinearLayoutManager unlockLayoutManager = new LinearLayoutManager(context);
            unlockLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.mainRecyclerView.setLayoutManager(unlockLayoutManager);
            holder.mainRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            holder.mainRecyclerView.setAdapter(unlockAdapter);
        } else {
            unlockAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class CustomTableViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rv_item_custom_content_lock)
        RecyclerView lockRecyclerView;
        @BindView(R.id.rv_item_custom_content_main)
        RecyclerView mainRecyclerView;
        @BindView(R.id.csv_item_custom_content_parent)
        CustomHorizontalScrollView scrollView;

        public CustomTableViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //解决滑动冲突，只保留最外层RecyclerView的下拉和上拉事件
            lockRecyclerView.setFocusable(false);
            mainRecyclerView.setFocusable(false);
            scrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChanged(HorizontalScrollView scrollView, int x, int y) {
                    if (onTableViewListener != null){
                        onTableViewListener.onTableViewScrollChange(x, y);
                    }
                }

                @Override
                public void onScrollFarLeft(HorizontalScrollView scrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onLeft(scrollView);
                    }
                }

                @Override
                public void onScrollFarRight(HorizontalScrollView scrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onRight(scrollView);
                    }
                }
            });
        }
    }

    /**
     * 表格创建完成回调
     */
    public interface OnTableViewCreatedListener {
        /**
         * 返回当前横向滚动视图给上个界面
         * @param scrollView
         */
        void onTableViewCreatedCompleted(CustomHorizontalScrollView scrollView);
    }

    public interface OnItemSelectedListener {
        void onItemSelected(View view, int position);
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

    public void setFirstRowBackgroundColor(int firstRowBackgroundColor) {
        this.firstRowBackgroundColor = firstRowBackgroundColor;
    }

    public void setTableHeaderTextColor(int tableHeaderTextColor) {
        this.tableHeaderTextColor = tableHeaderTextColor;
    }

    public void setTableContentTextColor(int tableContentTextColor) {
        this.tableContentTextColor = tableContentTextColor;
    }

    public void setCellPadding(int cellPadding) {
        this.cellPadding = cellPadding;
    }

    public void setOnTableViewListener(CustomTable.OnTableViewListener onTableViewListener) {
        this.onTableViewListener = onTableViewListener;
    }

    public void setOnTableViewRangeListener(CustomTable.OnTableViewRangeListener onTableViewRangeListener) {
        this.onTableViewRangeListener = onTableViewRangeListener;
    }

    public void setOnItemClickListener(CustomTable.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(CustomTable.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnTableViewCreatedListener(OnTableViewCreatedListener onTableViewCreatedListener) {
        this.onTableViewCreatedListener = onTableViewCreatedListener;
    }

    public void setOnItemSelector(int onItemSelector) {
        this.onItemSelector = onItemSelector;
    }
}
