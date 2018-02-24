package com.yyl.videolist.demo;

import com.yyl.videolist.listeners.VideoViewControllerListeners;

/**
 * Created by Administrator on 2016/10/20/020.
 */

public class VideoControllerDemo implements VideoViewControllerListeners {
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
