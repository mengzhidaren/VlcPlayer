package com.videolist.yyl.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by Administrator on 2016/1/26/026.
 */
public class DialogUtils {


    public static MaterialDialog showDialog(Context context, String title, String message, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText("确定").negativeText("取消").onPositive(singleButtonCallback).onNegative(singleButtonCallback).show();
    }

    public static MaterialDialog show3GDialog(Context context, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        return showDialog(context, "提示", "当前是3G网络是否继续播放", singleButtonCallback);
    }


}
