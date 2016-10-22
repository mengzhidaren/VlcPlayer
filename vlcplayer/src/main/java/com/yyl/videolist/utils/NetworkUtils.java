package com.yyl.videolist.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yuyidong on 15/10/19.
 */
public class NetworkUtils {
    /**
     * 判断当前网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }
    /**
     * 判断当前网络是否连接
     *
     * @return
     */
    public static boolean checkNetWorkOrToast(View view) {
        if (NetworkUtils.isNetworkConnected(view.getContext())) {
            return false;
        } else {
           // ToastUtils.showToast(view, "网络不可用");
            Toast.makeText(view.getContext(),"网络不可用",Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    /**
     * 判断wifi状态是否连接
     *
     * @param context
     * @return
     */
    public static final boolean isWifiConnected(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }



}
