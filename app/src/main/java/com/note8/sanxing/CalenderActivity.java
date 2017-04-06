package com.note8.sanxing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.note8.sanxing.adapters.TimeLineAdapter;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.List;

public class CalenderActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private View backgroundView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<Answer> mDataList = Answer.sampleAnswerData;

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
        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
        Log.d("Cal", "initial view");
    }
}