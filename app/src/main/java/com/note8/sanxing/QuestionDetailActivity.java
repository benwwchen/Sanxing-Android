package com.note8.sanxing;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.note8.sanxing.adapters.BroadcastQuestionsAnswersAdapter;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.models.Question;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.ui.StatusBarUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDetailActivity extends AppCompatActivity implements BroadcastQuestionsAnswersAdapter.BroadcastQustionsAnswersAdapterCallback {

    private Answer mAnswer;
    private ImageButton returnBtn;
    private ImageButton commentBtn;
    private ImageButton publicStatueBtn;
    private TextView moodDescribe;
    private TextView questionTitle;
    private TextView answerTxt;
    private ImageView answerImg;
    private TextView answerTimesCount;
    private ImageView mArrowImageView;
    private NestedScrollView mNestedScrollView;
    private RecyclerView mAnswersRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String questionId;
    private int publicStatusInt;
    private Integer moodIndexInt;
    private String questionTitleString;
    private String answerTxtString;
    private Integer answerTimesCountInt;
    private int answerImgInt;
    private double intervalOfProgress = 20;
    private ArrayList<Answer> mAnswers;

    private int answerScrollViewOriginalHeight = -1;

    // adapter, handlers
    private BroadcastQuestionsAnswersAdapter mBroadcastQuestionsAnswersAdapter;
    private Handler mDataHandler;
    private Handler mFavoriteHandler;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        mContext = QuestionDetailActivity.this;

        //设置状态栏颜色为白色且图标为对比色
        StatusBarCompat.setStatusBarColor(this, Color.parseColor("#ffffff"),true);

        findViewById();   //获取页面控件
        accessData();     //接收数据
        setViewContents();//设置控件内容
        if (questionId != null) {
            refresh(); //刷新精选回答列表
        } else {
            // 隐藏"精选回答"
        }


        //点击返回按钮，结束当前页面
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //点击返回按钮时存在动效
        returnBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.return_press));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.return_release));
                }
                return false;
            }
        });
        //查看问题详情时，点击评论按钮存在动效
        commentBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.comment_press));
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.comment_release));
                }
                return false;
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"评论功能暂未开通，敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
        //假设查看已回答的问题详情时不能更改公开状态
        /*publicStatueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(publicStatue[0] == 0)
                    publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));
                else
                    publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
                publicStatue[0] = 1 - publicStatue[0];
            }
        });*/
    }

    //获取控件
    private void findViewById(){
        returnBtn = (ImageButton)findViewById(R.id.answerPageReturn_detail);
        commentBtn = (ImageButton)findViewById(R.id.answerPageComment);
        publicStatueBtn = (ImageButton)findViewById(R.id.publicStatue_detail);
        moodDescribe = (TextView)findViewById(R.id.moodDescribe_detail);
        questionTitle = (TextView)findViewById(R.id.question_detail);
        answerTxt = (TextView)findViewById(R.id.answerTxt_detail);
        answerImg = (ImageView)findViewById(R.id.answerImg_detail);
        answerTimesCount = (TextView)findViewById(R.id.answer_times_count);

        mArrowImageView = (ImageView) findViewById(R.id.image_view_arrow);
        mArrowImageView.setVisibility(View.INVISIBLE);

        mAnswersRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_broadcast_questions_answers);
        // set layout manager
        mAnswersRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // swipe refresh layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.layout_swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorBrilliant);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            public void onRefresh() {
                if (questionId != null && !questionId.isEmpty()) {
                    updateData();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mNestedScrollView = (NestedScrollView) findViewById(R.id.nested_scroll_view_answer);
        mNestedScrollView.setNestedScrollingEnabled(true);
        setAnswerScrollViewHeight(true);
    }

    private void setAnswerScrollViewHeight(boolean isNoAnswers) {
        ViewGroup.LayoutParams layoutParams = mNestedScrollView.getLayoutParams();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        if (answerScrollViewOriginalHeight == -1) {
            answerScrollViewOriginalHeight = dm.heightPixels;
        }
        if (isNoAnswers) {
            layoutParams.height = (int) (answerScrollViewOriginalHeight * 1.2 / 2);
        } else {
            layoutParams.height = answerScrollViewOriginalHeight / 2;
        }
        mNestedScrollView.setLayoutParams(layoutParams);
    }

    //接收问题相关数据
    private void accessData(){
        Bundle bundleFromQuestion = getIntent().getExtras();
        int type = bundleFromQuestion.getInt("type");
        //boolean fromToday = bundleFromQuestion.getBoolean("today");

        publicStatusInt = bundleFromQuestion.getInt("publicStatus");
        moodIndexInt = bundleFromQuestion.getInt("mood");
        questionId = bundleFromQuestion.getString("questionId");
        questionTitleString = bundleFromQuestion.getString("title");
        answerTxtString = bundleFromQuestion.getString("answerTxt");
        answerImgInt = bundleFromQuestion.getInt("answerImg");
        answerTimesCountInt = bundleFromQuestion.getInt("answerCount");
        /*
        Intent intent = getIntent();
        mAnswer = (Answer) intent.getSerializableExtra("answer");
        publicStatusInt = mAnswer.getPublicStatus();
        moodIndexInt = mAnswer.getMood();
        questionTitleString = mAnswer.getQuestionContent();
        answerTxtString = mAnswer.getAnswerId();
        //answerImgInt = bundleFromQuestion.getInt("answerImg");
        */
    }

    //设置控件内容
    private void setViewContents(){
        //设置公开状态
        if(publicStatusInt == 0)
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
        else
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));

        //设置心情描述文本
        if(moodIndexInt == 0) moodDescribe.setText("#当时我感觉整个人都不好了#");
        else if(moodIndexInt == 100) moodDescribe.setText("#当时我兴奋到了极点#");
        else if(moodIndexInt < intervalOfProgress) moodDescribe.setText("#当时我有点烦躁#");
        else if(moodIndexInt < 2*intervalOfProgress) moodDescribe.setText("#当时我感觉到了一股淡淡的忧伤#");
        else if(moodIndexInt < 3*intervalOfProgress) moodDescribe.setText("#当时我内心毫无波澜#");
        else if(moodIndexInt < 4*intervalOfProgress) moodDescribe.setText("#当时我心情还算愉悦#");
        else moodDescribe.setText("#当时我有点想笑#");

        //设置问题的标题、回答、历史回答次数
        questionTitle.setText(questionTitleString);
        answerTxt.setText(answerTxtString);
        if(answerImgInt!=0){
            answerImg.setVisibility(View.VISIBLE);
            answerImg.setImageDrawable(getResources().getDrawable(answerImgInt));
        }
        if (answerTimesCountInt == 0) answerTimesCountInt++;
        answerTimesCount.setText("你答过该问题共"+answerTimesCountInt+"次");

        setUpCuratedAnswers();
    }

    private void setUpCuratedAnswers() {
        // load curated answers
        mAnswers = new ArrayList<>();
        mBroadcastQuestionsAnswersAdapter = new BroadcastQuestionsAnswersAdapter(mAnswers, mContext, this);
        mAnswersRecyclerView.setAdapter(mBroadcastQuestionsAnswersAdapter);
        mAnswersRecyclerView.setFocusable(false);

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

                // remove self today questions answers
//                String username = SanxingApiClient.getInstance(mContext).getUser().getUsername();
//                Iterator<Answer> iterator = answers.iterator();
//                while (iterator.hasNext()) {
//                    Answer answer = iterator.next();
//                    if (answer.getAnswerer().username.equals(username)) iterator.remove();
//                }

                // update data & notify the adapter
                mAnswers.clear();
                mAnswers.addAll(answers);
                if (!mAnswers.isEmpty()) {
                    // show the arrow and adjust the height
                    mArrowImageView.setVisibility(View.VISIBLE);
                    setAnswerScrollViewHeight(false);
                }
                mBroadcastQuestionsAnswersAdapter.notifyDataSetChanged();
                if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        };
    }

    // trigger swipe to refresh
    public void refresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                updateData();
            }
        });
    }

    private void updateData() {
        SanxingApiClient.getInstance(mContext).getPublicDailyQuestionsAnswers(questionId, mDataHandler);
    }

    @Override
    public void onFavoriteButtonClick(String answerId, Boolean favorite) {
        Handler favoriteAnswerHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                boolean isSuccess;

                if(msg.what == 1){
                    isSuccess = (boolean) msg.obj;
                } else {
                    Log.d("error", (String) msg.obj);
                    Toast.makeText(mContext, "错误:" + msg.obj, Toast.LENGTH_SHORT).show();
                    isSuccess = false;
                }

                updateData();
            }
        };
        SanxingApiClient.getInstance(mContext).addOrRemoveAFavoriteAnswer(answerId, favorite, favoriteAnswerHandler);
    }
}