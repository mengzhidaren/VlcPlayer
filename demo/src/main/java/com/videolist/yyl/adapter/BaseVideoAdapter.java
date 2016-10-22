package com.videolist.yyl.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yyl.videolist.utils.LogUtils;
import com.yyl.videolist.utils.NetworkUtils;

import java.util.ArrayList;

/**
 * Created by yuyunlong on 2016/8/9/009.
 */
public abstract class BaseVideoAdapter<T> extends BaseAdapter<T> implements BaseViewHolder.VideoViewDetachedEvent {

    private ArrayList<BaseViewHolder> holderList = new ArrayList<>();

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        holderList.clear();
        BaseViewHolder.addEvent(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE  && NetworkUtils.isWifiConnected(recyclerView.getContext())) {//自动播放视频代码
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int first = layoutManager.findFirstCompletelyVisibleItemPosition();
                    int last = layoutManager.findLastCompletelyVisibleItemPosition();
                    if (first == -1 || last == -1) {//没一个全显示
                        return;
                    }
                    for (BaseViewHolder holder : holderList) {
                        int adapterPosition = holder.getAdapterPosition();
                        LogUtils.i("first=" + first + "  last=" + last + "  adapterPosition=" + adapterPosition + "  isVideoType()=" + holder.isVideoType());
                        if (first == adapterPosition || adapterPosition == last) {//在view全部可见的item上
                            if (holder.isVideoType()) {
                                if (!holder.isPlayVideo()) {
                                    LogUtils.i("startPlayVideo    =" + adapterPosition);
                                    holder.startPlayVideo();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        });

    }

    @Override
    public void detachedEvent() {
        for (BaseViewHolder holder : holderList) {
            if (holder.isPlayVideo()) {
                holder.onViewDetachedFromWindow();
                holder.onViewAttachedToWindow();
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        BaseViewHolder.removeEvent(this);
        holderList.clear();
    }


    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
        holderList.add(holder);
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
        holderList.remove(holder);
    }


}
