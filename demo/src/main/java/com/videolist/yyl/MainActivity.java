package com.videolist.yyl;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.videolist.yyl.ui.DemoActivity;
import com.videolist.yyl.ui.ListVideoActivity;
import com.videolist.yyl.ui.MiniVideoActivity;
import com.videolist.yyl.ui.OthereActivity;
import com.videolist.yyl.ui.RTSPActivity;
import com.videolist.yyl.ui.ViewPageListActivity;
import com.yyl.videolist.utils.LogUtils;

import org.videolan.vlc.util.VLCInstance;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (VLCInstance.testCompatibleCPU(this)) {
            LogUtils.i("cup   ok");
        } else {
            LogUtils.i("不支持   什么鬼cpu");
        }
    }

    public void demo(View view) {
        startActivity(new Intent(this, DemoActivity.class));
    }

    public void rtsp(View view) {
        startActivity(new Intent(this, RTSPActivity.class));
    }

    public void listVideo(View view) {

    }

    /**
     *  悬浮播放
     */
    public void listVideo2(View view) {//悬浮播放
        startActivity(new Intent(this, ListVideoActivity.class));
    }

    public void viewpage(View view) {
        startActivity(new Intent(this, ViewPageListActivity.class));
    }

    public void miniVideo(View view) {
        startActivity(new Intent(this, MiniVideoActivity.class));
    }

    public void othere(View view) {
        startActivity(new Intent(this, OthereActivity.class));
    }

}
