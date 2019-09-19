package com.example.lc.materialuitest.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lc.materialuitest.R;
import com.example.lc.materialuitest.bean.ColorSelect;
import com.example.lc.materialuitest.bean.LocationSelect;
import com.example.lc.materialuitest.bean.SizeSelect;
import com.example.lc.materialuitest.bean.TimeFormatSelect;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CameraSettingDialog extends Dialog {

    @BindView(R.id.ns_dialog_camera_setting_text_color)
    NiceSpinner nsColor;                    //文字颜色选择
    @BindView(R.id.ns_dialog_camera_setting_text_size)
    NiceSpinner nsSize;                     //文字大小选择
    @BindView(R.id.ns_dialog_camera_setting_watermark_location)
    NiceSpinner nsWatermarkLocation;                 //水印位置选择
    @BindView(R.id.ns_dialog_camera_setting_time_format)
    NiceSpinner nsTimeFormat;               //时间格式选择

    private Context context;

    private List<String> colorList = null;
    private List<String> sizeList = null;
    private List<String> locationList = null;
    private List<String> timeFormatList = null;

    private OnColorSelectListener onColorSelectListener = null;
    private OnSizeSelectListener onSizeSelectListener = null;
    private OnLocationSelectListener onLocationSelectListener = null;
    private OnTimeFormatSelectListener onTimeFormatSelectListener = null;

    public CameraSettingDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView(){
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_camera_setting, null);
        setContentView(view);
        ButterKnife.bind(this);

        initList();
    }

    private void initList(){
        //设置文字颜色选择下拉框
        colorList = ColorSelect.getAllColor();
        nsColor.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                int color = ColorSelect.getColorByDesc(colorList.get(position));
                //如果背景色为黑色，提示文字切换为白色，否则一直显示为黑色
                if(color == ColorSelect.BLACK.getColor()){
                    nsColor.setTextColor(context.getResources().getColor(ColorSelect.WHITE.getColor()));
                }else {
                    nsColor.setTextColor(context.getResources().getColor(ColorSelect.BLACK.getColor()));
                }
                nsColor.setBackgroundColor(context.getResources().getColor(color));
                //设置水印颜色
                if(onColorSelectListener != null){
                    onColorSelectListener.onColorSelect(context.getResources().getColor(color));
                }
            }
        });
        nsColor.attachDataSource(colorList);

        //设置文字大小选择下拉框
        sizeList = SizeSelect.getAllSize();
        nsSize.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                int size = SizeSelect.getSizeByDesc(sizeList.get(position));
                if(onSizeSelectListener != null){
                    onSizeSelectListener.onSizeSelect(context.getResources().getDimension(size));
                }
            }
        });
        nsSize.attachDataSource(sizeList);

        //设置水印位置选择下拉框
        locationList = LocationSelect.getAllLocation();
        nsWatermarkLocation.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                int location = LocationSelect.getLocationByDesc(locationList.get(position));
                if(onLocationSelectListener != null){
                    onLocationSelectListener.onLocationSelect(location);
                }
            }
        });
        nsWatermarkLocation.attachDataSource(locationList);

        //设置时间格式选择下拉框
        timeFormatList = TimeFormatSelect.getAllFormat();
        nsTimeFormat.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String format = TimeFormatSelect.getDescByPosition(position);
                if(onTimeFormatSelectListener != null){
                    onTimeFormatSelectListener.onTimeFormatSelect(format);
                }
            }
        });
        nsTimeFormat.attachDataSource(timeFormatList);
    }

    public interface OnColorSelectListener{
        void onColorSelect(int color);
    }

    public interface OnSizeSelectListener{
        void onSizeSelect(float size);
    }

    public interface OnLocationSelectListener{
        void onLocationSelect(int gravity);
    }

    public interface OnTimeFormatSelectListener{
        void onTimeFormatSelect(String format);
    }

    public void setOnColorSelectListener(OnColorSelectListener onColorSelectListener) {
        this.onColorSelectListener = onColorSelectListener;
    }

    public void setOnSizeSelectListener(OnSizeSelectListener onSizeSelectListener) {
        this.onSizeSelectListener = onSizeSelectListener;
    }

    public void setOnLocationSelectListener(OnLocationSelectListener onLocationSelectListener) {
        this.onLocationSelectListener = onLocationSelectListener;
    }

    public void setOnTimeFormatSelectListener(OnTimeFormatSelectListener onTimeFormatSelectListener) {
        this.onTimeFormatSelectListener = onTimeFormatSelectListener;
    }
}
