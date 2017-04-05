package com.note8.sanxing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.note8.sanxing.utils.ui.StatusBarUtils;

public class QuestionDetailActivity extends AppCompatActivity {

    private ImageButton returnBtn;
    private ImageButton commentBtn;
    private ImageButton publicStatueBtn;
    private TextView moodDescribe;
    private TextView questionTitle;
    private TextView answerTxt;
    private ImageView answerImg;
    private TextView answerTimesCount;

    private int publicStatusInt;
    private double moodIndexDouble;
    private String questionTitleString;
    private String answerTxtString;
    private String answerTimesCountString;
    private int answerImgInt;
    private double intervalOfProgress = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBar();   //设置状态栏颜色为白色且图标为对比色
        findViewById();   //获取页面控件
        accessData();     //接收数据
        setViewContents();//设置控件内容

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

    //设置手机状态栏颜色为白色且通知图标为对比色
    private void setStatusBar(){
        //隐藏标题栏并使状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_question_detail);
        ViewGroup decorViewGroup = (ViewGroup) getWindow().getDecorView();
        View statusBarView = new View(getWindow().getContext());
        int statusBarHeight = getStatusBarHeight(getWindow().getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        decorViewGroup.addView(statusBarView);
    }

    //获取手机状态栏高度
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
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
    }

    //接受问题相关数据
    private void accessData(){
        Bundle bundleFromQuestion = getIntent().getExtras();
        int type = bundleFromQuestion.getInt("type");
        //boolean fromToday = bundleFromQuestion.getBoolean("today");

        publicStatusInt = bundleFromQuestion.getInt("publicStatus");
        moodIndexDouble = bundleFromQuestion.getDouble("moodIndex");
        questionTitleString = bundleFromQuestion.getString("title");
        answerTxtString = bundleFromQuestion.getString("answerTxt");
        answerImgInt = bundleFromQuestion.getInt("answerImg");
    }

    //设置控件内容
    private void setViewContents(){
        //设置公开状态
        if(publicStatusInt == 0)
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
        else
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));

        //设置心情描述文本
        if(moodIndexDouble == 0) moodDescribe.setText("#当时我感觉整个人都不好了#");
        else if(moodIndexDouble == 100) moodDescribe.setText("#当时我兴奋到了极点#");
        else if(moodIndexDouble < intervalOfProgress) moodDescribe.setText("#当时我有点烦躁#");
        else if(moodIndexDouble < 2*intervalOfProgress) moodDescribe.setText("#当时我感觉到了一股淡淡的忧伤#");
        else if(moodIndexDouble < 3*intervalOfProgress) moodDescribe.setText("#当时我内心毫无波澜#");
        else if(moodIndexDouble < 4*intervalOfProgress) moodDescribe.setText("#当时我心情还算愉悦#");
        else moodDescribe.setText("#当时我有点想笑#");

        //设置问题的标题、回答、历史回答次数
        questionTitle.setText(questionTitleString);
        answerTxt.setText(answerTxtString);
        answerImg.setVisibility(View.VISIBLE);
        //answerImg.setImageBitmap(ThumbnailUtils.extractThumbnail(answerImgBitmap, 1000, 1000));
        //answerTimesCount.setText(answerTimesCountString);
    }
}