package com.yyl.videolist.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yyl.videolist.demo.VideoControllerDemo;
import com.yyl.videolist.listeners.FullScreenControl;
import com.yyl.videolist.listeners.MediaPlayerChangeState;
import com.yyl.videolist.listeners.VideoViewControllerListeners;
import com.yyl.videolist.listeners.VideoViewListeners;
import com.yyl.videolist.listeners.VideoViewTouchListeners;

import org.videolan.vlc.listener.MediaPlayerControl;

/**
 * Created by yuyunlong on 2016/8/13/013.
 */
public abstract class VlcMediaControllerBase implements FullScreenControl, VideoViewListeners, MediaPlayerChangeState {
    protected int currentState = -1;
    protected VideoViewControllerListeners fullLayout;
    protected VideoViewControllerListeners smallLayout;
    protected VideoViewControllerListeners buffingLayout;
    protected VideoViewTouchListeners mediaTouch;
    private FullScreenControl fullScreenControl;
    protected MediaPlayerControl mPlayer;

    protected boolean mShowing;
    protected static final int FADE_OUT = 1;
    protected static final int SHOW_PROGRESS = 2;

    protected boolean isResume;
    private View rootView;
    private boolean isAnchor;

    public VlcMediaControllerBase(MediaPlayerControl mPlayer) {
        this.mPlayer = mPlayer;
        fullLayout = new VideoControllerDemo();
        smallLayout = new VideoControllerDemo();
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void setFullScreenControl(FullScreenControl fullScreenControl) {
        this.fullScreenControl = fullScreenControl;
    }


    public boolean isAnchor() {
        return isAnchor;
    }

    public void setAnchor() {
        isAnchor = true;
    }

    public void setBuffingLayout(VideoViewControllerListeners buffingLayout) {
        this.buffingLayout = buffingLayout;
    }

    public void setSmallLayout(VideoViewControllerListeners smallLayout) {
        this.smallLayout = smallLayout;
    }

    public void setFullLayout(VideoViewControllerListeners fullLayout) {
        this.fullLayout = fullLayout;
    }

    public void setTouch(VideoViewTouchListeners mediaTouch) {
        this.mediaTouch = mediaTouch;
    }

    public void anchorRootView(FrameLayout frameLayout) {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        frameLayout.addView(rootView);
    }

    public void removeRootView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
    }


    @SuppressLint("HandlerLeak")
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isResume) return;
            switch (msg.what) {
                case FADE_OUT:
                    if (isDragging()) {
                        sendEmptyMessageDelayed(FADE_OUT, 3000);
                    } else {
                        hide();
                    }
                    break;
                case SHOW_PROGRESS:
                    long pos = mPlayer.getCurrentPosition();
                    if (!isDragging() && mShowing) {
                        onProgressUpdate(pos, pos);
                    }
                    if (mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    } else {
                        removeMessages(SHOW_PROGRESS);
                    }
                    break;
            }
        }
    };

    //是否按下了进度条
    private boolean isDragging() {
        return smallLayout.isDragging() || fullLayout.isDragging();
    }


    private boolean isFullState;

    @Override
    public boolean isFullScreen() {
        return isFullState;
    }

    @Override
    public void setFullscreen(Activity activity, boolean isFullscreen) {
        this.isFullState = isFullscreen;
        if (isFullscreen) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (fullScreenControl != null)
            fullScreenControl.setFullscreen(activity, isFullscreen);
        if (mediaTouch != null)
            mediaTouch.setFullscreen(activity, isFullscreen);
    }

    private void show() {
        show(5000);
    }


    @Override
    public void show(int timeout) {
        if (!mShowing) {
            mShowing = true;
            mHandler.sendEmptyMessage(SHOW_PROGRESS);
            smallLayout.show(timeout);
        }
        if (timeout != 0) {
            mHandler.removeMessages(FADE_OUT);
            mHandler.sendMessageDelayed(mHandler.obtainMessage(FADE_OUT), timeout);
        }
    }

    @Override
    public void hide() {
        mShowing = false;
        smallLayout.hide();
    }


    @Override
    public void onProgressUpdate(long current, long duration) {
        smallLayout.onProgressUpdate(current, duration);
    }

    @Override
    public boolean clickEvent() {
        show();
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed() {
        if (mShowing) {
            hide();
        } else {
            show();
        }
        return false;
    }


    @Override
    public void onDoubleTap() {
        boolean prepare = mPlayer.isPrepare();
        if (prepare && mPlayer.isPlaying()) {
            mPlayer.pause();
        } else if (prepare) {
            mPlayer.start();
        }
    }

    protected void closeAll() {
        mShowing = false;
        smallLayout.close();
    }

    private void closeSmall() {
        mShowing = false;
        smallLayout.close();
    }

    @Override
    public void changeLayoutState(int layoutState) {
        currentState = layoutState;
        mediaTouch.changeLayoutState(layoutState);
        smallLayout.changeLayoutState(layoutState);
        fullLayout.changeLayoutState(layoutState);
    }
}
