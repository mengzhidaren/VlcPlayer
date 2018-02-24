package com.yyl.videolist.listeners;

import org.videolan.vlc.listener.MediaListenerEvent;

/**
 * Created by Administrator on 2016/2/17/017.
 * <p>
 * 所有的事件的定义
 */
public interface VideoViewListeners extends MediaListenerEvent {


    void show(int time);

    void hide();


    void onProgressUpdate(long current, long duration);


    boolean onSingleTapConfirmed();
    void onDoubleTap();
}
