package com.note8.sanxing.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.note8.sanxing.R;

public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TextView mDate;
    public TextView mMessage;
    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        this.mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);
        this.mMessage = (TextView) itemView.findViewById(R.id.text_timeline_title);
        this.mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        this.mTimelineView.initLine(viewType);
    }
}