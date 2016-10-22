package com.yyl.videolist.listeners;

/**
 * Created by Administrator on 2016/3/25/025.
 */
public interface MediaListenerEvent {

    void eventBuffing(float buffing, boolean show);

    /**
     * 初始化开始加载
     * @param openingVideo  true加载视频中
     */
    void eventPlayInit(boolean openingVideo);

    void eventStop(boolean isPlayError);

    void eventError(int error, boolean show);

    void eventPlay();
    void eventPause();

    /**
     * 回到初始化  比如显示封面图
     */
    void eventReleaseInit();
}
