package com.yyl.videolist.video;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yyl.videolist.R;
import com.yyl.videolist.listeners.FullScreenControl;
import com.yyl.videolist.listeners.VideoViewControllerListeners;
import com.yyl.videolist.utils.StringUtils;
import com.yyl.videolist.utils.V;

import org.videolan.vlc.listener.MediaPlayerControl;

/**
 * Created by yyl on 2016/2/16/016.
 * <p/>
 * 小屏播放器  底布局
 */
public class MediaControllerSmall implements VideoViewControllerListeners {
    private MediaPlayerControl mPlayer;
    private long mDuration;

    private boolean mDragging;
    private boolean mInstantSeeking = false;

    // @OnClick({R.id.mediacontroller_play_pause_small, R.id.mediacontroller_change_small, R.id.mediacontroller_projection_small})


    public void onProgressUpdate(long current, long duration) {
        updatePausePlay();
        setProgress();
    }

    @Override
    public boolean onSingleTapConfirmed() {
        return false;
    }

    @Override
    public void onDoubleTap() {

    }


    private SeekBar.OnSeekBarChangeListener mSeekListener = new SeekBar.OnSeekBarChangeListener() {
        public void onStartTrackingTouch(SeekBar bar) {
            mDragging = true;
        }

        public void onProgressChanged(SeekBar bar, int progress, boolean fromuser) {
            if (!fromuser)
                return;
            long newposition = (mDuration * progress) / 1000;
            String time = StringUtils.generateTime(newposition);
            if (mInstantSeeking)
                mPlayer.seekTo((int)newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking)
                mPlayer.seekTo((int)(mDuration * bar.getProgress()) / 1000);
            mDragging = false;
        }
    };
    SeekBar mProgress;
    TextView mEndTime;
    TextView mCurrentTime;
    View down_layout;
    ImageButton mPauseButton;
    View conterLayout;

    public MediaControllerSmall(final Activity activity, View layoutRootView, MediaPlayerControl player, final FullScreenControl fullScreenControl) {
        this.mPlayer = player;
        mProgress = V.findV(layoutRootView, R.id.mediacontroller_seekbar_small);
        mEndTime = V.findV(layoutRootView, R.id.mediacontroller_time_total_small);
        mCurrentTime = V.findV(layoutRootView, R.id.mediacontroller_time_current_small);
        down_layout = V.findV(layoutRootView, R.id.mediacontroller_down_layout_small);
        mPauseButton = V.findV(layoutRootView, R.id.mediacontroller_play_pause_small);
        conterLayout = V.findV(layoutRootView, R.id.mediacontroller_conter_bg);
        V.findV(layoutRootView, R.id.mediacontroller_change_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullScreenControl.setFullscreen(activity, !fullScreenControl.isFullScreen());
            }
        });
        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPauseResume();
                fullScreenControl.clickEvent();
            }
        });


        initView();
    }

    public <T extends View> T findV(View view, int id) {
        return (T) view.findViewById(id);
    }

    private void initView() {
        mEndTime.getPaint().setTextSkewX(-0.25f);
        mCurrentTime.getPaint().setTextSkewX(-0.25f);
        updatePausePlay();
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        mProgress.setPadding(0, 0, 0, 0);
        mProgress.setMax(1000);
        mProgress.setThumbOffset(0);
    }


    /**
     * @param seekWhenDragging false拖动完成后加载进度   ture拖动中加载进度
     */
    public void setInstantSeeking(boolean seekWhenDragging) {
        mInstantSeeking = seekWhenDragging;
    }


    private boolean mShowing;

    @Override
    public void show(int time) {
        if (mShowing) {
            return;
        }
        mShowing = true;
        startOpenAnimation(mPauseButton.getContext());
    }

    @Override
    public void close() {
        mShowing = false;
        down_layout.setVisibility(View.GONE);
        conterLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean isDragging() {
        return mDragging;
    }

    @Override
    public void changeLayoutState(int layoutState) {

    }


    public void hide() {
        if (mShowing) {
            mShowing = false;
            startHideAnimation(mPauseButton.getContext());
        }

    }


    private void startOpenAnimation(Context mContext) {
        if (down_layout.getVisibility() != View.VISIBLE) {
            down_layout.setVisibility(View.VISIBLE);
        }
        if (conterLayout.getVisibility() != View.VISIBLE) {
            conterLayout.setVisibility(View.VISIBLE);
        }

        Animation in_from_fade = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.in_from_fade);
        conterLayout.clearAnimation();
        conterLayout.startAnimation(in_from_fade);

        Animation anima = AnimationUtils.loadAnimation(mContext, R.anim.in_from_down);
        down_layout.startAnimation(anima);
    }


    private void startHideAnimation(Context mContext) {

        Animation out_from_fade = AnimationUtils.loadAnimation(mContext, R.anim.out_from_fade);
        conterLayout.clearAnimation();
        conterLayout.startAnimation(out_from_fade);

        Animation out_from_down = AnimationUtils.loadAnimation(mContext, R.anim.out_from_down);
        out_from_down.setAnimationListener(animationListener);
        down_layout.startAnimation(out_from_down);
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            down_layout.setVisibility(View.GONE);
            conterLayout.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public boolean isShowing() {
        return mShowing;
    }


    private long setProgress() {
        if (!mShowing || mDragging)
            return 0;
        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mProgress.setProgress((int) pos);
            }
            //  int percent = mPlayer.getBufferPercentage();
            //  mProgress.setSecondaryProgress(percent * 10);
        }
        mDuration = duration;
        if (mEndTime != null)
            mEndTime.setText(StringUtils.generateTime(mDuration));
        if (mCurrentTime != null)
            mCurrentTime.setText(StringUtils.generateTime(position));
        return position;
    }


    private void updatePausePlay() {
        if (mPauseButton == null)
            return;
        if (mPlayer.isPlaying()) {
            mPauseButton.setSelected(true);
        } else {
            mPauseButton.setSelected(false);
        }

    }

    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }


    public void setEnabled(boolean enabled) {
        if (mPauseButton != null)
            mPauseButton.setEnabled(enabled);
        if (mProgress != null)
            mProgress.setEnabled(enabled);

    }

    public void setSmallState() {
        smallState = true;
        //  projection_small.setVisibility(View.GONE);
        //   change_small.setVisibility(View.GONE);
        conterLayout.setVisibility(View.GONE);
        close();
    }

    boolean smallState;


    @Override
    public void eventBuffing(int event, float buffing) {

    }

    @Override
    public void eventPlayInit(boolean opening) {

    }

    @Override
    public void eventStop(boolean isPlayError) {

    }

    @Override
    public void eventError(int error, boolean show) {

    }

    @Override
    public void eventPlay(boolean isPlaying) {

    }

}
