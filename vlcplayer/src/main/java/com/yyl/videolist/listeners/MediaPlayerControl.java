package com.yyl.videolist.listeners;

/**
 * Created by yyl on 2016/2/16/016.
 */
public interface MediaPlayerControl {

    boolean isPrepare();

    /**
     *
     */
    void startPlay(String path);


    void start();

    void pause();

    long getDuration();

    long getCurrentPosition();

    void seekTo(long pos);

    boolean isPlaying();

    /**
     * 镜面
     */
    void setMirror(boolean mirror);

    boolean getMirror();

    /**
     * 第二级缓冲
     *
     * @return
     */
    int getBufferPercentage();

    /**
     * 速度  0.25--4.0
     */
    boolean setPlaybackSpeedMedia(float speed);

    float getPlaybackSpeed();

    /**
     * 循环    写的不是很好太low了
     */
    void setLoop(boolean isLoop);

    boolean isLoop();
}
