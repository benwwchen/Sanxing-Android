package com.note8.sanxing;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.ui.CustomGradientDrawable;
import com.note8.sanxing.utils.ui.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity
    implements OnDateSelectedListener, OnMonthChangedListener {
    private RecyclerView mRecyclerView;
    private View mBackgroundView;
    private TimeLineAdapter mTimeLineAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Handler mDataHandler;

    private int mIndex;
    private boolean move = false;
    //  support for mcv
    private TextView topBarYear;
    private TextView topBarMonth;
    private String[] monthStr = new String[] {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };
    //  TODO: Get Real Data From the Server
    private List<Answer> mDataList;

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
        mcv.setWeekDayLabels(new String[] { "日", "一", "二", "三", "四", "五", "六" });
        mcv.setOnDateChangedListener(this);
        mcv.setOnMonthChangedListener(this);
        mcv.setDateSelected(calendar, true);

        String yearStr = calendar.get(Calendar.YEAR) + "年";
        this.topBarYear.setText(yearStr);
        this.topBarMonth.setText(calendar.get(Calendar.MONTH) + "月");
    }

    private void initTimeline() {
        this.mRecyclerView = (RecyclerView) findViewById(R.id.cal_timeline_list);
        this.mRecyclerView.setHasFixedSize(true);
        initLayoutManager();
        initAdapter();
        loadData();
    }

    private void initLayoutManager() {
        mLinearLayoutManager = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        this.mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void initAdapter() {
        mDataList = new ArrayList<>();
        this.mTimeLineAdapter = new TimeLineAdapter(this.mDataList);
        mTimeLineAdapter.setOnItemClickListener(onItemClickListener);
        this.mRecyclerView.setAdapter(this.mTimeLineAdapter);
        this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (move && newState == RecyclerView.SCROLL_STATE_IDLE){
                    move = false;
                    int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                    if ( 0 <= n && n < mRecyclerView.getChildCount()){
                        int top = mRecyclerView.getChildAt(n).getTop();
                        mRecyclerView.smoothScrollBy(0, top);
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //在这里进行第二次滚动（最后的100米！）
                if (false) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = mIndex - mLinearLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < mRecyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = mRecyclerView.getChildAt(n).getTop();
                        //最后的移动
                        mRecyclerView.smoothScrollBy(0, top);
                    }
                }
            }
        });
        mDataHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Answer> answers;

                if (msg.what == SanxingApiClient.SUCCESS_CODE) {
                    answers = (List<Answer>) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    answers = Answer.sampleAnswerData;
                }

                String prevDate = "";
                for (int i = 0; i < answers.size(); i++) {
                    if (!answers.get(i).getDate().equals(prevDate)) {
                        prevDate = answers.get(i).getDate();
                        answers.get(i).setFirst(true);
                    }
                }

                // update data & notify the adapter
                mDataList.clear();
                mDataList.addAll(answers);
                mTimeLineAdapter.notifyDataSetChanged();
            }
        };
    }

    private void loadData() {
        SanxingApiClient.getInstance(CalendarActivity.this).getAnswerHistory(mDataHandler);
    }

    /**
     * Item click listener, start AnswerActivity / QuestionDetailActivity
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
            Answer answer = mDataList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("questionId", answer.getQuestionId());
            bundle.putString("title", answer.getQuestionContent());
            bundle.putString("answerTxt", answer.getContent());
            bundle.putInt("mood", answer.getMood());
            bundle.putInt("publicStatus", answer.isPublic()? 1:0);
            Intent intent = new Intent(CalendarActivity.this, QuestionDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private void smoothScroll(CalendarDay d) {
        //  smooth sliding to suitable date
        String date = d.getYear() + "-" + (d.getMonth() + 1) + "-" + d.getDay();
        Integer destPos = Answer.positionMap.get(date);
        //Toast.makeText(this, destPos + "", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, date + "", Toast.LENGTH_SHORT).show();
        if (destPos != null) {
            moveToPosition(destPos);
            mIndex = destPos;
        }
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLinearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem ){
            //当要置顶的项在当前显示的第一个项的前面时
            mRecyclerView.smoothScrollToPosition(n);
        }else if ( n <= lastItem ){
            //当要置顶的项已经在屏幕上显示时
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.smoothScrollBy(0, top);
        }else{
            //当要置顶的项在当前显示的最后一项的后面时
            mRecyclerView.smoothScrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
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