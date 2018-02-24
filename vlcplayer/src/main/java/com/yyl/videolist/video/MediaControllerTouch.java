package com.yyl.videolist.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyl.videolist.MyVideoView;
import com.yyl.videolist.R;
import com.yyl.videolist.listeners.VideoViewListeners;
import com.yyl.videolist.listeners.VideoViewTouchListeners;
import com.yyl.videolist.utils.StringUtils;
import com.yyl.videolist.utils.V;

import org.videolan.vlc.listener.MediaPlayerControl;


/**
 * Created by yyl on 2016/2/17/017.
 * 快进快退方向控制
 * 点击事件监听
 */
public class MediaControllerTouch implements VideoViewTouchListeners {

    ImageView speed_direction;
    View speedLayout;
    TextView speedTime;


    private long newposition;
    private boolean isMove = false;
    private float move = 0;
    private GestureDetector mGestureDetector;

    private final MediaPlayerControl mPlayer;
    private VideoViewListeners videolistener;
    private boolean isSmallMini;

    private boolean isNullLayout;//什么都不显示

    private boolean stateList;
    private boolean isError;
    private boolean isFullscreen;
    @SuppressLint("HandlerLeak")
    Handler handlerVisiable = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (speedLayout != null)
                speedLayout.setVisibility(View.GONE);
        }
    };

    public MediaControllerTouch(View layoutRootView, MyVideoView mPlayer, VideoViewListeners videolistener) {
        this.mPlayer = mPlayer;
        this.videolistener = videolistener;
        speed_direction = V.findV(layoutRootView, R.id.mediacontroller_speed_direction);
        speedLayout = V.findV(layoutRootView, R.id.mediacontroller_speed_layout);
        speedTime = V.findV(layoutRootView, R.id.mediacontroller_speed_text);

        initView(layoutRootView.getContext());
        setOnScreenTouchSpeed(mPlayer);
    }

    @Override
    public void changeLayoutState(int layoutState) {
        switch (layoutState) {
            case stateSmallFull:

                break;
            case stateFull:
                isFullscreen = true;
                break;
            case stateSmallList:
                stateList = true;
                break;
            case stateNull:
                isNullLayout = true;
                break;

        }
    }

    @Override
    public void setFullscreen(Activity activity, boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
    }

    private void initView(Context mContext) {
        mGestureDetector = new GestureDetector(mContext, new MyGestureListener());
        speedLayout.setVisibility(View.GONE);
    }

    public void setError(boolean error) {
        isError = error;
    }


    /**
     * 快进快退的父控件
     *
     * @param view
     */
    private void setOnScreenTouchSpeed(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isSmallMini) return true;
                if (isNullLayout) return true;
                if (isError) return true;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    boolean isresult = mGestureDetector.onTouchEvent(event);
                    if (!isresult && isMove && mPlayer != null) {
                        mPlayer.seekTo((int)newposition);
                        setSpeedVisibiliy(false);
                    }
                    return isresult;
                } else {
                    return mGestureDetector.onTouchEvent(event);
                }

            }
        });
    }


    private void setSpeedVisibiliy(boolean visibiliy) {
        if (visibiliy && speedLayout.getVisibility() != View.VISIBLE) {
            speedLayout.setVisibility(View.VISIBLE);
            handlerVisiable.removeMessages(0);
            handlerVisiable.sendEmptyMessageDelayed(0, 1000);
        } else if (!visibiliy && speedLayout.getVisibility() == View.VISIBLE) {
            speedLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speedLayout.setVisibility(View.GONE);
                }
            }, 300);
        }
    }

    private void setSpeedTimelayout(float move) {
        if (!isCanCuntroll() || !isFullscreen) {
            return;
        }
        isMove = true;
        if (move > 0) {
            speed_direction.setSelected(true);
        } else {
            speed_direction.setSelected(false);
        }
        long progress = mPlayer.getCurrentPosition();
        long moveLimit = progress + ((long) (move * 40));
        long count = mPlayer.getDuration();
        moveLimit = moveLimit > 0 ? moveLimit > count ? count : moveLimit : 0;
        //总位置数乘 现在位置百分比
        newposition = moveLimit;
        String time = StringUtils.generateTime(newposition);
        String time2 = StringUtils.generateTime(count);
        speedTime.setText(time + " / " + time2);
        setSpeedVisibiliy(true);
    }

    private boolean isCanCuntroll() {
        return mPlayer != null && mPlayer.getDuration() > 1;
    }

    public void changeMiniScaleState(boolean isSmallMini) {
        this.isSmallMini = isSmallMini;
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            isMove = false;
            move = 0;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            move = e2.getX() - e1.getX();
            setSpeedTimelayout(move);
            return true;
        }


        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            videolistener.onSingleTapConfirmed();
            return true;
        }

        //双击了
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            videolistener.onDoubleTap();
            return true;
        }


    }
}
