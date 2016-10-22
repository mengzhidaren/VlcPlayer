package com.yyl.videolist.listeners;

import android.app.Activity;

/**
 * Created by yuyunlong on 2016/7/16/016.
 */
public interface FullScreenControl {


    boolean isFullScreen();

    void setFullscreen(Activity activity, boolean isFullscreen);

    /**
     * 点击显示菜单 show
     *
     * @return
     */
    boolean clickEvent();
}
