package com.note8.sanxing.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;

public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView mDate;
    public TextView mMessage;
    public TimelineView mTimelineView;
    public CardView mCardView;
    public OnItemClickListener mOnItemClickListener;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        this.mCardView = (CardView) itemView.findViewById(R.id.card_view_timeline);
        mCardView.setOnClickListener(this);
        this.mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);
        this.mMessage = (TextView) itemView.findViewById(R.id.text_timeline_title);
        this.mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        this.mTimelineView.initLine(viewType);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }
}