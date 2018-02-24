package com.yyl.videolist.video;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yyl.videolist.FrameLayoutScale;
import com.yyl.videolist.listeners.VideoViewDetachedEvent;
import com.yyl.videolist.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by yyl on 2016/10/19/019.
 */

public abstract class VlcVideoViewImpl extends FrameLayoutScale implements VideoViewDetachedEvent, ViewPager.OnPageChangeListener, View.OnAttachStateChangeListener {
    private static final ArrayList<VideoViewDetachedEvent> videoListDetache = new ArrayList();
    private int translationY = -1;
    private int startLocationY = -1;
    private View currentPlayView;
    private boolean isAttach;
    public String tag = "VideoViewImpl";

    public VlcVideoViewImpl(Context context) {
        super(context);
    }

    public VlcVideoViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VlcVideoViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void addEvent(VideoViewDetachedEvent event) {
        if (!videoListDetache.contains(event))
            videoListDetache.add(event);
    }

    protected void removeEvent(VideoViewDetachedEvent event) {
        if (videoListDetache.contains(event))
            videoListDetache.remove(event);
    }

    /**
     * 没有可活动的窗口就回收掉
     */
    public static void detachedWindowEvent() {
        for (VideoViewDetachedEvent detache : videoListDetache) {
            detache.detachedEvent();
        }
    }


    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setScaleFull(isFullState());
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            if (isFullState()) {
                setTranslationY(0);
            } else {
                if (translationY != -1)
                    setTranslationY(translationY);
            }
        }
    }

    protected Activity activity;

    public void onAttached(Activity activity) {
        this.activity = activity;
    }

    public void onAttached(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(recyclerListener);
    }


    public void onAttached(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(this);
    }

    public void detached(ViewPager viewPager) {
        viewPager.removeOnPageChangeListener(this);
    }

    public void detached(RecyclerView recyclerView) {
        recyclerView.removeOnScrollListener(recyclerListener);
    }

    /**
     * 初始化开始的位置
     */
    private void initTranslation(View playView) {
        setTranslationY(0);
        setTranslationX(0);
        setVisibility(VISIBLE);
        int[] location = new int[2];
        playView.getLocationInWindow(location);
        // int viewX = location[0];
        int viewY = location[1];
        if (startLocationY == -1) {
            getLocationInWindow(location);
            //      startLocationX = location[0];
            startLocationY = location[1];
        }
        Log.i("yyl", "startLocationY=" + startLocationY + "    viewY=" + viewY);
        translationY = viewY - startLocationY;
        setTranslationY((float) translationY);
    }


    public void onClickViewPlay(View playView, String path) {
        initTranslation(playView);
        if (currentPlayView != null) {
            currentPlayView.removeOnAttachStateChangeListener(this);
            currentPlayView = null;
        }
        currentPlayView = playView;
        currentPlayView.addOnAttachStateChangeListener(this);
        playVideo(path);
    }

    public abstract void playVideo(String path);

    public abstract void stopVideo(boolean visiable);

    public abstract boolean isFullState();

    public abstract boolean onBackPressed(Activity activity);

    @Override
    public void detachedEvent() {
        Log.i(tag, "detachedEvent ");
        stopVideo(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isAttach = true;
        addEvent(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isAttach = false;
        removeEvent(this);
        Log.i(tag, "onDetachedFromWindow ");
        stopVideo(false);
    }


    private RecyclerView.OnScrollListener recyclerListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            translationY -= dy;
            setTranslationY((float) translationY);
            //scrollBy 即表示在原先偏移的基础上在发生偏移
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(tag, "positionOffsetPixels=" + positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onViewAttachedToWindow(View v) {

    }


    @Override
    public void onViewDetachedFromWindow(View v) {
        if (v.equals(currentPlayView) && !isFullState()) {
            v.removeOnAttachStateChangeListener(this);
            LogUtils.i(tag,"v.equals(currentPlayView)");
            stopVideo(false);
        }
        Log.i(tag, "onViewDetachedFromWindow = " + v.equals(currentPlayView));
    }
}
