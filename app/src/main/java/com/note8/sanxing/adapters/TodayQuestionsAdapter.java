package com.note8.sanxing.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.models.TodayQuestion;
import com.note8.sanxing.utils.ui.DateTimeUtils;
import com.note8.sanxing.utils.ui.VectorDrawableUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class TodayQuestionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<TodayQuestion> mTodayQuestions;
    private List<Answer> mAnswerList;
    Context mContext;

    public static enum ITEM_TYPE {
        ITEM_TYPE_TODAY_QUESTION(8);

        private int code;
        private ITEM_TYPE(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    // ViewHolder for today questions
    public static class TodayQuestionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView clockImageView;
        public TextView questionTextView;
        public Button answerButton;
        public Button favoriteButton;
        public LinearLayout contentView;

        OnItemClickListener mOnItemClickListener;

        public TodayQuestionsViewHolder(View itemView) {
            super(itemView);
            contentView = (LinearLayout) itemView.findViewById(R.id.content_view);
            contentView.setOnClickListener(this);
            clockImageView = (ImageView) itemView.findViewById(R.id.image_view_clock);
            questionTextView = (TextView) itemView.findViewById(R.id.text_view_today_question);
            answerButton = (Button) itemView.findViewById(R.id.button_answer);
            favoriteButton = (Button) itemView.findViewById(R.id.button_favorite);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TodayQuestionsAdapter(ArrayList<TodayQuestion> todayQuestions, List<Answer> answerList, Context context) {
        this.mContext = context;
        this.mTodayQuestions = todayQuestions;
        this.mAnswerList = answerList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_TODAY_QUESTION.getCode()) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_today_questions, parent, false);
            TodayQuestionsViewHolder viewHolder = new TodayQuestionsViewHolder(view);
            viewHolder.mOnItemClickListener = mOnItemClickListener;
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_timeline, parent, false);
            return new TimeLineViewHolder(view, viewType);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (holder instanceof TodayQuestionsViewHolder) {
            TodayQuestionsViewHolder viewHolder = (TodayQuestionsViewHolder) holder;
            bindTodayQuestionsView(viewHolder, position);
        } else if (holder instanceof TimeLineViewHolder) {
            TimeLineViewHolder viewHolder = (TimeLineViewHolder) holder;
            bindTimeLineView(viewHolder, position);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position < mTodayQuestions.size() ?
                ITEM_TYPE.ITEM_TYPE_TODAY_QUESTION.getCode() : TimelineView.getTimeLineViewType(position, getItemCount());
    }

    private void bindTodayQuestionsView(TodayQuestionsViewHolder holder, int position) {
        final TodayQuestion curQuestion = mTodayQuestions.get(position);

        // quetion, answer count, favorite count
        holder.questionTextView.setText(curQuestion.getContent());
        holder.answerButton.setText(curQuestion.getAnswerCount().toString());
        holder.favoriteButton.setText(curQuestion.getLikeCount().toString());

        // adjust button image sizes
        Button[] buttons = {holder.answerButton, holder.favoriteButton};
        int[] buttonImageIDs = {R.drawable.answer_count_logo, R.drawable.favorite_count_logo};
        for (int i = 0; i < buttons.length; ++i) {
            Drawable buttonImage = ContextCompat.getDrawable(mContext, buttonImageIDs[i]);
            buttonImage.setBounds(0, 0, 48, 48);  //  第一0是距左边距离，第二0是距上边距离，40分别是长宽
            buttons[i].setCompoundDrawables(buttonImage, null, null, null);//只放左边
        }
    }

    private void bindTimeLineView(TimeLineViewHolder holder, int position) {
        position -= mTodayQuestions.size();
        Answer answer = mAnswerList.get(position);
        holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext,
                R.drawable.ic_marker_active, R.color.colorPrimary));
        holder.mDate.setVisibility(View.VISIBLE);
        holder.mDate.setText(DateTimeUtils.parseDateTime(answer.getDate(),
                "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        holder.mMessage.setText(answer.getQuestionContent());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTodayQuestions.size() + mAnswerList.size();
    }
}
