# VlcPlayer
vlc-android播放器
# VlcPlayer
VlcPlayer实现了在列表控件（RecyclerView）中加载并播放视频，
非入侵adapter的写法

**注意：最低支持API 14以上**

#效果预览


**Demo 更新**

#基本用法

	
	

在xml布局中加入以下代码
 <com.yyl.videolist.video.VlcVideoView
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
然后设置视频路径地址，和活动范围
recyclerView最后调用onClickViewPlay()方法开始播放视频
  vlcVideoView.onAttached(activity);
  vlcVideoView.onAttached(recyclerView);
  vlcVideoView.onClickViewPlay(v, videoItemData.getMp4_url());
  //全屏返回
   if (vlcVideoView.onBackPressed(this)) return;
    详细请查看代码
