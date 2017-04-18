package com.note8.sanxing.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.BroadcastQuestion;
import com.note8.sanxing.models.Question;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.network.VolleyUtil;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/3/24.
 */

public class BroadcastQuestionsAdapter extends RecyclerView.Adapter<BroadcastQuestionsAdapter.ViewHolder> {

    private ArrayList<BroadcastQuestion> mBroadcastQuestions;

    Context mContext;
    ImageLoader mImageLoader;
    private int picMaxWidth;
    private int picMaxHeight;

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
        public TextView favoriteCountTextView;
        public TextView answerCountTextView;
        public CardView cardView;

        OnItemClickListener mOnItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_broadcast_questions);
            cardView.setOnClickListener(this);
            picImageView = (ImageView) itemView.findViewById(R.id.image_view_pic);
            questionTextView = (TextView) itemView.findViewById(R.id.text_view_broadcast_question);
            favoriteCountTextView = (TextView) itemView.findViewById(R.id.text_view_favorite_count);
            answerCountTextView = (TextView) itemView.findViewById(R.id.text_view_answer_count);
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
        // Get the ImageLoader through your singleton class.
        mImageLoader = VolleyUtil.getInstance(context).getImageLoader();
        // Get pic max width/height (display width/height)
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        picMaxWidth = dm.widthPixels;
        picMaxHeight = dm.heightPixels;
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

        final BroadcastQuestion curQuestion = mBroadcastQuestions.get(position);

        // question, answer count, favorite count
        holder.questionTextView.setText(curQuestion.getContent());
        holder.timeTextView.setText(curQuestion.getReleaseTime());
        holder.favoriteCountTextView.setText(String.valueOf(curQuestion.getLikeCount()));
        holder.answerCountTextView.setText(String.valueOf(curQuestion.getAnswerCount()));

        // picture
        if (!curQuestion.getPicture().isEmpty()) {
            String url = curQuestion.getPicture() + "?imageView2/2/w/" + picMaxWidth;
            mImageLoader.get(url, ImageLoader.getImageListener(holder.picImageView,
                    R.color.cardview_light_background, R.color.cardview_light_background), picMaxWidth,
                    picMaxHeight);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mBroadcastQuestions.size();
    }
}
