package com.lsm.android_demo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import org.litepal.LitePal;


/**
 * 自己定义application
 * 用于保存Context
 * 方便获取Context
 */
public class MyApplication extends Application {
    private static Context mContent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContent = getApplicationContext();
        LitePal.initialize(this);
        Log.d("MyApplication", "onCreate: ");

    }

    public static Context getContext(){
        return mContent;
    }
}
