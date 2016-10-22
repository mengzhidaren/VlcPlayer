package com.yyl.videolist.listeners;

import android.app.Activity;

/**
 * Created by Administrator on 2016/10/19/019.
 */

public interface VideoViewTouchListeners extends MediaPlayerChangeState {

    void setError(boolean show);

    void setFullscreen(Activity activity, boolean isFullscreen);
}
