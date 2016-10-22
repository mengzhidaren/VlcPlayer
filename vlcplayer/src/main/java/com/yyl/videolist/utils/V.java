package com.yyl.videolist.utils;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2016/10/19/019.
 */

public class V {

    public static  <T extends View> T findV(View view,int id) {
        return (T) view.findViewById(id);
    }
    public static  <T extends View> T findV(Activity activity, int id) {
        return (T) activity.findViewById(id);
    }
}
