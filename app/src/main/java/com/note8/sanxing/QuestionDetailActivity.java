package com.note8.sanxing;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.note8.sanxing.utils.ui.StatusBarUtils;

public class QuestionDetailActivity extends AppCompatActivity {

    private ImageButton returnBtn;
    private ImageButton commentBtn;
    private ImageButton publicStatueBtn;
    private int[] publicStatue = {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏并使状态栏透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_question_detail);
        //设置状态栏颜色为白色且图标为对比色
        setStatusBar();
        //获取页面控件
        findViewById();

        //设置公开状态
        if(publicStatue[0] == 0)
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
        else
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));

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
    }
}
