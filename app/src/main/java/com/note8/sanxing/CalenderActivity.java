package com.note8.sanxing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class CalenderActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        customizeCalendar();
    }

    void customizeCalendar() {
        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.calendarView);
        mcv.setTopbarVisible(false);  //  隐藏TopBar 改用自己定制的TopBar
        mcv.setWeekDayLabels(new String[]{"S", "M", "T", "W", "T", "F", "S"});
    }
}
