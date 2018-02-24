package com.yyl.videolist;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by yyl on 2018/2/24.
 */

public class MyVideoView extends org.videolan.vlc.VlcVideoView {
    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
