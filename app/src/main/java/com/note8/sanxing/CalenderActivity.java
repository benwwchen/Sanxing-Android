package com.note8.sanxing;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.note8.sanxing.timeLineModel.TimeLineAdapter;
import com.note8.sanxing.timeLineModel.TimeLineModel;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

public class CalenderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private View backgroundView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        initBackgroundGradient();
        customizeCalendar();
        initTimeline();
    }

    private void initBackgroundGradient() {
        // hide status bar color
        StatusBarUtils.setContentToTop(this);

        // set background gradient color
        CustomGradientDrawable gradientDrawable = new CustomGradientDrawable(
                new int[] {0xfff78ca0, 0xfff9748f, 0xfffd868c, 0xfffe9a8b},
                new float[] {0, 0.19f, 0.60f, 1});
        backgroundView = findViewById(R.id.view_background);
        backgroundView.setBackground(gradientDrawable);
    }

    void customizeCalendar() {
        MaterialCalendarView mcv = (MaterialCalendarView) findViewById(R.id.cal_calendar_view);
        mcv.setTopbarVisible(false);  //  隐藏TopBar 改用自己定制的TopBar
        mcv.setWeekDayLabels(new String[]{"S", "M", "T", "W", "T", "F", "S"});
        Log.d("Cal", "customize calendar");
    }

    void initTimeline() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cal_timeline_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        Log.d("Cal", "initial timeline");
        initView();
    }

    private void initView() {
        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
        Log.d("Cal", "initial view");
    }

    private void setDataListItems() {
        mDataList.add(new TimeLineModel("Item successfully delivered", "", "Happy"));
        mDataList.add(new TimeLineModel("Courier is out to delivery your order", "2017-02-12 08:00", "Happy"));
        mDataList.add(new TimeLineModel("Item has reached courier facility at New Delhi", "2017-02-11 21:00", "Happy"));
        mDataList.add(new TimeLineModel("Item has been given to the courier", "2017-02-11 18:00", "Happy"));
        mDataList.add(new TimeLineModel("Item is packed and will dispatch soon", "2017-02-11 09:30", "Happy"));
        mDataList.add(new TimeLineModel("Order is being readied for dispatch", "2017-02-11 08:00", "Happy"));
        mDataList.add(new TimeLineModel("Order processing initiated", "2017-02-10 15:00", "Happy"));
        mDataList.add(new TimeLineModel("Order confirmed by seller", "2017-02-10 14:30", "Happy"));
        mDataList.add(new TimeLineModel("Order placed successfully", "2017-02-10 14:00", "Happy"));
    }
}