package com.note8.sanxing;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.note8.sanxing.adapters.TimeLineAdapter;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalenderActivity extends AppCompatActivity
    implements OnDateSelectedListener, OnMonthChangedListener {
    private RecyclerView mRecyclerView;
    private View mBackgroundView;
    private TimeLineAdapter mTimeLineAdapter;
    //  support for mcv
    private TextView topBarYear;
    private TextView topBarMonth;
    private String[] monthStr = new String[] {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    //  TODO: Get Real Data From the Server
    private List<Answer> mDataList = Answer.sampleAnswerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        this.initBackgroundGradient();
        this.initCalendar();
        this.initTimeline();
    }

    private void initBackgroundGradient() {
        // hide status bar color
        StatusBarUtils.setContentToTop(this);

        // set background gradient color
        this.mBackgroundView = findViewById(R.id.view_background);
        this.mBackgroundView.setBackground(new CustomGradientDrawable(
            new int[] { 0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b },
            new float[] { 0, 0.19f, 0.60f, 1 })
        );
    }

    private void initCalendar() {
        this.topBarYear = (TextView) findViewById(R.id.cal_top_bar_year);
        this.topBarMonth = (TextView) findViewById(R.id.cal_top_bar_mouth);

        Calendar calendar = Calendar.getInstance();
        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.cal_calendar_view);
        mcv.setTopbarVisible(false);  //  Hide top bar
        mcv.setWeekDayLabels(new String[] { "S", "M", "T", "W", "T", "F", "S" });
        mcv.setOnDateChangedListener(this);
        mcv.setOnMonthChangedListener(this);
        mcv.setDateSelected(calendar, true);

        String yearStr = calendar.get(Calendar.YEAR) + "";
        this.topBarYear.setText(yearStr);
        this.topBarMonth.setText(this.monthStr[calendar.get(Calendar.DAY_OF_MONTH)]);
    }

    private void initTimeline() {
        this.mRecyclerView = (RecyclerView) findViewById(R.id.cal_timeline_list);
        this.mRecyclerView.setHasFixedSize(true);
        initLayoutManager();
        initAdapter();
    }

    private void initLayoutManager() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false));
    }

    private void initAdapter() {
        this.mTimeLineAdapter = new TimeLineAdapter(this.mDataList);
        this.mRecyclerView.setAdapter(this.mTimeLineAdapter);
    }

    private void smoothScroll(CalendarDay d) {
        //  smooth sliding to suitable date
        String date = d.getYear() + "-" + (d.getMonth() + 1) + "-" + d.getDay();
        Integer destPos = Answer.positionMap.get(date);
        Toast.makeText(this, destPos + "", Toast.LENGTH_SHORT).show();
        if (destPos != null) {
            this.mRecyclerView.smoothScrollToPosition(destPos);
        }
    }

    //  implement OnDateSelectedListener interface
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView mcv, @NonNull CalendarDay date, boolean selected) {
        smoothScroll(date);
    }

    //  implement OnMonthChangedListener interface
    @Override
    public void onMonthChanged(MaterialCalendarView mcv, CalendarDay date) {
        String yearStr = date.getYear() + "";
        this.topBarYear.setText(yearStr);
        this.topBarMonth.setText(this.monthStr[date.getMonth()]);
    }
}