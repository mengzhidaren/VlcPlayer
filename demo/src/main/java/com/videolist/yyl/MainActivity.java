package com.videolist.yyl;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * 市场 上的列表播放器 好像只有这 5 种写法了
 * 欢迎补充
 */
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

    /**
     * 常规写法1
     * ----切换根布局更换父容器
     */
    public void listVideo1(View view) {
        //太多了懒的写
    }

    /**
     * 常规写法2
     * <p>推荐</p>
     * 悬浮无入侵
     */
    public void listVideo2(View view) {//悬浮播放
        startActivity(new Intent(this, ListVideoActivity.class));
    }

    /**
     * 常规写法3
     * 切换activity
     */
    public void listVideo3(View view) {
        //太多了懒的写
    }

    /**
     * 常规写法4
     * 旋转rotation 写法
     */
    public void listVideo4(View view) {
        //这个有时间慢慢更新
    }

    /**
     * 常规写法5
     * 悬浮server 写法
     */
    public void listVideo5(View view) {
        //有时间慢慢更新
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
