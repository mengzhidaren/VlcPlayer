package com.yyl.videolist.video;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.yyl.videolist.R;
import com.yyl.videolist.VideoView;
import com.yyl.videolist.listeners.MediaPlayerChangeState;

/**
 * Created by yyl on 2016/10/12/012.
 */

public class VlcVideoView extends VlcVideoViewImpl {
    String tag = "VlcVideoView";

    private VlcMediaController controller;
    private boolean isStop = true;
    private VideoView videoPlayer;

    public VlcVideoView(Context context) {
        super(context);
        initView(context);
    }

    public VlcVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public VlcVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    protected void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.video_list_controller, this);
        videoPlayer = findV(R.id.vlc_video_view);
        controller = new VlcMediaController(videoPlayer);
    }

    @Override
    public boolean isFullState() {
        return controller.isFullScreen();
    }

    @Override
    public boolean onBackPressed(Activity activity) {
        if (isFullState() && layoutState != MediaPlayerChangeState.stateFull) {
            controller.setFullscreen(activity, false);
            return true;
        }
        return false;
    }

    private int layoutState;

    public void changeLayoutState(int layoutState) {
        this.layoutState = layoutState;
    }

    private void initController() {
        if (controller.isAnchor()) return;
        controller.setBuffingLayout(new MediaControllerBuffing(this));
        controller.setSmallLayout(new MediaControllerSmall(activity, this, videoPlayer, controller));
        //controller.setFullLayout(new MediaControllerSmall(activity, this, videoPlayer, controller));
        controller.setTouch(new MediaControllerTouch(this, videoPlayer, controller));
        controller.changeLayoutState(MediaPlayerChangeState.stateSmallFull);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        controller.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        controller.onDetachedFromWindow();
    }

    @Override
    public void playVideo(String path) {
        initController();
        videoPlayer.setMediaListenerEvent(controller);
        controller.setAnchor();
        AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        videoPlayer.startPlay(path);
        isStop = false;
    }

    @Override
    public void stopVideo(boolean visiable) {
        if (!visiable) {
            setVisibility(INVISIBLE);
        }
        Log.i(tag, "stopVideo     isStop=" + isStop);
        if (!isStop) {
            isStop = true;
            videoPlayer.onStop();
        }
    }


    public void saveState() {
        videoPlayer.saveState();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (isInEditMode()) {
            return;
        }
        if (visibility == GONE) {
            stopVideo(false);
        } else if (visibility == INVISIBLE) {
            stopVideo(false);
        }
    }

    public <T extends View> T findV(int id) {
        return (T) findViewById(id);
    }


}