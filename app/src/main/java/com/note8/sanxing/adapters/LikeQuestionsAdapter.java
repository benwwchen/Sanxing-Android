package com.note8.sanxing.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.note8.sanxing.QuestionDetailActivity;
import com.note8.sanxing.R;
import com.note8.sanxing.models.LikedQuestionsClass;

import java.util.List;

/**
 * Created by WIKI on 2017/3/24 0024.
 */
public class LikeQuestionsAdapter extends ArrayAdapter<LikedQuestionsClass>{

    private int resourceId;

    public LikeQuestionsAdapter(Context context, int resource, List<LikedQuestionsClass> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LikedQuestionsClass likedQuestions = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView titleTxt = (TextView) view.findViewById(R.id.me_like_question_title);
        TextView timeTxt = (TextView) view.findViewById(R.id.me_like_question_time);
        ImageView backgroundImg = (ImageView) view.findViewById(R.id.me_like_question_img);
        LinearLayout likeQuestionsLayout = (LinearLayout)view.findViewById(R.id.like_questions_layout);

        titleTxt.setText(likedQuestions.title);
        timeTxt.setText(likedQuestions.time);
        if (likedQuestions.isBroadcastQuestion) {
            backgroundImg.setImageResource(likedQuestions.broadcastImg);
        }else {
            backgroundImg.setVisibility(View.GONE);
        }
        likeQuestionsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                Bundle bundle = new Bundle();
                bundle.putString("title", likedQuestions.title);
                if(!likedQuestions.isBroadcastQuestion)
                    bundle.putString("answerTxt", likedQuestions.answerTxt);
                if(likedQuestions.answerImg!=0)
                    bundle.putInt("answerImg",likedQuestions.answerImg);
                bundle.putString("time", likedQuestions.time);
                intent = new Intent(view.getContext(), QuestionDetailActivity.class);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}


