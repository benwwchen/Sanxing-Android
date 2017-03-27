package com.note8.sanxing.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.note8.sanxing.QuestionDetailActivity;
import com.note8.sanxing.R;
import com.note8.sanxing.models.LikedAnswersClass;

import java.util.List;

/**
 * Created by WIKI on 2017/3/26 0026.
 */
public class LikeAnswersAdapter extends ArrayAdapter<LikedAnswersClass> {

    private int resourceId;

    public LikeAnswersAdapter(Context context, int resource, List<LikedAnswersClass> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LikedAnswersClass likedAnswers = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView titleTxt = (TextView) view.findViewById(R.id.me_like_answer_title);
        ImageView portraitImg = (ImageView) view.findViewById(R.id.me_like_answer_portrait);
        TextView answerTxt = (TextView) view.findViewById(R.id.me_like_answer_txt);
        ImageView answerImg = (ImageView) view.findViewById(R.id.me_like_answer_img);
        final Button goBtn = (Button) view.findViewById(R.id.me_like_answer_go_btn);

        titleTxt.setText(likedAnswers.title);
        portraitImg.setImageResource(likedAnswers.portraitImg);
        if (!likedAnswers.hasAnswerImg) answerImg.setVisibility(View.GONE);
        if(likedAnswers.hasAnswerImg)   answerImg.setImageResource(likedAnswers.answerImg);
        if(!likedAnswers.hasAnswerTxt)  answerTxt.setVisibility(View.GONE);
        if(likedAnswers.hasAnswerTxt)   answerTxt.setText(likedAnswers.answerTxt);
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putString("title", likedAnswers.title);
//              bundle.putString("date", today.date);
//                if (position == 0) {  //  第一个item进入问题回答界面
//                    bundle.putBoolean("newAns", true);
//                } else {              //  其他进入当天问题及回答浏览界面
//                    bundle.putBoolean("newAns", false);
//                }
                intent = new Intent(view.getContext(), QuestionDetailActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}


