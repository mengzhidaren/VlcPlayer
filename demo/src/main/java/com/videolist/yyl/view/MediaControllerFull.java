package com.videolist.yyl.view;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.videolist.yyl.R;
import com.yyl.videolist.listeners.FullScreenControl;
import com.yyl.videolist.listeners.MediaPlayerControl;
import com.yyl.videolist.listeners.VideoViewControllerListeners;
import com.yyl.videolist.utils.LogUtils;
import com.yyl.videolist.utils.StringUtils;
import com.yyl.videolist.video.MySensorListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yyl on 2016/2/16/016.
 * <p/>
 * 全屏播放器  底布局
 */
public class MediaControllerFull implements VideoViewControllerListeners {

    public MediaPlayerControl mPlayer;

    @Bind(R.id.mediacontroller_back)
    ImageButton mediacontrollerBack;
    @Bind(R.id.mediacontroller_file_name)
    TextView mediacontrollerFileName;
    @Bind(R.id.mediacontroller_projection)
    ImageButton mediacontrollerProjection;
    @Bind(R.id.mediacontroller_lock)
    ImageButton mediacontrollerLock;
    @Bind(R.id.mediacontroller_share)
    ImageButton mediacontrollerShare;

    @Bind(R.id.mediacontroller_time_current)
    TextView mCurrentTime;

    @Bind(R.id.mediacontroller_playmode)
    ImageButton loopButton;
    @Bind(R.id.mediacontroller_last)
    ImageButton mediacontrollerLast;
    @Bind(R.id.mediacontroller_play_pause)
    ImageButton mPauseButton;
    @Bind(R.id.mediacontroller_next)
    ImageButton mediacontrollerNext;
    @Bind(R.id.mediacontroller_mirror)
    TextView mediacontrollerMirror;

    @Bind(R.id.mediacontroller_speed_change)
    TextView mediacontroller_speed_change;

    @Bind(R.id.mediacontroller_seekbar)
    SeekBar mProgress;


    @Bind(R.id.mediacontroller_tag)
    LinearLayout mediacontrollerTag;

    @Bind(R.id.mediacontroller_down_layout)
    View down_layout;

    @Bind(R.id.mediacontroller_up_layout)
    View upLayout;


    @Bind(R.id.mediacontroller_conter_bg)
    View conterLayout;
    private FullScreenControl fullScreenControl;


    private long mDuration;
    private boolean mDragging;
    private boolean mInstantSeeking = false;

    public void setLoopState(boolean loopState) {

    }

    public void setFileName(String name) {
        mediacontrollerFileName.setText(name);
    }

    public void onProgressUpdate(long current, long duration) {
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
                mPlayer.seekTo(newposition);
            if (mCurrentTime != null)
                mCurrentTime.setText(time);
        }

        public void onStopTrackingTouch(SeekBar bar) {
            if (!mInstantSeeking)
                mPlayer.seekTo((mDuration * bar.getProgress()) / 1000);
            mDragging = false;
        }
    };

    public boolean isDragging() {
        return mDragging;
    }


    public MediaControllerFull(View layoutRootView, MediaPlayerControl player, FullScreenControl fullScreenControl) {
        ButterKnife.bind(this, layoutRootView);
        this.mPlayer = player;
        this.fullScreenControl = fullScreenControl;
        initView();
    }

    private void initView() {
        mediacontrollerLock.setSelected(MySensorListener.isLock());
        mProgress.setPadding(0, 0, 0, 0);
        mProgress.setOnSeekBarChangeListener(mSeekListener);
        mProgress.setMax(1000);
        mProgress.setThumbOffset(0);
    }


    /**
     * Control the action when the seekbar dragged by user
     *
     * @param seekWhenDragging True the media will seek periodically
     */
    public void setInstantSeeking(boolean seekWhenDragging) {
        mInstantSeeking = seekWhenDragging;
    }


    private boolean mShowing;

    public void show() {
        if (mShowing) {
            return;
        }
        mShowing = true;
        startOpenAnimation();
    }


    @Override
    public void show(int time) {

    }

    public void hide() {
        if (mShowing) {
            mShowing = false;
            startHideAnimation();
        }
    }

    public void close() {
        mShowing = false;
        upLayout.setVisibility(View.GONE);
        down_layout.setVisibility(View.GONE);
        conterLayout.setVisibility(View.GONE);
    }

    @Override
    public void changeLayoutState(int layoutState) {

    }

    public void startOpenAnimation() {
        if (down_layout.getVisibility() != View.VISIBLE) {
            down_layout.setVisibility(View.VISIBLE);
        }
        if (upLayout.getVisibility() != View.VISIBLE) {
            upLayout.setVisibility(View.VISIBLE);
        }
        if (conterLayout.getVisibility() != View.VISIBLE) {
            conterLayout.setVisibility(View.VISIBLE);
        }

        Animation in_from_fade = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.in_from_fade);
        Animation in_from_down = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.in_from_down);
        Animation in_from_up = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.in_from_up);

        conterLayout.clearAnimation();
        conterLayout.startAnimation(in_from_fade);

        upLayout.clearAnimation();
        upLayout.startAnimation(in_from_up);

        down_layout.clearAnimation();
        down_layout.startAnimation(in_from_down);
    }

    public void startHideAnimation() {
        Animation out_from_down = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.out_from_down);
        Animation out_from_up = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.out_from_up);
        Animation out_from_fade = AnimationUtils.loadAnimation(down_layout.getContext(), R.anim.out_from_fade);

        //  out_from_fade.setAnimationListener(animationListener);
        // out_from_up.setAnimationListener(animationListener);
        out_from_down.setAnimationListener(animationListener);

        conterLayout.clearAnimation();
        conterLayout.startAnimation(out_from_fade);

        upLayout.clearAnimation();
        upLayout.startAnimation(out_from_up);

        down_layout.clearAnimation();
        down_layout.startAnimation(out_from_down);
    }

    public Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            down_layout.setVisibility(View.GONE);
            upLayout.setVisibility(View.GONE);
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
            //int percent = mPlayer.getBufferPercentage();
            //mProgress.setSecondaryProgress(percent * 10);
        }
        updatePausePlay();
        mDuration = duration;
        if (mCurrentTime != null)
            mCurrentTime.setText(StringUtils.generateTime(position) + " / " + StringUtils.generateTime(mDuration));
        return position;
    }


    public void setLoop(boolean loop) {
        loopButton.setSelected(loop);
    }

    public void setEnabled(boolean enabled) {
        if (mProgress != null)
            mProgress.setEnabled(enabled);

    }

    private void updatePausePlay() {
        mPauseButton.setSelected(mPlayer.isPlaying());
    }

    public void hideLastButton() {
        mediacontrollerLast.setEnabled(false);
    }

    public void showLastButton() {
        mediacontrollerLast.setEnabled(true);
    }

    public void hideNextButton() {
        mediacontrollerNext.setEnabled(false);
    }

    public void showNextButton() {
        mediacontrollerNext.setEnabled(true);
    }

    private void doPauseResume() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
        updatePausePlay();
    }

    private int speed = 1;

    private void setSpeed() {
        String speedText = "1.0X";
        switch (speed) {
            case 1:
                mPlayer.setPlaybackSpeedMedia(0.25f);
                speed = 2;
                speedText = "0.2X";
                break;
            case 2:
                mPlayer.setPlaybackSpeedMedia(0.4f);
                speed = 4;
                speedText = "0.4X";
                break;
            case 4:
                mPlayer.setPlaybackSpeedMedia(0.6f);
                speed = 6;
                speedText = "0.6X";
                break;
            case 6:
                mPlayer.setPlaybackSpeedMedia(0.8f);
                speed = 8;
                speedText = "0.8X";
                break;
            case 8:
                mPlayer.setPlaybackSpeedMedia(1f);
                speed = 1;
                speedText = "1.0X";
                break;
        }
        mediacontroller_speed_change.setText(speedText);
    }


    @OnClick({R.id.mediacontroller_speed_change, R.id.mediacontroller_back, R.id.mediacontroller_projection, R.id.mediacontroller_lock, R.id.mediacontroller_share, R.id.mediacontroller_playmode, R.id.mediacontroller_last, R.id.mediacontroller_play_pause, R.id.mediacontroller_next, R.id.mediacontroller_mirror})
    public void onClick(View view) {
        LogUtils.i("view.getId()=" + view.getId());
        fullScreenControl.clickEvent();
        switch (view.getId()) {
            case R.id.mediacontroller_speed_change:
                setSpeed();
                break;
            case R.id.mediacontroller_back:
                fullScreenClick.back(view);
                break;
            case R.id.mediacontroller_projection://二维码切屏
                fullScreenClick.projection(view);
                break;
            case R.id.mediacontroller_lock:
                MySensorListener.setLock(!MySensorListener.isLock());
                view.setSelected(MySensorListener.isLock());
                break;
            case R.id.mediacontroller_playmode:
//                boolean singleCyclePlay = !PrefCache.getLoopPlay(view.getContext());
//                PrefCache.saveLoopPlay(view.getContext(), singleCyclePlay);
//                mPlayer.setLoop(singleCyclePlay);
//                setLoop(singleCyclePlay);
                break;
            case R.id.mediacontroller_last:
                fullScreenClick.last(view);
                break;
            case R.id.mediacontroller_play_pause:
                doPauseResume();
                break;
            case R.id.mediacontroller_next:
                fullScreenClick.next(view);
                break;
            case R.id.mediacontroller_mirror:
                boolean mirror = !mPlayer.getMirror();
                boolean mirrorState = !view.isSelected();
                mPlayer.setMirror(mirror);
                setMirrorState(mirrorState);
                break;
            case R.id.mediacontroller_share:
                fullScreenClick.share(view);
                break;
        }
    }


    public void setMirrorState(boolean mirror) {
        mediacontrollerMirror.setText(mirror ? "已镜面" : "未镜面");
        mediacontrollerMirror.setSelected(mirror);
    }


    public void setMirrorEnable(boolean enable) {

    }

    private FullScreenClick fullScreenClick;

    public void setFullScreenClick(FullScreenClick fullScreenClick) {
        this.fullScreenClick = fullScreenClick;
    }

    @Override
    public void eventBuffing(float buffing, boolean show) {

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
    public void eventPlay() {

    }

    @Override
    public void eventPause() {

    }

    @Override
    public void eventReleaseInit() {

    }


    public interface FullScreenClick {
        void share(View view);

        void last(View view);

        void next(View view);

        void back(View view);

        void projection(View view);

    }
}
