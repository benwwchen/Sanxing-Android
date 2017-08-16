package com.note8.sanxing.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;

public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mDate;  //  2015 Jal
    public TextView mTime;  //  xx:xx
    public TextView mMessage;
    public ImageView mMood;
    public TimelineView mTimelineView;
    public LinearLayout mTimelineLayout;
    public OnItemClickListener mOnItemClickListener;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        this.mTimelineLayout = (LinearLayout) itemView.findViewById(R.id.layout_timeline);
        mTimelineLayout.setOnClickListener(this);
        this.mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);
        this.mTime = (TextView) itemView.findViewById(R.id.text_timeline_time);
        this.mMood = (ImageView) itemView.findViewById(R.id.timeline_mood);
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