package com.note8.sanxing.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.note8.sanxing.R;
import com.note8.sanxing.listeners.OnItemClickListener;
import com.note8.sanxing.models.Answer;
import com.note8.sanxing.utils.ui.DateTimeUtils;
import com.note8.sanxing.utils.ui.VectorDrawableUtils;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    private List<Answer> mDataList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public TimeLineAdapter(List<Answer> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
        View view = this.mLayoutInflater.inflate(R.layout.item_timeline, parent, false);
        TimeLineViewHolder viewHolder = new TimeLineViewHolder(view, viewType);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        Answer answer = this.mDataList.get(position);
        holder.mTimelineView.setMarker(VectorDrawableUtils.getDrawable(mContext,
                R.drawable.ic_marker_active, R.color.colorPrimary));
        //  render date
        if (!answer.isFirst()) {
            holder.mDate.setVisibility(View.GONE);
        } else {
            holder.mDate.setVisibility(View.VISIBLE);
        }
        holder.mDate.setText(answer.getDate());

        //  render mood
        if (answer.getMood() > 50) {
            holder.mMood.setImageResource(R.drawable.good_mood);
        } else {
            holder.mMood.setImageResource(R.drawable.bad_mood);
        }

        //  feed question content
        holder.mMessage.setText(answer.getQuestionContent());

        //  feed time
        holder.mTime.setText(answer.getTime());
    }

    @Override
    public int getItemCount() {
        return (mDataList != null ? mDataList.size() : 0);
    }
}