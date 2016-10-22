package com.videolist.yyl.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyunlong on 2016/6/23/023.
 */
public abstract class BaseAdapter<T> extends
        RecyclerView.Adapter<BaseViewHolder>  {

    protected final ArrayList<T> mData = new ArrayList<>();


    public boolean isEmpty() {
        return mData.size() == 0;
    }

    public void addAllItemNotify(ArrayList<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addAllItemNotify(List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void setDataItem(List<T> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void addFirst(T t) {
        mData.add(0, t);
    }

    public void addLast(T t) {
        mData.add(mData.size(), t);
    }


    public void addItemList(List<T> data) {
        mData.addAll(mData.size(), data);
    }

    public void clearAllItem() {
        mData.clear();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return getOtherItemViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getOtherItemViewType(position);
    }

    protected int getOtherItemViewCount() {
        return mData.size();
    }

    protected int getOtherItemViewType(int position) {
        return 0;
    }

    protected abstract int getLayoutId(int viewType);

    protected abstract BaseViewHolder getHolder(View itemView, int viewType);

    public void onDestroy() {

    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        holder.onRecycler();
    }
}
