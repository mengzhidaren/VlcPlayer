package com.yyl.videolist.listeners;

/**
 * Created by yuyunlong on 2016/7/16/016.
 */
public interface MediaPlayerChangeState {
    int stateSmallFull = 0;//普通的大小屏切换
    int stateSmallList = 1;//列表播放
    int stateFull = 2;//只有大屏
    int stateNull = 3;//空的 预览视频
    void changeLayoutState(int layoutState);
}
