package com.yyl.videolist.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * Created by yyl on 2015/11/26/026.
 */
public class MySensorListener{

    private SensorManager mManager;
    private Sensor mSensor = null;
    private boolean isChange = false;
    private static boolean lock = false;

    public MySensorListener(Context context) {
        mManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public static boolean isLock() {
        return lock;
    }

    public static void setLock(boolean lock) {
        MySensorListener.lock = lock;
    }

    public void onResume() {
        /*
         * 一个服务可能有多个Sensor实现，此处调用getDefaultSensor获取默认的Sensor 参数3 ：模式 可选数据变化的刷新频率
		 */
        if (mManager != null) {
            mManager.registerListener(myAccelerometerListener, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void onPause() {
        handler.removeMessages(landscape);
        handler.removeMessages(portrait);
        /*
         * onPause方法中关闭触发器，否则讲耗费用户大量电量，
		 */
        if (myAccelerometerListener != null && mManager != null) {
            mManager.unregisterListener(myAccelerometerListener);
        }
    }
    private final static int portrait = 1;
    private final static int landscape = 2;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case portrait:
                        if (sensorLandspace != null && !isLock()) {
                            boolean change1 = sensorLandspace.changeScreen(false);
                            if (!change1) {
                                isChange = !isChange;
                            }
                        }
                        break;
                    case landscape:
                        if (sensorLandspace != null) {
                            boolean change = sensorLandspace.changeScreen(true);
                            if (!change) {
                                isChange = !isChange;
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    ScreenChangeSensorLandspace sensorLandspace;
    //ScreenChangeSensorPortrait sensorPortrait;

    public void setSensorLandspace(ScreenChangeSensorLandspace sensorLandspace) {
        this.sensorLandspace = sensorLandspace;
    }

    public void setSensorPortrait(ScreenChangeSensorPortrait sensorPortrait) {
        // this.sensorPortrait = sensorPortrait;
    }

    public interface ScreenChangeSensorLandspace {
        boolean changeScreen(boolean isFullSceen);
    }

    public interface ScreenChangeSensorPortrait {
        boolean changeScreen();
    }

    String TAG = "yyl";
    /*
     * SensorEventListener接口的实现，需要实现两个方法 方法1 onSensorChanged 当数据变化的时候被触发调用 方法2
     * onAccuracyChanged 当获得数据的精度发生变化的时候被调用，比如突然无法获得数据时
     */
    private SensorEventListener myAccelerometerListener = new SensorEventListener() {

        // 复写onSensorChanged方法
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[0];//x是9的时候手机右侧最上面  向上
                float y = sensorEvent.values[1];// y是9的时候是手机（听筒）最上面 -9相反
                float z = sensorEvent.values[2];// z是9的时候屏幕重力向上 对着人脸
                // Log.i(TAG, "\n  X=" + x);
                //	Log.i(TAG, "\n Y= " + y);
                //	Log.i(TAG, "\n Z= " + z);
                if (y > 7.1f) {//竖屏
                    if (!isLock() && isChange) {
                        isChange = false;
                        handler.removeMessages(landscape);
                        handler.sendEmptyMessageDelayed(portrait, 500);

                    }
                } else if (y < 3f && y > -3f) {//横屏
                    if (x > 5f || x < -5f) {
                        if (!isLock() && !isChange) {
                            isChange = true;
                            handler.removeMessages(portrait);
                            handler.sendEmptyMessageDelayed(landscape, 500);

                        }
                    }
                }
            }
        }

        // 复写onAccuracyChanged方法
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


}
