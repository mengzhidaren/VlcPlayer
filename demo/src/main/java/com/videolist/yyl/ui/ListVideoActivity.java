package com.videolist.yyl.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.videolist.yyl.R;
import com.videolist.yyl.dao.VideoListData;
import com.videolist.yyl.view.VideoAdapter;
import com.yyl.videolist.video.VlcVideoView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ListVideoActivity extends AppCompatActivity {

    VideoAdapter videoAdapter;
    RecyclerView recyclerView;
    VlcVideoView vlcVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        recyclerView = ((RecyclerView) findViewById(R.id.videoList));
        vlcVideoView = ((VlcVideoView) findViewById(R.id.vlc_videoView));
        videoAdapter = new VideoAdapter(vlcVideoView);
        recyclerView.setAdapter(videoAdapter);


        vlcVideoView.onAttached(this);
        vlcVideoView.onAttached(recyclerView);
        initData();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    @Override
    public void onBackPressed() {
        if (vlcVideoView.onBackPressed(this)) return;
        super.onBackPressed();
    }

    private void initData() {
        String data = readTextFileFromRawResourceId(this, R.raw.video_list);
        VideoListData data1 = new Gson().fromJson(data, VideoListData.class);
        videoAdapter.refresh(data1.getList());
    }

    public String readTextFileFromRawResourceId(Context context, int resourceId) {
        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(
                resourceId)));

        try {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }
}
