package com.note8.sanxing.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.BroadcastQuestion;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class BroadcastQuestionsAdapter extends RecyclerView.Adapter<BroadcastQuestionsAdapter.ViewHolder> {

    private ArrayList<BroadcastQuestion> mBroadcastQuestions;
    Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView picImageView;
        public TextView questionTextView;
        public TextView timeTextView;
        public CardView cardView;

        OnItemClickListener mOnItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_broadcast_questions);
            cardView.setOnClickListener(this);
            picImageView = (ImageView) itemView.findViewById(R.id.image_view_pic);
            questionTextView = (TextView) itemView.findViewById(R.id.text_view_broadcast_question);
            timeTextView = (TextView) itemView.findViewById(R.id.text_view_time);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BroadcastQuestionsAdapter(ArrayList<BroadcastQuestion> BroadcastQuestions, Context context) {
        this.mContext = context;
        this.mBroadcastQuestions = BroadcastQuestions;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BroadcastQuestionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_broadcast_questions, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        vh.mOnItemClickListener = mOnItemClickListener;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final BroadcastQuestion curQuestion = mBroadcastQuestions.get(position);

        // quetion, answer count, favorite count
        holder.questionTextView.setText(curQuestion.getContent());
        holder.timeTextView.setText(curQuestion.getReleaseTime());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBroadcastQuestions.size();
    }
}
