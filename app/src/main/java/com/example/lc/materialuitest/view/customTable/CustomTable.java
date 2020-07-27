package com.example.lc.materialuitest.view.customTable;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.adapter.CustomTableAdapter;
import com.example.lc.materialuitest.util.DisplayUtil;
import com.example.lc.materialuitest.util.ProgressStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomTable {

    private Context context;

    /**
     * 表格父视图
     */
    private ViewGroup contentView;

    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> tableDataList = new ArrayList<>();

    /**
     * 表格视图
     */
    private View tableView;

    /**
     * 是否锁定首行
     */
    private boolean isLockFirstRow = true;

    /**
     * 是否锁定首列
     */
    private boolean isLockFirstColumn = true;

    /**
     * 最大列宽
     */
    private int maxColumnWidth;

    /**
     * 最小列宽
     */
    private int minColumnWidth;

    /**
     * 最大行高
     */
    private int maxRowHeight;

    /**
     * 最小行高
     */
    private int minRowHeight;

    /**
     * 第一行背景颜色
     */
    private int firstRowBackgroundColor;

    /**
     * 数据为空时的默认值
     */
    private String nullableString;

    /**
     * 单元格字体大小
     */
    private int textViewSize;

    /**
     * 表格首行字体颜色
     */
    private int tableHeaderTextColor;

    /**
     * 表格内容字体颜色
     */
    private int tableContentTextColor;

    /**
     * 表格横向滚动监听事件
     */
    private OnTableViewListener onTableViewListener;

    /**
     * 表格横向滚动到边界监听事件
     */
    private OnTableViewRangeListener onTableViewRangeListener;

    /**
     * 加载刷新监听事件
     */
    private OnLoadingListener onLoadingListener;

    /**
     * 点击事件
     */
    private OnItemClickListener onItemClickListener;

    /**
     * 长按事件
     */
    private OnItemLongClickListener onItemLongClickListener;

    /**
     * Item选中样式
     */
    private int onItemSelector;

    /**
     * 单元格内边距
     */
    private int cellPadding;

    /**
     * 要改变的列集合
     */
    private HashMap<Integer, Integer> changeColumns = new HashMap<>();

    /**
     * 表格第一行数据，不包含第一个元素
     */
    private ArrayList<String> tableFirstDataList = new ArrayList<>();

    /**
     * 表格第一列数据，不包含第一个元素
     */
    private ArrayList<String> tableColumnDataList = new ArrayList<>();

    /**
     * 表格左上角数据
     */
    private String columnTitle;

    /**
     * 表格每一行数据，不包括第一行和第一列
     */
    private ArrayList<ArrayList<String>> tableRowDataList = new ArrayList<ArrayList<String>>();

    /**
     * 记录每列最大宽度
     */
    private ArrayList<Integer> columnMaxWidthList = new ArrayList<>();

    /**
     * 记录每行最大高度
     */
    private ArrayList<Integer> rowMaxHeightList = new ArrayList<>();

    /**
     * 把所有的滚动视图放入列表，后面实现联动效果
     */
    private ArrayList<HorizontalScrollView> scrollViewList = new ArrayList<>();

    /**
     * 表格左上角视图
     */
    private TextView columnTitleView;

    /**
     * 第一行布局（锁状态）
     */
    private LinearLayout lockHeaderView;

    /**
     * 第一行布局（未锁状态）
     */
    private LinearLayout unlockHeaderView;

    /**
     * 第一行滚动视图（锁状态）
     */
    private CustomHorizontalScrollView lockScrollView;

    /**
     * 第一行滚动视图（未锁状态）
     */
    private CustomHorizontalScrollView unlockScrollView;

    /**
     * 表格主视图
     */
    private XRecyclerView scrollTableView;

    /**
     * 列表适配器
     */
    private CustomTableAdapter customTableAdapter;

    public CustomTable(Context context, ViewGroup contentView, ArrayList<ArrayList<String>> tableDataList) {
        this.context = context;
        this.contentView = contentView;
        this.tableDataList = tableDataList;
        initAttrs();
    }

    /**
     * 初始化属性
     */
    private void initAttrs(){
        tableView = LayoutInflater.from(context).inflate(R.layout.view_table_view, null);
        maxColumnWidth = 100;
        minColumnWidth = 70;
        minRowHeight = 20;
        maxRowHeight = 60;
        nullableString = "N/A";
        tableHeaderTextColor = R.color.md_red_500;
        tableContentTextColor = R.color.md_grey_500;
        firstRowBackgroundColor = R.color.md_white;
        textViewSize = 16;
        cellPadding = DisplayUtil.dip2px(context, 45);
    }

    /**
     * 展现视图
     */
    public void show(){
        initData();
        initView();
        contentView.removeAllViews();
        contentView.addView(tableView);
    }

    /**
     * 初始化展现表格
     */
    private void initData(){
        if (tableDataList != null && tableDataList.size() > 0){
            //检查数据，如果有一行数据长度不一致，以最长为标准填"N/A"字样，如果有null也替换
            int maxLength = 0;
            for (int i = 0; i < tableDataList.size(); i++){
                if (tableDataList.get(i).size() >= maxLength){
                    maxLength = tableDataList.get(i).size();
                }
                //替换空值
                ArrayList<String> rowDataList = tableDataList.get(i);
                for (int j = 0; j < rowDataList.size(); j++){
                    if (TextUtils.isEmpty(rowDataList.get(j))){
                        rowDataList.set(j, nullableString);
                    }
                }
                tableDataList.set(i, rowDataList);
            }
            //对于某行数据长度可能不满于最大长度的情况，给予空值补充
            for (int i = 0; i < tableDataList.size(); i++){
                ArrayList<String> rowDataList = tableDataList.get(i);
                if (rowDataList.size() < maxLength){
                    int size = maxLength - rowDataList.size();
                    for (int j = 0; j < size; j++){
                        rowDataList.add(nullableString);
                    }
                    tableDataList.set(i, rowDataList);
                }
            }

            //初始化每列最大宽度
            for (int i = 0; i < tableDataList.size(); i++){
                ArrayList<String> rowDataList = tableDataList.get(i);
                for (int j = 0; j < rowDataList.size(); j++){
                    TextView textView = new TextView(context);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewSize);
                    textView.setText(rowDataList.get(j));
                    textView.setGravity(Gravity.CENTER);
                    //设置布局
                    LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    textParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
                    textView.setLayoutParams(textParams);
                    if (i == 0){
                        columnMaxWidthList.add(measureTextWidth(textView, rowDataList.get(j)));
                    } else {
                        int length = columnMaxWidthList.get(j);
                        int current = measureTextWidth(textView, rowDataList.get(j));
                        if (current > length){
                            columnMaxWidthList.set(j, current);
                        }
                    }
                }
            }

            Log.d("JsonList", "columnMaxWidthList.size:" + columnMaxWidthList.size());

            //如果用户指定某列宽度，则按照用户指定宽度计算
            if (changeColumns.size() > 0){
                for (int key : changeColumns.keySet()){
                    changeColumnWidth(key, changeColumns.get(key));
                }
            }

            //初始化每行最大高度
            for (int i = 0; i < tableDataList.size(); i++){
                ArrayList<String> rowDataList = tableDataList.get(i);

                TextView textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewSize);
                textView.setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                textViewParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
                textView.setLayoutParams(textViewParams);

                int maxHeight = measureTextViewHeight(textView, rowDataList.get(0));
                rowMaxHeightList.add(maxHeight);
                for (int j = 0; j < rowDataList.size(); j++){
                    int currentHeight;
                    //如果用户指定了某列的高度
                    if (changeColumns.size() > 0 && changeColumns.containsKey(j)){
                        currentHeight = getTextViewHeight(textView, rowDataList.get(j), changeColumns.get(j));
                    } else {
                        currentHeight = measureTextViewHeight(textView, rowDataList.get(j));
                    }
                    if (currentHeight > maxHeight){
                        rowMaxHeightList.set(i, currentHeight);
                    }
                }
            }

            if (isLockFirstRow){
                ArrayList<String> firstRowDataList = (ArrayList<String>) tableDataList.get(0).clone();
                if (isLockFirstColumn){
                    //锁定第一列
                    columnTitle = firstRowDataList.get(0);
                    firstRowDataList.remove(0);
                    tableFirstDataList.addAll(firstRowDataList);
                    //构造第一列数据，并且构造表格每行数据
                    for (int i = 1; i < tableDataList.size(); i++){
                        ArrayList<String> rowDataList = (ArrayList<String>) tableDataList.get(i).clone();
                        tableColumnDataList.add(rowDataList.get(0));
                        rowDataList.remove(0);
                        tableRowDataList.add(rowDataList);
                    }
                } else {
                    tableFirstDataList.addAll(firstRowDataList);
                    for (int i = 1; i < tableDataList.size(); i++){
                        tableRowDataList.add(tableDataList.get(i));
                    }
                }
                /*Log.d("JsonList", "tableFirstDataList.size:" + tableFirstDataList.size());
                for (int i = 0; i < tableFirstDataList.size(); i++){
                    Log.d("JsonList", "title content:" + tableFirstDataList.get(i));
                }*/
            } else {
                if (isLockFirstColumn){
                    //锁定第一列
                    for (int i = 0; i < tableDataList.size(); i++){
                        ArrayList<String> rowDataList = (ArrayList<String>) tableDataList.get(i).clone();
                        tableColumnDataList.add(rowDataList.get(0));
                        rowDataList.remove(0);
                        tableRowDataList.add(rowDataList);
                    }
                } else {
                    for (int i = 0; i < tableRowDataList.size(); i++){
                        tableRowDataList.add(tableDataList.get(i));
                    }
                }
            }
        } else {
            Toast.makeText(context, "表格数据为空", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化表格视图
     */
    private void initView(){
        columnTitleView = (TextView) tableView.findViewById(R.id.tv_lock_header_view_title);
        lockHeaderView = (LinearLayout) tableView.findViewById(R.id.ll_lock_header_view);
        unlockHeaderView = (LinearLayout) tableView.findViewById(R.id.ll_unlock_header_view);
        lockScrollView = (CustomHorizontalScrollView) tableView.findViewById(R.id.sv_lock_header_view);
        unlockScrollView = (CustomHorizontalScrollView) tableView.findViewById(R.id.sv_unlock_header_view);
        //表格主视图
        scrollTableView = tableView.findViewById(R.id.xv_table_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        scrollTableView.setLayoutManager(layoutManager);
        scrollTableView.setArrowImageView(R.drawable.iconfont_downgrey);
        scrollTableView.setRefreshProgressStyle(ProgressStyle.BallRotate);
        scrollTableView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        scrollTableView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if (onLoadingListener != null){
                    onLoadingListener.onRefresh(scrollTableView, tableDataList);
                }
            }

            @Override
            public void onLoadMore() {
                if (onLoadingListener != null){
                    onLoadingListener.onLoadMore(scrollTableView, tableDataList);
                }
            }
        });
        customTableAdapter = new CustomTableAdapter(context, tableColumnDataList, tableRowDataList, isLockFirstColumn, isLockFirstRow);
        customTableAdapter.setCellPadding(cellPadding);
        customTableAdapter.setColumnMaxWidthList(columnMaxWidthList);
        customTableAdapter.setRowMaxHeightList(rowMaxHeightList);
        customTableAdapter.setTextViewSize(textViewSize);
        customTableAdapter.setTableContentTextColor(tableContentTextColor);
        customTableAdapter.setTableHeaderTextColor(tableHeaderTextColor);
        customTableAdapter.setFirstRowBackgroundColor(firstRowBackgroundColor);
        customTableAdapter.setOnTableViewListener(new OnTableViewListener() {
            @Override
            public void onTableViewScrollChange(int x, int y) {
                changeAllScrollView(x, y);
            }
        });
        if (onItemClickListener != null){
            customTableAdapter.setOnItemClickListener(onItemClickListener);
        }
        if (onItemLongClickListener != null){
            customTableAdapter.setOnItemLongClickListener(onItemLongClickListener);
        }
        if (onItemSelector != 0){
            customTableAdapter.setOnItemSelector(onItemSelector);
        } else {
            customTableAdapter.setOnItemSelector(R.color.md_grey_400);
        }
        customTableAdapter.setOnTableViewRangeListener(new OnTableViewRangeListener() {
            @Override
            public void onLeft(HorizontalScrollView horizontalScrollView) {
                if (onTableViewRangeListener != null){
                    onTableViewRangeListener.onLeft(horizontalScrollView);
                }
            }

            @Override
            public void onRight(HorizontalScrollView horizontalScrollView) {
                if (onTableViewRangeListener != null){
                    onTableViewRangeListener.onRight(horizontalScrollView);
                }
            }
        });
        customTableAdapter.setOnTableViewCreatedListener(new CustomTableAdapter.OnTableViewCreatedListener() {
            @Override
            public void onTableViewCreatedCompleted(CustomHorizontalScrollView customHorizontalScrollView) {
                scrollViewList.add(customHorizontalScrollView);
            }
        });
        scrollTableView.setAdapter(customTableAdapter);

        lockHeaderView.setBackgroundColor(ContextCompat.getColor(context, firstRowBackgroundColor));
        unlockHeaderView.setBackgroundColor(ContextCompat.getColor(context, firstRowBackgroundColor));
        if (isLockFirstRow){
            if (isLockFirstColumn){
                lockHeaderView.setVisibility(View.VISIBLE);
                unlockHeaderView.setVisibility(View.GONE);
            } else {
                lockHeaderView.setVisibility(View.GONE);
                unlockHeaderView.setVisibility(View.VISIBLE);
            }
            createHeaderView();
        } else {
            lockHeaderView.setVisibility(View.GONE);
            unlockHeaderView.setVisibility(View.GONE);
        }
    }

    /**
     * 创建头部视图
     */
    private void createHeaderView(){
        if (isLockFirstColumn){
            columnTitleView.setTextColor(ContextCompat.getColor(context, tableHeaderTextColor));
            columnTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewSize);
            columnTitleView.setText(columnTitle);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) columnTitleView.getLayoutParams();
            layoutParams.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(0));
            layoutParams.height = DisplayUtil.dip2px(context, rowMaxHeightList.get(0));
            layoutParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
            columnTitleView.setLayoutParams(layoutParams);
            //构造滚动视图
            createScrollView(lockScrollView, tableFirstDataList, true);
            scrollViewList.add(lockScrollView);
            lockScrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChanged(HorizontalScrollView horizontalScrollView, int x, int y) {
                    changeAllScrollView(x, y);
                }

                @Override
                public void onScrollFarLeft(HorizontalScrollView horizontalScrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onLeft(horizontalScrollView);
                    }
                }

                @Override
                public void onScrollFarRight(HorizontalScrollView horizontalScrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onRight(horizontalScrollView);
                    }
                }
            });
        } else {
            createScrollView(unlockScrollView, tableFirstDataList, true);
            scrollViewList.add(unlockScrollView);
            unlockScrollView.setOnScrollChangeListener(new CustomHorizontalScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChanged(HorizontalScrollView horizontalScrollView, int x, int y) {
                    changeAllScrollView(x, y);
                }

                @Override
                public void onScrollFarLeft(HorizontalScrollView horizontalScrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onLeft(horizontalScrollView);
                    }
                }

                @Override
                public void onScrollFarRight(HorizontalScrollView horizontalScrollView) {
                    if (onTableViewRangeListener != null){
                        onTableViewRangeListener.onRight(horizontalScrollView);
                    }
                }
            });
        }
    }

    /**
     * 构造滚动视图
     * @param scrollView
     * @param dataList
     * @param isFirstRow
     */
    private void createScrollView(HorizontalScrollView scrollView, List<String> dataList, boolean isFirstRow){
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < dataList.size(); i++){
            //构造单元格
            TextView textView = new TextView(context);
            if (isFirstRow){
                textView.setTextColor(ContextCompat.getColor(context, tableHeaderTextColor));
            } else {
                //textView.setTextColor(ContextCompat.getColor(context, tableContentTextColor));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textViewSize);
            textView.setGravity(Gravity.CENTER);
            textView.setText(dataList.get(i));
            Log.d("JsonList", "dataList.value:" + dataList.get(i));
            //设置布局
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(cellPadding, cellPadding, cellPadding, cellPadding);
            textView.setLayoutParams(textViewParams);
            ViewGroup.LayoutParams textViewParamsCopy = textView.getLayoutParams();
            if (isLockFirstColumn){
                textViewParamsCopy.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(i + 1));
            } else {
                textViewParamsCopy.width = DisplayUtil.dip2px(context, columnMaxWidthList.get(i));
            }
            linearLayout.addView(textView);
            //画分隔线
            if (i != dataList.size() - 1){
                View spiltView = new View(context);
                ViewGroup.LayoutParams spiltViewParams = new ViewGroup.LayoutParams(DisplayUtil.dip2px(context, 1),
                        ViewGroup.LayoutParams.MATCH_PARENT);
                spiltView.setLayoutParams(spiltViewParams);
                if (isFirstRow){
                    spiltView.setBackgroundColor(ContextCompat.getColor(context, R.color.md_white));
                } else {
                    spiltView.setBackgroundColor(ContextCompat.getColor(context, R.color.md_grey_400));
                }
                linearLayout.addView(spiltView);
            }
        }
        scrollView.addView(linearLayout);
    }

    /**
     * 根据最大最小值，计算textView的宽度
     * @param textView
     * @param text
     * @return
     */
    private int measureTextWidth(TextView textView, String text){
        if (textView != null){
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            int width = DisplayUtil.px2dip(context, layoutParams.leftMargin)
                        + DisplayUtil.px2dip(context, layoutParams.rightMargin)
                        + getTextViewWidth(textView, text);
            if (width <= minColumnWidth){
                return minColumnWidth;
            } else if (width > minColumnWidth && width < maxColumnWidth){
                return width;
            } else if (width >= maxColumnWidth){
                //return maxColumnWidth;
                return width;
            }
        }
        return 0;
    }

    /**
     * 根据文字计算textView的宽度
     * @param textView
     * @param text
     * @return
     */
    private int getTextViewWidth(TextView textView, String text){
        if (textView != null){
            TextPaint paint = textView.getPaint();
            return DisplayUtil.px2dip(context, (int) paint.measureText(text));
        }
        return 0;
    }

    /**
     * 改变指定列指定宽度
     * @param columnNum
     * @param columnWidth
     */
    private void changeColumnWidth(int columnNum, int columnWidth){
        if (columnMaxWidthList != null && columnMaxWidthList.size() > 0){
            if (columnNum < columnMaxWidthList.size() && columnNum >= 0 ){
                columnMaxWidthList.set(columnNum, columnWidth + DisplayUtil.px2dip(context, 15) * 2);
            }
        }
    }

    /**
     * 计算TextView高度
     * @param textView
     * @param text
     * @return
     */
    private int measureTextViewHeight(TextView textView, String text){
        if (textView != null){
            int height = getTextViewHeight(textView, text);
            if (height < minRowHeight){
                return minRowHeight;
            } else if (height > minRowHeight && height < maxRowHeight){
                return height;
            } else {
                //return maxRowHeight;
                return height;
            }
        }
        return 0;
    }

    /**
     * 根据文字计算textView的宽度
     * @param textView
     * @param text
     * @return
     */
    private int getTextViewHeight(TextView textView, String text){
        if (textView != null){
            int width = measureTextWidth(textView, text);
            TextPaint textPaint = textView.getPaint();
            StaticLayout staticLayout = new StaticLayout(text, textPaint, DisplayUtil.dip2px(context, width), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
            int height = DisplayUtil.px2dip(context, staticLayout.getHeight());
            return height;
        }
        return 0;
    }

    /**
     * 根据文字和指定宽度计算宽度
     * @param textView
     * @param text
     * @param width
     * @return
     */
    private int getTextViewHeight(TextView textView, String text, int width){
        if (textView != null){
            TextPaint textPaint = textView.getPaint();
            StaticLayout staticLayout = new StaticLayout(text, textPaint, DisplayUtil.dip2px(context, width), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
            int height = DisplayUtil.px2dip(context, staticLayout.getHeight());
            return height;
        }
        return 0;
    }

    /**
     *改变所有滚动视图的位置
     * @param x
     * @param y
     */
    private void changeAllScrollView(int x, int y){
        if (scrollViewList.size() > 0){
            if (onTableViewListener != null){
                onTableViewListener.onTableViewScrollChange(x, y);
            }
            for (int i = 0; i < scrollViewList.size(); i++){
                HorizontalScrollView scrollView= scrollViewList.get(i);
                scrollView.scrollTo(x, y);
            }
        }
    }

    /**
     * 横向滚动监听
     */
    public interface OnTableViewListener{
        /**
         * 滚动监听
         * @param x
         * @param y
         */
        void onTableViewScrollChange(int x, int y);
    }

    public interface OnTableViewRangeListener{
        /**
         * 说明最右侧
         * @param view
         */
        void onLeft(HorizontalScrollView view);

        /**
         * 说明最左侧
         * @param view
         */
        void onRight(HorizontalScrollView view);
    }

    /**
     * 上拉刷新，下拉加载
     */
    public interface OnLoadingListener {
        /**
         * 下拉刷新
         * @param xRecyclerView
         * @param tableDataList
         */
        void onRefresh(XRecyclerView xRecyclerView, ArrayList<ArrayList<String>> tableDataList);

        /**
         * 上拉加载
         * @param xRecyclerView
         * @param tableDataList
         */
        void onLoadMore(XRecyclerView xRecyclerView, ArrayList<ArrayList<String>> tableDataList);
    }

    /**
     * Item点击事件
     */
    public interface OnItemClickListener {
        /**
         * @param item
         * @param position
         */
        void onItemClick(View item, int position);
    }

    /**
     * Item长按事件
     */
    public interface OnItemLongClickListener{
        /**
         * @param item
         * @param position
         */
        void onItemLongClick(View item, int position);
    }

    public CustomTable setLockFirstRow(boolean lockFirstRow) {
        isLockFirstRow = lockFirstRow;
        return this;
    }

    public CustomTable setLockFirstColumn(boolean lockFirstColumn) {
        isLockFirstColumn = lockFirstColumn;
        return this;
    }

    public CustomTable setMaxColumnWidth(int maxColumnWidth) {
        this.maxColumnWidth = maxColumnWidth;
        return this;
    }

    public CustomTable setMinColumnWidth(int minColumnWidth) {
        this.minColumnWidth = minColumnWidth;
        return this;
    }

    public CustomTable setFirstRowBackgroundColor(int firstRowBackgroundColor) {
        this.firstRowBackgroundColor = firstRowBackgroundColor;
        return this;
    }

    public CustomTable setNullableString(String nullableString) {
        this.nullableString = nullableString;
        return this;
    }

    public CustomTable setTextViewSize(int textViewSize) {
        this.textViewSize = textViewSize;
        return this;
    }

    public CustomTable setMaxRowHeight(int maxRowHeight) {
        this.maxRowHeight = maxRowHeight;
        return this;
    }

    public CustomTable setMinRowHeight(int minRowHeight) {
        this.minRowHeight = minRowHeight;
        return this;
    }

    public CustomTable setTableHeaderTextColor(int tableHeaderTextColor) {
        this.tableHeaderTextColor = tableHeaderTextColor;
        return this;
    }

    public CustomTable setTableContentTextColor(int tableContentTextColor) {
        this.tableContentTextColor = tableContentTextColor;
        return this;
    }

    public CustomTable setOnTableViewListener(OnTableViewListener onTableViewListener) {
        this.onTableViewListener = onTableViewListener;
        return this;
    }

    public CustomTable setOnTableViewRangeListener(OnTableViewRangeListener onTableViewRangeListener) {
        this.onTableViewRangeListener = onTableViewRangeListener;
        return this;
    }

    public CustomTable setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.onLoadingListener = onLoadingListener;
        return this;
    }

    public CustomTable setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public CustomTable setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    public CustomTable setOnItemSelector(int onItemSelector) {
        this.onItemSelector = onItemSelector;
        return this;
    }

    public CustomTable setCellPadding(int cellPadding) {
        this.cellPadding = DisplayUtil.dip2px(context, cellPadding);
        return this;
    }

    public CustomTable setChangeColumns(HashMap<Integer, Integer> changeColumns) {
        this.changeColumns = changeColumns;
        return this;
    }

    public CustomTable setTableFirstDataList(ArrayList<String> tableFirstDataList) {
        this.tableFirstDataList = tableFirstDataList;
        return this;
    }

    public CustomTable setTableColumnDataList(ArrayList<String> tableColumnDataList) {
        this.tableColumnDataList = tableColumnDataList;
        return this;
    }

    public CustomTable setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
        return this;
    }

    public CustomTable setTableRowDataList(ArrayList<ArrayList<String>> tableRowDataList) {
        this.tableRowDataList = tableRowDataList;
        return this;
    }

    public CustomTable setColumnMaxWidthList(ArrayList<Integer> columnMaxWidthList) {
        this.columnMaxWidthList = columnMaxWidthList;
        return this;
    }

    public CustomTable setRowMaxHeightList(ArrayList<Integer> rowMaxHeightList) {
        this.rowMaxHeightList = rowMaxHeightList;
        return this;
    }

    public CustomTable setScrollViewList(ArrayList<HorizontalScrollView> scrollViewList) {
        this.scrollViewList = scrollViewList;
        return this;
    }

    public CustomTable setColumnTitleView(TextView columnTitleView) {
        this.columnTitleView = columnTitleView;
        return this;
    }

    public void setTableDataList(ArrayList<ArrayList<String>> tableDataList){
        this.tableDataList = tableDataList;
        tableFirstDataList.clear();
        tableColumnDataList.clear();
        tableRowDataList.clear();
        columnMaxWidthList.clear();
        rowMaxHeightList.clear();
        initData();
        customTableAdapter.notifyDataSetChanged();
    }

    public CustomTable setColumnWidth(int columnNum, int columnWidth) {
        if (changeColumns.containsKey(columnNum)) {
            changeColumns.remove(columnNum);
        }
        changeColumns.put(columnNum, columnWidth);
        return this;
    }

    public ArrayList<Integer> getColumnMaxWidthList() {
        return columnMaxWidthList;
    }

    public ArrayList<Integer> getRowMaxHeightList() {
        return rowMaxHeightList;
    }

    public LinearLayout getLockHeaderView() {
        return lockHeaderView;
    }

    public LinearLayout getUnlockHeaderView() {
        return unlockHeaderView;
    }

    public XRecyclerView getScrollTableView() {
        return scrollTableView;
    }

    public ArrayList<HorizontalScrollView> getScrollViewList() {
        return scrollViewList;
    }
}
