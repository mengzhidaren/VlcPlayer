package com.videolist.yyl.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.videolist.yyl.R;
import com.videolist.yyl.dao.VideoItemData;
import com.yyl.videolist.video.VlcMediaView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/17/017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<VideoItemData> list = new ArrayList<>();
    VlcMediaView vlcVideoView;

    public VideoAdapter(VlcMediaView vlcVideoView) {
        this.vlcVideoView = vlcVideoView;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(List<VideoItemData> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView cover;
        TextView from;
        TextView vlc_type;

        public VideoViewHolder(View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.vlc_title);
            cover=itemView.findViewById(R.id.cover);
            from=itemView.findViewById(R.id.vlc_from);
            vlc_type=itemView.findViewById(R.id.vlc_type);
        }

        public void setData(final int position) {
            final VideoItemData videoItemData = list.get(position);
            title.setText(videoItemData.getTitle());
            cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vlcVideoView.onClickViewPlay(v, videoItemData.getMp4_url());
                }
            });
            Glide.with(cover.getContext()).load(videoItemData.getCover()).into(cover);
            from.setText(videoItemData.getVideosource());
            vlc_type.setText(videoItemData.getType() + "=type");
        }
    }

}