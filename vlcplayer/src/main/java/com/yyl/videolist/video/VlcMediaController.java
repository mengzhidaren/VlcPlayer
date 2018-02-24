package com.yyl.videolist.video;


import org.videolan.vlc.listener.MediaListenerEvent;
import org.videolan.vlc.listener.MediaPlayerControl;

/**
 * Created by yuyunlong on 2016/8/12/012.
 */
public class VlcMediaController extends VlcMediaControllerBase implements MediaListenerEvent {

    public VlcMediaController(MediaPlayerControl mediaPlayerControl) {
        super(mediaPlayerControl);
    }

    boolean isShowLoading;

    public void onAttachedToWindow() {
        isResume = true;
    }

    public void onDetachedFromWindow() {
        isResume = false;
    }


    @Override
    public void eventBuffing(final int event, final float buffing) {
        if (isResume) {
            isShowLoading = buffing==100f;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    buffingLayout.eventBuffing(event, buffing);
                }
            });

        }
    }

    @Override
    public void eventPlayInit(boolean opening) {
        if (!opening) {
            smallLayout.close();
            fullLayout.close();
        }
        mediaTouch.setError(false);
    }

    @Override
    public void eventStop(boolean isPlayError) {
        if (isResume) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    @Override
    public void eventError(final int error, final boolean show) {
        if (isResume) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    closeAll();
                    buffingLayout.eventError(error, show);
                    mediaTouch.setError(show);
                }
            });

        }
    }


    @Override
    public void eventPlay(boolean isPlaying) {

    }




}
