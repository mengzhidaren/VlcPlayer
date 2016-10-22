package com.videolist.yyl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by yuyunlong on 2016/6/17/017.
 */
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private static final ArrayList<VideoViewDetachedEvent> videoListDetache = new ArrayList();

    public interface VideoViewDetachedEvent {
        void detachedEvent();
    }

    public static void addEvent(VideoViewDetachedEvent event) {
        if (!videoListDetache.contains(event))
            videoListDetache.add(event);
    }

    public static void removeEvent(VideoViewDetachedEvent event) {
        if (videoListDetache.contains(event))
            videoListDetache.remove(event);
    }

    public static void detachedWindow() {
        for (VideoViewDetachedEvent detache : videoListDetache) {
            detache.detachedEvent();
        }
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
    }


    public void setData(int position) {
        itemView.setOnClickListener(new ItemsOnClick(position));
    }


    public void setData(int positionH, int positionBody) {

    }

    public void onRecycler() {
    }

    public void setOnClick(View v, int position) {

    }

    private class ItemsOnClick implements View.OnClickListener {
        final int position;

        public ItemsOnClick(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            setOnClick(v, position);
        }
    }

    public void onViewAttachedToWindow() {
    }

    public boolean isVideoType() {
        return false;
    }


    public void onViewDetachedFromWindow() {
    }

    public boolean isPlayVideo() {
        return false;
    }

    public void startPlayVideo() {
    }


}
