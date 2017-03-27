package com.note8.sanxing.viewpager;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.note8.sanxing.R;

import java.util.ArrayList;

/**
 * Created by BenWwChen on 2017/3/27.
 */

public class CustomViewPager extends ViewPager {

    private int childId;
    private int pageIndex;

    private float beforeX;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context) {

        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        View child = findViewById(childId);
        if (child != null && getCurrentItem() == pageIndex) {
            Rect rect = new Rect();
            child.getHitRect(rect);
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                if (isSwipeToRight(event)) {
                    return false;
                }
            }
        }

        return super.onInterceptTouchEvent(event);
    }

    private boolean isSwipeToRight(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下如果‘仅’作为‘上次坐标’，不妥，因为可能存在左滑，motionValue大于0的情况（来回滑，只要停止坐标在按下坐标的右边，左滑仍然能滑过去）
                beforeX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float motionValue = ev.getX() - beforeX;
                if (motionValue > 0) { //禁止右划
                    return true;
                }
                beforeX = ev.getX();//手指移动时，再把当前的坐标作为下一次的‘上次坐标’，解决上述问题

                break;
            default:
                break;
        }
        return false;
    }

    public void setChildIdAndPageIndex(int id, int pageIndex) {
        this.childId = id;
        this.pageIndex = pageIndex;
    }
}