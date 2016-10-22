package com.videolist.yyl.ui;

import android.app.ProgressDialog;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.videolist.yyl.R;
import com.yyl.videolist.VideoView;
import com.yyl.videolist.listeners.MediaListenerEvent;
import com.yyl.videolist.utils.V;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.vlc.util.VLCOptions;

import java.util.ArrayList;

/**
 * 电视直播
 * 请至官方网站查看更多设置
 * https://wiki.videolan.org/VLC_command-line_help/
 */
public class RTSPActivity extends AppCompatActivity implements MediaListenerEvent {
    VideoView videoView;
    String path1 = "http://devimages.apple.com/iphone/samples/bipbop/bipbopall.m3u8";//苹果的
    String path2 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";// h264的地址
    String path3 = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";//

    ProgressDialog progressDialog;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtsp);
        editText = V.findV(this, R.id.url_test);
        progressDialog = new ProgressDialog(this);
        videoView = V.findV(this, R.id.rtsp_video);
        videoView.setMediaListenerEvent(this);
        videoView.startPlay(path3);
        editText.setText(path2);
        getSupportActionBar().setTitle("RTSP,电视直播,m3u8");
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoView.onStop();
    }

    public void test_start(View view) {

        videoView.startPlay(editText.getText().toString());
    }

    /**
     * <p>尝试秒开网络流但是秒开后马上就卡那几秒
     * <p>有用的上的自己慢慢调吧
     * 推荐硬解会好点  手机烂的就算了
     */
    public void test2() {
//        videoView.onStop();
        ArrayList<String> libOptions = VLCOptions.getLibOptions(getApplicationContext());
        //  libOptions.add("--rtsp-tcp");
        libOptions.add("--rtsp-http");
        libOptions.add("--rtsp-frame-buffer-size=1024");//  --rtsp-frame-buffer-size=<integer [-2147483648 .. 2147483647]>
        //libOptions.add("--rtsp-timeout=1");
        libOptions.add("--ipv4-timeout=5");
        libOptions.add("--network-caching=500");
        libOptions.add("--androidwindow-chroma");
        libOptions.add("RV16");
        videoView.setMediaPlayer(new LibVLC(getApplicationContext(), libOptions));

        final Media media = new Media(new LibVLC(this), Uri.parse(path2));
        media.setHWDecoderEnabled(true, true);
        media.parseAsync(Media.Parse.FetchNetwork, 10 * 1000);
        media.addOption(":file-caching=500");
        media.addOption(":network-caching=500");
        videoView.setMedia(media);
        videoView.startPlay(path2);

    }

    long time;

    @Override
    public void eventPlayInit(boolean openingVideo) {
        if (!openingVideo) {
            time = System.currentTimeMillis();
        } else {
            long useTime = System.currentTimeMillis() - time;
            Log.i("yyl", "打开地址时间=" + useTime);
        }
        progressDialog.show();
    }

    @Override
    public void eventBuffing(float buffing, boolean show) {

    }


    @Override
    public void eventStop(boolean isPlayError) {
        progressDialog.hide();
    }

    @Override
    public void eventError(int error, boolean show) {
        progressDialog.hide();
    }

    @Override
    public void eventPlay() {
        long useTime = System.currentTimeMillis() - time;
        Log.i("yyl", "延迟时间=" + useTime);
        progressDialog.hide();
    }

    @Override
    public void eventPause() {

    }

    @Override
    public void eventReleaseInit() {

    }
}
