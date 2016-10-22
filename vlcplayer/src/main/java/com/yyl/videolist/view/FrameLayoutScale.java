package com.yyl.videolist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2016/10/19/019.
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
    }

    public FrameLayoutScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (width != 0 && videoScale > 0) {
            //视频显示比例16/9
            getLayoutParams().height = (int) (width * videoScale);
            setMeasuredDimension(width, (int) (width * videoScale));
        } else {
            //没有显示比例 就全屏播放视频
            getLayoutParams().height = -1;
            setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
        }
        Log.i(tag, "onMeasure " + videoScale);
    }
    public void setScaleVideo(float videoScale) {
        this.videoScale = videoScale;
    }
    public void setScaleFull(boolean full) {
        if (full) {
            videoScale = -1;
        } else {
            videoScale = lastScale;
        }
        // requestLayout();
    }
}
