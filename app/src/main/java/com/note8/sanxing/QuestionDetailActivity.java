package com.note8.sanxing;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
        setContentView(R.layout.activity_question_detail);

        StatusBarUtils.setContentToTop(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findViewById();

        //���ù���״̬
        if(publicStatue[0] == 0)
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_invisible));
        else
            publicStatueBtn.setImageDrawable(getResources().getDrawable(R.drawable.scroll_visible));

        //������ذ�ť��������ǰҳ��
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //������ذ�ťʱ���ڶ�Ч
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
        //�鿴��������ʱ��������۰�ť���ڶ�Ч
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

        //����鿴�ѻش����������ʱ���ܸ��Ĺ���״̬
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

    private void findViewById(){
        returnBtn = (ImageButton)findViewById(R.id.answerPageReturn_detail);
        commentBtn = (ImageButton)findViewById(R.id.answerPageComment);
        publicStatueBtn = (ImageButton)findViewById(R.id.publicStatue_detail);
    }
}
