package com.example.lc.materialuitest.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.lc.materialuitest.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PickerActivity extends AppCompatActivity {

    @BindView(R.id.btn_activity_picker_date)
    Button btnDate;
    @BindView(R.id.btn_activity_picker_time)
    Button btnTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_activity_picker_time, R.id.btn_activity_picker_date})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_activity_picker_date:
                pickerDate();
                break;
            case R.id.btn_activity_picker_time:
                pickerTime();
                break;
        }
    }

    private void pickerDate(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = String.format("%02d-%02d-%02d", year, (month-1), dayOfMonth);
                Snackbar.make(btnDate, date, Snackbar.LENGTH_SHORT).show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void pickerTime(){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format("%02d:%02d", hourOfDay, minute);
                Snackbar.make(btnDate, time, Snackbar.LENGTH_SHORT).show();
            }
        }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);

        timePickerDialog.show();
    }

}
