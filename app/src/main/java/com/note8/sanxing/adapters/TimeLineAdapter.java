package com.note8.sanxing.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.note8.sanxing.R;
import com.note8.sanxing.models.TimeLineModel;
import com.note8.sanxing.utils.ui.DateTimeUtils;
import com.note8.sanxing.utils.ui.VectorDrawableUtils;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    private List<TimeLineModel> mDataList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.item_timeline, parent, false);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        TimeLineModel timeLineModel = mDataList.get(position);
        holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext,
                R.drawable.ic_marker_active, R.color.colorPrimary));
        holder.mDate.setVisibility(View.VISIBLE);
        holder.mDate.setText(DateTimeUtils.parseDateTime(timeLineModel.getDate(),
                "yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy"));
        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mDataList != null ? mDataList.size() : 0);
    }
}