package com.yyl.videolist.video;


import com.yyl.videolist.listeners.MediaPlayerControl;

/**
 * Created by yuyunlong on 2016/8/12/012.
 */
public class VlcMediaController extends VlcMediaControllerBase {

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
    public void eventBuffing(final float buffing, boolean show) {
        if (isResume) {
            isShowLoading = show;
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    buffingLayout.eventBuffing(buffing, isShowLoading);
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
    public void eventPlay() {

    }

    @Override
    public void eventPause() {

    }

    @Override
    public void eventReleaseInit() {

    }


}
