package com.yyl.videolist.listeners;

/**
 * Created by Administrator on 2016/10/19/019.
 */

public interface VideoViewControllerListeners extends VideoViewListeners  {

    /**
     * @return 是否在控制进度中
     */
    boolean isDragging();

    /**
     * 切换中关闭没有动画
     */
    void close();


    void changeLayoutState(int layoutState);
}
