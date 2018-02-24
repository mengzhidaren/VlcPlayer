package com.yyl.videolist.video;

import android.view.View;
import android.widget.TextView;


import com.yyl.videolist.R;
import com.yyl.videolist.listeners.VideoViewControllerListeners;
import com.yyl.videolist.utils.V;


/**
 * Created by yyl on 2016/2/17/017.
 * 加载进度条控制器
 */
public class MediaControllerBuffing implements VideoViewControllerListeners {
    View bufferingView;
    View video_error_layout;
    TextView buffering;

    public MediaControllerBuffing(View layoutRootView) {
        bufferingView = V.findV(layoutRootView, R.id.video_buffering_progress);
        video_error_layout = V.findV(layoutRootView, R.id.video_error_layout);
        buffering = V.findV(layoutRootView, R.id.video_buffering_progress_text);
    }



    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public void changeLayoutState(int layoutState) {

    }

    @Override
    public void show(int time) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onProgressUpdate(long current, long duration) {

    }

    @Override
    public boolean onSingleTapConfirmed() {
        return false;
    }

    @Override
    public void onDoubleTap() {

    }


    @Override
    public void eventBuffing(int event, float buffing) {
        if (buffing==100f) {
            buffering.setVisibility(buffing > 0 ? View.VISIBLE : View.GONE);
            buffering.setText(buffing + "%");
            bufferingView.setVisibility(View.VISIBLE);
        } else {
            bufferingView.setVisibility(View.GONE);
        }
        if (video_error_layout.getVisibility() == View.VISIBLE) {
            video_error_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void eventPlayInit(boolean opening) {

    }

    @Override
    public void eventStop(boolean isPlayError) {

    }

    @Override
    public void eventError(int error, boolean show) {
        if (show) {
            bufferingView.setVisibility(View.GONE);
            video_error_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void eventPlay(boolean isPlaying) {

    }

}
