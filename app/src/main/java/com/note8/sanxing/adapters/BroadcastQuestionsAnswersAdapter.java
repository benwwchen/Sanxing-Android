package com.note8.sanxing.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.note8.sanxing.BroadcastQuestionDetailActivity;
import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.models.BroadcastQuestion;
import com.note8.sanxing.utils.network.SanxingApiClient;
import com.note8.sanxing.utils.network.VolleyUtil;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/4/18.
 */

public class BroadcastQuestionsAnswersAdapter extends RecyclerView.Adapter<BroadcastQuestionsAnswersAdapter.ViewHolder>{

    private ArrayList<Answer> mAnswers;
    Context mContext;
    ImageLoader mImageLoader;

    private OnItemClickListener mOnItemClickListener;
    private BroadcastQustionsAnswersAdapterCallback mBroadcastQustionsAnswersAdapterCallback;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView avatarImageView;
        public TextView usernameTextView;
        public Button likeButton;
        public TextView timeTextView;
        public TextView answerContentTextView;

        OnItemClickListener mOnItemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.avatar_image_view);
            usernameTextView = (TextView) itemView.findViewById(R.id.username_text_view);
            likeButton = (Button) itemView.findViewById(R.id.like_button);
            timeTextView = (TextView) itemView.findViewById(R.id.time_text_view);
            answerContentTextView = (TextView) itemView.findViewById(R.id.content_text_view);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BroadcastQuestionsAnswersAdapter(ArrayList<Answer> answers, Context context, BroadcastQustionsAnswersAdapterCallback broadcastQustionsAnswersAdapterCallback) {
        this.mContext = context;
        this.mAnswers = answers;
        this.mBroadcastQustionsAnswersAdapterCallback = broadcastQustionsAnswersAdapterCallback;
        // Get the ImageLoader through your singleton class.
        mImageLoader = VolleyUtil.getInstance(context).getImageLoader();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BroadcastQuestionsAnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_broadcast_questions_answer, parent, false);
        BroadcastQuestionsAnswersAdapter.ViewHolder vh = new BroadcastQuestionsAnswersAdapter.ViewHolder(v);
        vh.mOnItemClickListener = mOnItemClickListener;
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final BroadcastQuestionsAnswersAdapter.ViewHolder holder, int position) {

        final Answer curAnswer = mAnswers.get(position);

        // username, like count, create time, content
        holder.usernameTextView.setText(curAnswer.getAnswerer().username);
        holder.likeButton.setText(String.valueOf(curAnswer.getLikeCount()));
        holder.timeTextView.setText(curAnswer.getTimeFromNow());
        holder.answerContentTextView.setText(curAnswer.getContent());

        // favorite button
        if (curAnswer.getFavorite() == null || !curAnswer.getFavorite()) {
            holder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notlike_gray, 0, 0, 0);
        } else {
            holder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
        }
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // change the icon first (if failed, the fragment will refresh the data back)
                if (curAnswer.getFavorite()) {
                    holder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_notlike_gray, 0, 0, 0);
                } else {
                    holder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_like, 0, 0, 0);
                }
                mBroadcastQustionsAnswersAdapterCallback.onFavoriteButtonClick(
                        curAnswer.getAnswerId(), !curAnswer.getFavorite());
            }
        });

        // avatar (fake)
        // TODO: replace with real avatars
        int[] avatars = {R.drawable.sample_avatar_1, R.drawable.sample_avatar_2, R.drawable.sample_avatar_3};
        holder.avatarImageView.setImageDrawable(mContext.getResources().getDrawable(avatars[position % 3], null));

    }

    public interface BroadcastQustionsAnswersAdapterCallback {
        void onFavoriteButtonClick(String answerId, Boolean favorite);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAnswers.size();
    }
}
