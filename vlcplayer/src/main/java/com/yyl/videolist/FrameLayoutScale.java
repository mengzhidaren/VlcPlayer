package com.yyl.videolist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;



/**
 * Created by Administrator on 2016/11/22/022.
 */

public class FrameLayoutScale extends FrameLayout {

    private float videoScale = 9f / 16f;
    private float lastScale = videoScale;
    private String tag = "FrameLayoutScale";

    public FrameLayoutScale(Context context) {
        super(context);
    }

    public FrameLayoutScale(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FrameLayoutScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        onMeasureVideo(widthMeasureSpec, heightMeasureSpec, getLayoutParams().height);
    }

    private void onMeasureVideo(int widthMeasureSpec, int heightMeasureSpec, int heightParams) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureHeight = isFullVideoState ? measureHeight : (int) (measureWidth * videoScale);
        setMeasuredDimension(measureWidth, measureHeight);
        if (isFullVideoState && heightParams != FrameLayout.LayoutParams.MATCH_PARENT) {
            getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
            if (isInEditMode()) {
                setMeasuredDimension(measureWidth, measureHeight);
            }
            return;
        } else if (!isFullVideoState && heightParams != FrameLayout.LayoutParams.WRAP_CONTENT) {
            getLayoutParams().height = FrameLayout.LayoutParams.WRAP_CONTENT;
            if (isInEditMode()) {
                setMeasuredDimension(measureWidth, measureHeight);
            }
            return;
        }

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(measureWidth, MeasureSpec.EXACTLY);
                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(measureHeight, MeasureSpec.EXACTLY);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
    }

    public void setScaleVideo(float videoScale) {
        this.videoScale = videoScale;
    }

    private boolean isFullVideoState;

    public void setScaleFull(boolean full) {
        this.isFullVideoState = full;
    }
}